/**
 * @FileName: UserLoginSuccessHandler
 * @Author: code-fusheng
 * @Date: 2020/4/26 22:42
 * Description: 登录成功处理类
 */
package xyz.fusheng.model.security.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import xyz.fusheng.code.springboot.core.entity.ResultVo;
import xyz.fusheng.model.common.config.JwtConfig;
import xyz.fusheng.model.common.enums.StateEnums;
import xyz.fusheng.model.common.utils.JwtTokenUtil;
import xyz.fusheng.model.common.utils.SecurityUtil;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.service.LoginLogService;
import xyz.fusheng.model.core.service.UserService;
import xyz.fusheng.model.security.entity.SelfUser;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private UserService userService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private Environment environment;

    /**
     * 登录成功处理类
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装 Jwt
        SelfUser selfUser = (SelfUser) authentication.getPrincipal();
        if (selfUser == null) {
            BeanUtils.copyProperties(authentication.getPrincipal(), selfUser);
        }
        String token = JwtTokenUtil.createAccessToken(selfUser);
        token = JwtConfig.tokenPrefix + token;
        // 封装返回参数
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new ResultVo<>("登陆成功！", token)));

        // 登录日志
        LoginLog loginLog = SecurityUtil.createLoginLog(request);
        loginLog.setUserName(selfUser.getUsername());
        loginLog.setUserId(selfUser.getUserId());
        loginLog.setMsg("登录成功！");
        loginLog.setLoginStatus(StateEnums.LOGIN_SUCCESS.getCode());

        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("env")+".log.login.exchange");
            rabbitTemplate.setRoutingKey(environment.getProperty("env")+".log.login.routing-key");
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(loginLog)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
            rabbitTemplate.convertAndSend(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
