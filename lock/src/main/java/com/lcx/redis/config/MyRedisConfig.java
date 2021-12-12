package com.lcx.redis.config;

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
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setPassword("123456");
        // config.setThreads(24).useClusterServers()
        //         .addNodeAddress("redis://localhost:7001", "redis://localhost:7002", "redis://localhost:7003");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
