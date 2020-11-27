package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.service.LoginLogService;
import xyz.fusheng.model.security.sms.ValidateCodeException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName: UserLoginFailureHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:21
 * Description: 登录失败处理类
 */

@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    @Resource
    private LoginLogService loginLogService;

    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        LoginLog loginLog = SecurityUtil.createLoginLog(request);
        loginLog.setUserName(request.getParameter("username"));
        loginLog.setLoginStatus(StateEnums.LOGIN_ERROR.getCode());
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException) {
            log.info("【登录失败】" + exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500, "登录失败: 用户名不存在！")));
            loginLog.setMsg("登录失败: 用户名不存在！");
        }
        if (exception instanceof LockedException) {
            log.info("【登录失败】" + exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500, "登录失败: 用户被冻结！")));
            loginLog.setMsg("登录失败: 用户被冻结！");
        }
        if (exception instanceof BadCredentialsException) {
            log.info("【登录失败】" + exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500, "登录失败: 用户名密码不正确！")));
            loginLog.setMsg("登录失败: 用户名密码不正确！");
        }
        if (exception instanceof ValidateCodeException) {
            log.info("【登录失败】" + exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500, "登录失败: 验证码异常！")));
            loginLog.setMsg("登录失败: 验证码异常！");
        }
        loginLogService.save(loginLog);
    }
}
