package xyz.fusheng.model.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.fusheng.model.common.utils.IdWorker;

/**
 * 用于将一些实体类放入Spring容器
 *
 * @Author: code-fusheng
 * @Date: 2020/2/9 14:37
 * @Version 1.0
 */
@Configuration
public class BeanConfig {

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
