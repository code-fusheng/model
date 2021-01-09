package xyz.fusheng.model.test.rabbitmq;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @FileName: Consumer
 * @Author: code-fusheng
 * @Date: 2021/1/8 4:28 下午
 * @Version: 1.0
 * @Description: 消息消费者
 */

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("code-fusheng");
        factory.setPassword("123456");
        factory.setHost("localhost");
        // 建立到代理服务器连接
        Connection connection = factory.newConnection();
        // 获得信道
        Channel channel = connection.createChannel();
        // 声明交换器
        String exchangeName = "test-exchange";
        channel.exchangeDeclare(exchangeName, "direct", true);
        // 声明队列
        String queueName = channel.queueDeclare().getQueue();

        String message = "QAQ";

        // 绑定队列，通过键 rout 将队列和交换器绑定起来
        channel.queueBind(queueName, exchangeName, message);

        while (true) {
            // 消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName, autoAck, consumerTag ,new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键:" + routingKey);
                    System.out.println("消费的内容类型:" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    // 确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费的消息体内容:");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);
                }
            });
        }
    }
}
