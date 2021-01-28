package xyz.fusheng.model;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.fusheng.model.common.utils.RabbitMQUtils;
import xyz.fusheng.model.core.vo.ArticleVo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @FileName: RabbitMQConsumerTest
 * @Author: code-fusheng
 * @Date: 2021/1/26 6:49 下午
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
public class RabbitMQConsumerTest {



    @Test
    public void receiveMessage() throws IOException, TimeoutException {

        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", true, false, false, null);
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者接收到消息：" + new String(body));;
                ArticleVo articleVo = JSONObject.parseObject(new String(body), ArticleVo.class);
                System.out.println(articleVo);
            }
        });
        System.in.read();

        //        channel.close();
        //        connection.close();
        //        System.out.println("消息消费成功");

    }

}
