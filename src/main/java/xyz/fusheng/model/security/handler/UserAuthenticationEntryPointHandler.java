/**
 * @FileName: UserAuthenticationEntryPointHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:18
 * Description: 用户未登录处理类
 */
package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    /**
     * 用户未登录返回结果
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new Result<>(401,"请求失败: 未登录！")));
    }
}
