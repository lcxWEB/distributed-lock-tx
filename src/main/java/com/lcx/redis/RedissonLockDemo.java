package com.lcx.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author: lichunxia
 * @create: 2021-04-16 12:32
 */
public class RedissonLockDemo {

    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://localhost:7001", "redis://localhost:7002", "redis://localhost:7003");
        RedissonClient redissonClient = Redisson.create(config);

        // 还可以getFairLock(), getReadWriteLock()
        RLock lock = redissonClient.getLock("my-lock");

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("线程开始..." + Thread.currentThread() + "ThreadId: " + Thread.currentThread().getId());
                    lock.lock();
                    // a43c8a3d-8ca8-4816-971b-75e421975663:45
                    // a43c8a3d-8ca8-4816-971b-75e421975663:49
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
            }).start();
        }
        // boolean isLock;
        // try {
        //     isLock = redLock.tryLock();
        //     // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
        //     isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
        //     if (isLock) {
        //         //TODO if get lock success, do something;
        //     }
        // } catch (Exception e) {
        // } finally {
        //     // 无论如何, 最后都要解锁
        //     redLock.unlock();
        // }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
