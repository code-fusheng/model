package xyz.fusheng.model.common.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @FileName: RabbitMQUtils
 * @Author: code-fusheng
 * @Date: 2021/1/10 4:48 下午
 * @Version: 1.0
 * @Description: RabbitMQ - 消息中间件工具类
 */

@Component
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;

    static {
        /**
         * 创建连接工厂
         */
        connectionFactory = new ConnectionFactory();
        /**
         * 设置相关参数
         */
        connectionFactory.setHost("175.24.45.179");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("code-fusheng");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/model");
    }

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
        try {
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭信道和连接
     * @param channel
     * @param connection
     */
    public static void closeChannelAndConnection(Channel channel, Connection connection) {
        try {
            if (null!=channel) {
                channel.close();
            }
            if (null!=connection) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
