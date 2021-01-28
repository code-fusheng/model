package xyz.fusheng.model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.fusheng.model.common.utils.RabbitMQUtils;
import xyz.fusheng.model.core.service.ArticleService;
import xyz.fusheng.model.core.vo.ArticleVo;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @FileName: RabbitMQProducerTest
 * @Author: code-fusheng
 * @Date: 2021/1/26 6:38 下午
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
public class RabbitMQProducerTest {

    @Resource
    ArticleService articleService;

    @Test
    public void sendMessage() throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", true, false, false, null);
        ArticleVo articleVo = articleService.getById(146L);
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, articleVo.toString().getBytes());
        RabbitMQUtils.closeChannelAndConnection(channel, connection);
        System.out.println("消息发送成功");
    }
}
