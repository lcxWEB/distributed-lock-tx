package com.lcx.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ConnectionDemo {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            String connectString = "localhost:2181,localhost:2182,localhost:2183";
            // 客户端注册Watcher时会将这个watcher对象存储在客户端的WatcherManager中
            Watcher watcher = event -> {
                System.out.println("收到监听到的事件：" + event);
                if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                    if (EventType.None == event.getType() && null == event.getPath()) {
                        //如果收到了服务端的响应事件，连接成功
                        countDownLatch.countDown();
                    } else if (EventType.NodeChildrenChanged == event.getType()) {
                        try {
                            List<String> children = zooKeeper.getChildren(event.getPath(), true);
                            System.out.println("监听到子节点变化：" + children);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else if (EventType.NodeDataChanged == event.getType()) {
                        try {
                            Stat stat = new Stat();
                            byte[] data = zooKeeper.getData(event.getPath(), true, stat);
                            System.out.println("监听到数据变化：" + stat + "；新数据：" + new String(data));
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            };

            zooKeeper = new ZooKeeper(connectString,
                    4000, watcher);
            countDownLatch.await();
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPasswd = zooKeeper.getSessionPasswd();
            // 收到监听到的事件：WatchedEvent state:Expired type:None path:null
            // zooKeeper = new ZooKeeper(connectString, 5000, watcher, 1L, "test".getBytes());
            // Thread.sleep(10000);
            // zooKeeper = new ZooKeeper(connectString, 5000, watcher, sessionId, sessionPasswd);
            System.out.println(zooKeeper.getState());

            String path = "/foo-3";
            Stat exists = zooKeeper.exists(path, false);
            if (null != exists) {
                zooKeeper.delete(path, -1, new AsyncCallback.VoidCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx) {
                        System.out.println("删除结果：" + rc + "|" + path + "|" + ctx);
                    }
                }, "context");
            }
            // zooKeeper.create("/foo", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            // String s = zooKeeper.create("/foo", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            // String s = zooKeeper.create("/foo-", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            // String s = zooKeeper.create("/foo-", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // System.out.println("创建节点成功：" + s);
            zooKeeper.create(path, "000".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.Create2Callback() {
                @Override
                public void processResult(int rc, String path, Object ctx, String name, Stat stat) {
                    System.out.println("创建回调：" + rc + " ｜ " + path + " ｜ " + ctx + " ｜ " + name + " ｜ " + stat);
                }
            }, "I am context");

            // 获取节点值并注册监听
            zooKeeper.getData(path, true, new Stat());

            // 同步获取子节点，并注册监听
            List<String> children = zooKeeper.getChildren(path, true);
            System.out.println("子节点：" + children);

            zooKeeper.create(path + "/c1", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, String name) {
                    System.out.println("创建回调：" + rc + " ｜ " + path + " ｜ " + ctx + " ｜ " + name);
                }
            }, "I am context");
            Thread.sleep(1000);

            // 再创建一个子节点
            zooKeeper.create(path + "/c2", "2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

            zooKeeper.setData(path, "test".getBytes(), -1);

            zooKeeper.setData(path, "666".getBytes(), -1, new AsyncCallback.StatCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, Stat stat) {
                    System.out.println("监听到数据变更：" + rc + "|" + path + "|" + ctx + "|" + stat);
                }
            }, "context");

            zooKeeper.getChildren(path, true, new AsyncCallback.Children2Callback() {
                @Override
                public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                    System.out.println("异步获取到子节点：" + rc + " | " + path + " | " + ctx + " | " + children + " | " + stat);
                }
            }, "context");

            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
