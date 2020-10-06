/**
 * @FileName: RequestAspect
 * @Author: code-fusheng
 * @Date: 2020/4/14 20:53
 * Description: 切面
 */
package xyz.fusheng.model.common.aspect.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.StringUtils;
import xyz.fusheng.model.common.utils.ThreadLocalContext;
import xyz.fusheng.model.core.entity.Log;
import xyz.fusheng.model.core.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author code-fusheng
 * 切面输出基本信息
 * 以及记录日志
 * 0、@pointcut 配置切入点
 * 1、@Around 环绕通知
 *  在目标方法执行前后实施增强，可以用于日志，事务管理等功能
 * 2、@Before 前置通知
 *  在目标方法执行前实施增强，可以应用于权限管理功能
 * 3、@AfterReturning 后置通知
 *  在目标方法执行后实施增强，可以应用于关闭流、上传文件、删除临时文件等功能
 * 4、@AfterThrowing 异常抛出通知
 *  在方法抛出异常后实施增强，可以应用于处理异常记录日志等功能
 * 5、@DeclareParents 引介通知
 *  在目标类中添加一些新的方法和属性，可以应用于修改老版本程序
 * 6、@After 最终通知
 */

@Aspect
@Component
@Slf4j
public class RequestAspect {

    @Autowired
    private LogService logService;

    /**
     * @Pointcut 切点 指定那些文件需要 AOP
     *
     * execution 切入点表达式
     * 两个..代表所有子目录，最后括号里的两个..代表所有参数
     */
    @Pointcut("execution( * xyz.fusheng.*.controller..*(..))")
    public void logPointCut() {
    }

    /**
     * 前置通知：方法执行之前调用
     * 在目标方法执行前后实施增强，可以应用于权限管理等功能
     * 此处用于记录请求的参数和请求内容
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        Log logger = ThreadLocalContext.get().getLogger();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();
        // 记录下请求内容
        printRequestLog(joinPoint, request, uri);
    }

    /**
     * @Around 环绕通知
     * 在目标方法执行前后实施增强，可以应用于日志，事务等功能
     * pjp 是 JoinPoint 的子接口，表示可以执行目标方法
     * 1。必须是Object类型的返回值
     * 2. 必须要接收一个参数
     * 3. 必须使用 throw Throwable
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        // pjp.proceed() 执行目标方法 可以理解为对业务方法的模拟
        Object ob = pjp.proceed();
        // System.currentTimeMillis 获取系统当前时间
        long time = System.currentTimeMillis() - startTime;
        log.info("耗时 : {}", time);
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogTime(time);
        return ob;
    }

    /**
     * 后置通知 没有发生异常 （在发生异常的情况下不执行此通知）
     * @AfterReturning 会直接获取对应切面方法的返回值，可以对这个返回值进行进一步处理（不能改变）
     * 此处用于返回日志请求结果 LogResult
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        String result = JSON.toJSONString(ret);
        // 数据量大选择性赋值为******
        result = "******";
        ret = "******";
        log.info("返回值:{}", JSON.toJSONString(ret));
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogResult(result);
        logService.save(logger);
    }

    /**
     * 异常通知 处理程序中未处理的异常
     * 发生异常
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogStatus(StateEnums.REQUEST_ERROR.getCode());
        String exception = StringUtils.getPackageException(e, "xyz.fusheng");
        // 数据量大选择性赋值为******
        exception = "******";
        logger.setLogMessage(exception);
        logger.setLogTime(0L);
        // 发生异常走异常通知
        logService.save(logger);
    }

    /**
     * 打印请求日志
     * @param joinPoint
     * @param request
     * @param uri
     */
    private void printRequestLog(JoinPoint joinPoint, HttpServletRequest request, String uri) {
        // 拿到切面方法
        log.info("请求地址 : {}", uri);
        log.info("请求方式 : {}", request.getMethod());
        // 获取真实的ip地址
        String ip = StringUtils.getRemoteIp(request);
        log.info("IP : {}", ip);
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        log.info("方法 : {}.{}", controllerName, joinPoint.getSignature().getName());
        String params = Arrays.toString(joinPoint.getArgs());
        // 数据量大选择性赋值为******
        params = "******";
        log.info("请求参数：{}", params);

        // 获取日志实体
        Log logger = ThreadLocalContext.get().getLogger();
        logger.setLogUrl(uri);
        logger.setLogParams(params);
        logger.setLogStatus(StateEnums.REQUEST_SUCCESS.getCode());
        logger.setLogMethod(request.getMethod());
        logger.setLogIp(ip);
    }

}
