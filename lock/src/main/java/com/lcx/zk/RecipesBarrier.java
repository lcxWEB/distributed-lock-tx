package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: lichunxia
 * @create: 2021-04-13 10:28
 */
public class RecipesBarrier {
    static String connectString = "localhost:2181,localhost:2182,localhost:2183";

    static String barrierPath = "/curator_recipes_barrier_path";
    static DistributedBarrier barrier;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectString)
                            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                            .build();
                    client.start();
                    barrier = new DistributedBarrier(client, barrierPath);
                    System.out.println(Thread.currentThread().getName() + " 号 barrier 设置");
                    try {
                        // 设置Barrier
                        barrier.setBarrier();
                        // 等待Barrier的释放
                        barrier.waitOnBarrier();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("启动。。。");

                }
            }).start();
        }
        Thread.sleep(2000);
        try {
            // 在主线程释放Barrier，同时出发所有等待该Barrier的5个线程同时进行各自的逻辑
            barrier.removeBarrier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
