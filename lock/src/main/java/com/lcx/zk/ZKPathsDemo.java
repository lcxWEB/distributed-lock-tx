package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author: lichunxia
 * @create: 2021-04-13 10:37
 */
public class ZKPathsDemo {

    static String connectString = "localhost:2181,localhost:2182,localhost:2183";
    static String path = "/curator_zkpath";
    static CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 5000, 3000,
            new RetryOneTime(1000));

    public static void main(String[] args) throws Exception {
        client.start();
        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();

        System.out.println(ZKPaths.fixForNamespace("zkpath", path));
        // 不创建节点
        System.out.println(ZKPaths.makePath(path, "/sub"));

        System.out.println(ZKPaths.getNodeFromPath(path + "/sub1"));

        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode(path + "/sub1");
        System.out.println(pathAndNode.getPath());
        System.out.println(pathAndNode.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        // 创建节点
        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);

        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path));

        Thread.sleep(10000);

        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path, true);

    }
}
