/**
 * @FileName: UserLoginSuccessHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:42
 * Description: 登录成功处理类
 */
package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.config.JwtConfig;
import xyz.fusheng.model.common.utils.JwtTokenUtil;
import xyz.fusheng.model.common.utils.Result;
import xyz.fusheng.model.security.entity.SelfUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功处理类
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装 Jwt
        SelfUser selfUser = (SelfUser) authentication.getPrincipal();
        String token = JwtTokenUtil.createAccessToken(selfUser);
        token = JwtConfig.tokenPrefix + token;
        // 封装返回参数
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new Result<>("登陆成功！", token)));
    }
}
