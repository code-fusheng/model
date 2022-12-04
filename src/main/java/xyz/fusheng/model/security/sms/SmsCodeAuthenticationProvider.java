package xyz.fusheng.model.security.sms; /**
 * @author: code-fusheng
 * @Date: 2020/10/30 13:25
 */

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.fusheng.model.security.service.SelfUserDetailsService;

/**
 * @FileName: SmsCodeAuthenticationProvider
 * @Author: code-fusheng
 * @Date: 2020/10/30 13:25
 * @version: 1.0
 * Description: 短信登录鉴权 实现 AuthenticationProvider
 */

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private SelfUserDetailsService selfUserDetailsService;

    /**
     * 处理验证逻辑
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 强转 authentication 为 smsCodeAuthenticationToken
        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;

        UserDetails user = selfUserDetailsService.loadUserByPhone((String) smsCodeAuthenticationToken.getPrincipal());

        if (user == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(smsCodeAuthenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }

    public SelfUserDetailsService getSelfUserDetailsService() {
        return selfUserDetailsService;
    }

    public void setSelfUserDetailsService(SelfUserDetailsService selfUserDetailsService) {
        this.selfUserDetailsService = selfUserDetailsService;
    }
}
