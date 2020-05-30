package xyz.fusheng.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @FileName: RedisTests
 * @Author: code-fusheng
 * @Date: 2020/5/25 20:01
 * @version: 1.0
 * Description:
 */

@SpringBootTest
public class RedisTests {

    @Test
    public void test1(){
        JedisPool pool =new JedisPool(this.getJedisPoolConfig(),"175.24.45.179",6390,3000,"123456");
        Jedis redis = pool.getResource();
        redis.set("name","1234");
        redis.close();
        System.out.println("ok");
    }

    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setLifo(true);
        config.setMinIdle(5);
        config.setMaxIdle(5);
        return config;
    }

}
