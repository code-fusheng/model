package xyz.fusheng.model.common.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName: MyBatisPlusConfig
 * @Author: code-fusheng
 * @Date: 2020/5/30 13:58
 * @version: 1.0
 * Description: MP配置类
 */

@Configuration
public class MyBatisPlusConfig {

    /**
     * 注册 MyBatisPlus 乐观锁拦截器
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    /**
     * 注册 MyBatisPlus Page拦截器
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

}
