package xyz.fusheng.model.security.filter; /**
 * @author: code-fusheng
 * @Date: 2020/10/30 14:36
 */

import com.alibaba.fastjson.JSON;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.fusheng.model.common.utils.RedisUtils;
import xyz.fusheng.model.common.utils.Result;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName: IpFilter
 * @Author: code-fusheng
 * @Date: 2020/10/30 14:36
 * @version: 1.0
 * Description: 监听访问 IP 地址，并进行相关逻辑判断
 */

public class IpFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getHeader("x-forwarded-for");
        String ipKey = "user:ip";
        // 判断该ip是否被封禁
        if (redisUtils.get(ipKey + ip) != null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new Result<>(401, "由于您近期存在异常操作，系统已禁止您访问。")));
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
