/**
 * @FileName: UserAuthAccessDeniedHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 21:48
 * Description: 暂无权限处理类
 */
package xyz.fusheng.model.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.ResultUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception){
        ResultUtil.responseJson(response,ResultUtil.resultCode(403,"未授权"));
    }
}
