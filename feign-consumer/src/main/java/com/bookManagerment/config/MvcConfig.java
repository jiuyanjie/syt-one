package com.bookManagerment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class MvcConfig {

    @Bean
    public Jedis jedis(){
        Jedis jedis = new Jedis();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setMinIdle(40);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"localhost");
        jedis.setDataSource(jedisPool);
//        jedis.auth("root");
        return jedis;
    }

}
