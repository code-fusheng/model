package xyz.fusheng.model.security.sms;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.fusheng.model.common.enums.TypeEnums;
import xyz.fusheng.model.common.utils.RedisUtils;
import xyz.fusheng.model.common.utils.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @FileName: ValidateCodeFilter
 * @Author: code-fusheng
 * @Date: 2020/10/30 15:30
 * @version: 1.0
 * Description:
 */

public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isSms = StringUtils.equals("/authentication/mobile", request.getRequestURI()) || StringUtils.equals("/register", request.getRequestURI());
        if (isSms && StringUtils.equalsIgnoreCase(request.getMethod(), TypeEnums.POST.getLabel())) {
            validateSmsCode(request);
            // 测试阶段不删除
            // redisUtils.del(request.getParameter("mobile"));
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 校验手机验证码
     *
     * @param request
     */
    private void validateSmsCode(HttpServletRequest request) {
        // 请求里的手机号与验证码
        String mobileInRequest = request.getParameter("mobile");
        String codeInRequest = request.getParameter("smsCode");

        SmsCode code = (SmsCode) redisUtils.get(mobileInRequest);

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空!");
        }
        if (code == null) {
            throw new ValidateCodeException("验证码已失效!");
        }
        if (!StringUtils.equals(code.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
}
