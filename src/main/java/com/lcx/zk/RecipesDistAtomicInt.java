package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;

/**
 * @author: lichunxia
 * @create: 2021-04-13 10:11
 */
public class RecipesDistAtomicInt {

    static String connectString = "localhost:2181,localhost:2182,localhost:2183";
    static String path = "/curator_recipes_distatomicint_path";
    static CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 10000, 3000,
            new RetryOneTime(1000));

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger distributedAtomicInteger = new DistributedAtomicInteger(client, path, new RetryNTimes(3, 100));
        AtomicValue<Integer> rc = distributedAtomicInteger.add(8);
        System.out.println("结果：" + rc.succeeded());
    }
}
