package xyz.fusheng.model.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


/**
 * @FileName: RabbitMQConfig
 * @Author: code-fusheng
 * @Date: 2021/1/27 10:04 上午
 * @Version: 1.0
 * @Description: RabbitMQ 配置类
 */

@Configuration
public class RabbitmqConfig {

    public static  final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private Environment environment;

    /**
     * RabbitMQ 链接工厂实例
     */
    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * RabbitMQ 消息监听器所在的容器工厂配置类实例
     */
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory singleListenerContainer() {
        // 定义容器所在的工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置工场所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置并发消费者的初始数量
        factory.setConcurrentConsumers(1);
        // 设置并发消费者的最大数量
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        // 设置消息签收模式 - AUTO 自动 MANUAL 手动
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者 高并发场景配置
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        // 定义容器所在的工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factoryConfigurer.configure(factory, connectionFactory);
        // 设置消息的确认模式 NONE 不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        // 并发消费者 初始实例数
        factory.setConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.concurrency", int.class));
        // 并发消费者 最大实例数
        factory.setMaxConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.max-concurrency", int.class));
        // 并发消费者 每个拉取的消息数量
        factory.setPrefetchCount(environment.getProperty("spring.rabbitmq.listener.prefetch", int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        // 设置消息分布类型
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        // 发送消息后返回确认信息
        connectionFactory.setPublisherReturns(true);
        // 构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                logger.info("消息发送成功: correlationData({}), ack({}), cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                logger.info("消息丢失:exchange({}), route({}), replyCode({}), replyText({}), message:{}", exchange,routingKey,replyCode,replyText,message));
        return rabbitTemplate;
    }

    @Bean(name = "logLoginQueue")
    public Queue logLoginQueue() {
        return new Queue(environment.getProperty("env")+".log.login.queue", true);
    }

    @Bean
    public DirectExchange logLoginExchange() {
        return new DirectExchange(environment.getProperty("env")+".log.login.exchange", true, false);
    }

    @Bean
    public Binding logLoginBinding() {
        return BindingBuilder.bind(logLoginQueue()).to(logLoginExchange()).with(environment.getProperty("env")+".log.login.routing-key");
    }





}
