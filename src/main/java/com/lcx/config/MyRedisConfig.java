package com.lcx.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LiChunxia
 * @Date 2021/1/25  17:33
 */
@Configuration
public class MyRedisConfig {

    /**
     * 所有对Redisson的使用都通过RedissonClient
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://47.105.150.11:6379").setPassword("3edc4rfv");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
