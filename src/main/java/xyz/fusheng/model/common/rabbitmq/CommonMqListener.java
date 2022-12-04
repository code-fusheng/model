package xyz.fusheng.model.common.rabbitmq;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import xyz.fusheng.model.common.utils.AddressUtils;
import xyz.fusheng.model.core.entity.LoginLog;
import xyz.fusheng.model.core.entity.User;
import xyz.fusheng.model.core.mapper.LoginLogMapper;
import xyz.fusheng.model.core.service.UserService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @FileName: CommonMQListener
 * @Author: code-fusheng
 * @Date: 2021/1/27 3:48 下午
 * @Version: 1.0
 * @Description:
 */

@Component
public class CommonMqListener {

    private static final Logger logger = LoggerFactory.getLogger(CommonMqListener.class);

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

    @Resource
    private UserService userService;

    @RabbitListener(queues = "${spring.profiles.active}.log.login.queue", containerFactory = "singleListenerContainer")
    public void consumeLogLoginQueue(@Payload byte[] message) {
        try {
            LoginLog loginLog = objectMapper.readValue(message, LoginLog.class);
            logger.info("消费者监听到用户登陆日志消息: -> {}", loginLog);
            loginLogMapper.insert(loginLog);
            // 更新用户地理信息位置
            User user = userService.getById(loginLog.getUserId());
            Map<String, Object> addressMap = AddressUtils.getIpAddressInfo(loginLog.getIpAddress());
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.lambda().eq(User::getUserId, user.getUserId());
            userUpdateWrapper.lambda().set(User::getAddress, loginLog.getLoginLocation());
            userUpdateWrapper.lambda().set(User::getLng, addressMap.containsKey("lng")?addressMap.get("lng").toString():null);
            userUpdateWrapper.lambda().set(User::getLat, addressMap.containsKey("lat")?addressMap.get("lat").toString():null);
            userService.update(userUpdateWrapper);
            logger.info("消费者处理用户登陆日志进行更新用户地理位置信息操作: -> {}", addressMap.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
