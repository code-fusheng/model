package xyz.fusheng.model;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void testJedis() {
        JedisPool pool = new JedisPool(this.getJedisPoolConfig(), "42.192.222.62", 36380, 3000, "Xcode-redis?");
        Jedis redis = pool.getResource();
        redis.set("name", "1234");
        String s = redis.get("name");
        System.out.println(s);
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
