package xyz.fusheng.model.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @FileName: Producer
 * @Author: code-fusheng
 * @Date: 2021/1/8 4:19 下午
 * @Version: 1.0
 * @Description: 消息生产者
 */

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("code-fusheng");
        factory.setPassword("123456");
        // 设置 RabbitMQ 地址
        factory.setHost("localhost");
        // 建立到代理服务器连接
        Connection connection = factory.newConnection();
        // 获得信道
        Channel channel = connection.createChannel();
        // 声明交换器
        String exchangeName = "test-exchange";
        channel.exchangeDeclare(exchangeName, "direct", true);

        String message = "Hello RabbitMQ By Code-fusheng";

        // 发布信息
        byte[] messageBodyBytes = message.getBytes();

        for (int i = 0; i < 10; i ++) {
            channel.basicPublish(exchangeName, message, null, messageBodyBytes);
        }
        channel.close();
        connection.close();

    }

}
