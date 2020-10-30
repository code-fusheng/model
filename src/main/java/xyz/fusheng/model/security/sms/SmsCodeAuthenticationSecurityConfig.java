package xyz.fusheng.model.security.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.security.handler.UserLoginFailureHandler;
import xyz.fusheng.model.security.handler.UserLoginSuccessHandler;

/**
 * @FileName: SmsCodeAuthenticationConfig
 * @Author: code-fusheng
 * @Date: 2020/10/30 14:04
 * @version: 1.0
 * Description: 短信验证 smsCode 配置类
 */

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        // 配置 smsCodeAuthenticationFilter 成功和失败的处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(userLoginFailureHandler);

        // 配置 SmsCodeAuthenticationProvider 的 UserDetailService
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 把 smsCodeAuthenticationFilter 过滤器添加在 UsernamePasswordAuthenticationFilter 之前
        builder.authenticationProvider(smsCodeAuthenticationProvider);
        builder.addFilterBefore(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
