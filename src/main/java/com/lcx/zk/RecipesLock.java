package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author: lichunxia
 * @create: 2021-04-13 09:18
 */
public class RecipesLock {

    static String connectString = "localhost:2181,localhost:2182,localhost:2183";
    static String path = "/curator_recipes_lock_path";
    static CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 10000, 3000,
            new RetryOneTime(1000));

    public static void main(String[] args) {
        client.start();
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, path);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        interProcessMutex.acquire();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = simpleDateFormat.format(new Date());
                    System.out.println(finalI + 1 + " 生成的订单号：" + orderNo);
                    try {
                        Thread.sleep(1000);
                        interProcessMutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDownLatch.countDown();
    }

}
