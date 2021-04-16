package com.lcx.redis;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.config.Config;

/**
 * @author: lichunxia
 * @create: 2021-04-16 23:27
 */
public class RedissonRedLockDemo {

    public static void main(String[] args) {
        String lockKey = "myLock";
        Config config = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6379");
        Config config2 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6380");
        Config config3 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6381");

        RLock lock = Redisson.create(config).getLock(lockKey);
        RLock lock2 = Redisson.create(config2).getLock(lockKey);
        RLock lock3 = Redisson.create(config3).getLock(lockKey);

        RedissonRedLock redLock = new RedissonRedLock(lock, lock2, lock3);

        try {
            redLock.lock();
        } finally {
            redLock.unlock();
        }
    }
}
