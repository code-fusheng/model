package xyz.fusheng.model.common.aspect.aop; /**
 * @author: code-fusheng
 * @Date: 2020/10/6 20:02
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import xyz.fusheng.model.common.aspect.annotation.Log;
import xyz.fusheng.model.common.aspect.enums.BusinessStatus;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.AddressUtils;
import xyz.fusheng.model.common.utils.IpUtils;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.common.utils.ServletUtils;
import xyz.fusheng.model.core.entity.Collection;
import xyz.fusheng.model.core.entity.Good;
import xyz.fusheng.model.core.entity.OperaLog;
import xyz.fusheng.model.core.service.OperaLogService;
import xyz.fusheng.model.security.entity.SelfUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @FileName: OperaLogAspect
 * @Author: code-fusheng
 * @Date: 2020/10/6 20:02
 * @version: 1.0
 * Description: 操作日志切面
 */

@Component
@Aspect
@Log4j2
public class OperaLogAspect {

    /**
     * 保存操作日志接口
     */
    @Resource
    private OperaLogService operaLogService;

    /**
     * 声明切面
     * 只要Controller的方法里面有@Log这个一样注解，那我就要切入
     */
    @Pointcut("@annotation(xyz.fusheng.model.common.aspect.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult, Long time) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 判断当前用户是否登录
            SelfUser loginUser = new SelfUser();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                // 获取当前的用户
                loginUser = SecurityUtil.getUserInfo();
            } else {
                loginUser.setUsername("游客");
            }
            // *========数据库日志=========*//
            OperaLog operaLog = new OperaLog();
            operaLog.setStatus(BusinessStatus.SUCCESS.getCode());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operaLog.setOperaIp(ip);
            String address = AddressUtils.getIpAddressInfo(ip);
            operaLog.setOperaLocation(address);
            // 返回参数
            operaLog.setJsonResult(JSON.toJSONString(jsonResult));

            operaLog.setOperaUrl(ServletUtils.getRequest().getRequestURI());
            if (loginUser != null) {
                operaLog.setOperaName(loginUser.getUsername());
            }
            if (e != null) {
                operaLog.setStatus(BusinessStatus.FAIL.getCode());
                operaLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operaLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operaLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operaLog);
            // 保存数据库
            operaLogService.saveOperaLog(operaLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }


    /**
     * 是否存在注解，如果存在就获取
     *
     * @param joinPoint
     * @return
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint
     * @param controllerLog
     * @param operaLog
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, OperaLog operaLog) {
        // 设置action动作
        operaLog.setBusinessType((controllerLog.businessType().getCode()));
        // 设置标题
        operaLog.setOperaTitle(controllerLog.title());
        // 设置操作人类别
        operaLog.setOperatorType(controllerLog.operatorType().getCode());
        // 是否需要保存request，参数和值
        if (controllerLog.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operaLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param joinPoint
     * @param operaLog
     */
    private void setRequestValue(JoinPoint joinPoint, OperaLog operaLog) {
        String requestMethod = operaLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operaLog.setOperaParam(StringUtils.substring(params, 0, 1000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operaLog.setOperaParam(StringUtils.substring(paramsMap.toString(), 0, 1000));
        }
    }

    /**
     * 参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                if (!isFilterObject(paramsArray[i])) {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest
                || o instanceof HttpServletResponse;
    }

}
