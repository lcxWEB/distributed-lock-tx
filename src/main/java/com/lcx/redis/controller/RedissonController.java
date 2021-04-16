package com.lcx.redis.controller;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: lichunxia
 * @create: 2021-01-25 20:27
 */
@RestController
public class RedissonController {


    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping("/hello")
    public String hello() {
        // 获取一把锁
        RLock lock = redissonClient.getLock("my-lock");

        RLock lock1= redissonClient.getLock("my-lock1");


        // 加锁，阻塞式等待。默认30秒，会自动续期。

        lock.lock();
        lock1.lock();

        // 10秒自动解锁，自动解锁时间一定要大于业务的执行时间
        // lock.lock(30, TimeUnit.SECONDS);

        try {
            System.out.println("加锁成功，执行业务..." + Thread.currentThread() + "ThreadId: " + Thread.currentThread().getId());
            System.out.println(LocalDateTime.now());
            Thread.sleep(60000);
            System.out.println(LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁..." + Thread.currentThread().getId());
            // 超时锁自动过期后，尝试解锁非自己加的锁
            // java.lang.IllegalMonitorStateException: attempt to unlock lock,
            // not locked by current thread by node id: 9cf3d0e2-6e15-4205-9ffe-1569ce78b9ef thread-id: 62
            lock.unlock();
        }

        return "hello";
    }

    @GetMapping("/write")
    public String write() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        // 加写锁
        RLock rLock = lock.writeLock();
        String s = "";
        try {
            rLock.lock();
            System.out.println("写锁加锁成功..." + Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("写锁释放..." + Thread.currentThread().getId());
        }
        return s;
    }

    @GetMapping("/read")
    public String read() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        // 加读锁
        RLock rLock = lock.readLock();
        String s = "";
        try {
            rLock.lock();
            System.out.println("读锁加锁成功..." + Thread.currentThread().getId());
            s = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("读锁释放..." + Thread.currentThread().getId());
        }
        return s;
    }


}
