/**
 * @FileName: UserLoginFailureHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:21
 * Description: 登录失败处理类
 */
package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException){
            log.info("【登录失败】"+exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500,"登录失败: 用户名不存在！")));
        }
        if (exception instanceof LockedException){
            log.info("【登录失败】"+exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500,"登录失败: 用户被冻结！")));
        }
        if (exception instanceof BadCredentialsException){
            log.info("【登录失败】"+exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(500,"登录失败: 用户名密码不正确！")));
        }
    }
}
