/**
 * @FileName: UserAuthAccessDeniedHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 21:48
 * Description: 暂无权限处理类
 * AccessDeniedHandler 授权异常处理
 */
package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.code.springboot.core.entity.ResultVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new ResultVo<>(403,"请求失败: 未授权！")));
    }
}
