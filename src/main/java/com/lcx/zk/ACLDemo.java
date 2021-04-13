package com.lcx.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: lichunxia
 * @create: 2021-04-12 21:44
 */
public class ACLDemo {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        String connectString = "localhost:2181,localhost:2182,localhost:2183";
        Watcher watcher = event -> {
            System.out.println("收到监听到的事件：" + event);
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                if (Watcher.Event.EventType.None == event.getType() && null == event.getPath()) {
                    //如果收到了服务端的响应事件，连接成功
                    countDownLatch.countDown();
                }
            }
        };
        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectString,
                    4000, watcher);
            zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
            String path = "/zk-book-auth";
            String path2 = "/zk-book-auth/child";

            Stat exists = zooKeeper.exists(path, false);
            if (null != exists) {
                zooKeeper.delete(path, -1);
            }

            zooKeeper.create(path, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
            zooKeeper.create(path2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

            ZooKeeper zooKeeper2 = new ZooKeeper(connectString,
                    4000, null);
            // KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /zk-book-auth
            // zooKeeper2.getData(path, false, null);

            // 删除失败，删除子节点必须要有相应权限
            // zooKeeper2.delete(path2, -1);

            ZooKeeper zooKeeper3 = new ZooKeeper(connectString,
                    4000, watcher);
            zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
            // 删除成功
            zooKeeper3.delete(path2, -1);

            ZooKeeper zooKeeper4 = new ZooKeeper(connectString,
                    4000, null);
            // 删除成功，不影响这个节点
            zooKeeper4.delete(path, -1);

        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
