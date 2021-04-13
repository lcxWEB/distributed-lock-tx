package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CuratorDemo {

    public static void main(String[] args) throws Exception {
        String connectString = "localhost:2181,localhost:2182,localhost:2183";
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 5000, 3000,
                new RetryOneTime(1000));
        client.start();
        String path = "/curator/c1";
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println(event.getResultCode() + "|" + event.getType());
                        countDownLatch.countDown();
                    }
                }, threadPool)
                .forPath(path, "111".getBytes());

        countDownLatch.await();
        threadPool.shutdown();
        // CuratorFramework client = CuratorFrameworkFactory
        //         .builder()
        //         .connectString(connectString)
        //         .sessionTimeoutMs(4000)
        //         .retryPolicy(new
        //                 ExponentialBackoffRetry(1000, 3))
        //         .namespace("base")
        //         .build();
        //
        // client.start();

        Stat stat = new Stat();

        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));

        System.out.println(stat);

        client.close();

    }
}
