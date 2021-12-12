package com.lcx.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.RetryOneTime;

/**
 * @author: lichunxia
 * @create: 2021-04-13 09:10
 */
public class MasterSelectDemo {

    static String connectString = "localhost:2181,localhost:2182,localhost:2183";
    static String path = "/curator_recipes_master_path";
    static CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, 5000, 3000,
            new RetryOneTime(1000));

    public static void main(String[] args) throws InterruptedException {
        client.start();
        LeaderSelector leaderSelector = new LeaderSelector(client, path, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为Master角色");
                Thread.sleep(1000);
                System.out.println("完成Master操作，释放Master权利");
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
