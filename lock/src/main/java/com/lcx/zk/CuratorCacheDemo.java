package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.RetryOneTime;

/**
 * @author: lichunxia
 * @create: 2021-04-13 08:35
 */
public class CuratorCacheDemo {
    static String connectString = "localhost:2181,localhost:2182,localhost:2183";
    // static String path = "/curator";
    static String path = "/curator_recipes_master_path";
    static String cpath = "/curator/nodecache";
    static CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 5000, 3000,
            new RetryOneTime(1000));


    public static void main(String[] args) throws Exception {
        client.start();
        CuratorCache curatorCache = CuratorCache.build(client, path);
        curatorCache.start();
        curatorCache.listenable().addListener(new CuratorCacheListener() {
            @Override
            public void event(Type type, ChildData oldData, ChildData data) {
                System.out.println("类型: " + type + "，OldData: " + oldData + ", data: " + data + ", 节点值: " + new String(data.getData()));
            }
        });
        // client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(cpath, "init".getBytes());
        // Thread.sleep(1000);
        // client.setData().forPath(cpath, "u".getBytes());
        // Thread.sleep(1000);
        // client.setData().forPath(path, "888".getBytes());
        // Thread.sleep(1000);
        // client.delete().forPath(cpath);
        // Thread.sleep(1000);
        // client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
