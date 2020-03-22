import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

public class CuratorDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 2000));
        client.start();// 连接zookeeper服务器
        // 获取子节点，并进行监控
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
            public void process(WatchedEvent event) throws Exception {
                System.out.println("监控： " + event);
            }
        }).forPath("/");
        System.out.println(children);
        // 创建节点
        String result = client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath("/curatorDemo", "RopleData".getBytes());
        System.out.println(result);
        // 设置节点数据
        client.setData().forPath("/curatorDemo", "NewRopleData".getBytes());
        // 删除节点
        //System.out.println(client.checkExists().forPath("/curatorDemo"));
//        client.delete().withVersion(-1).forPath("/curatorDemo");
//        System.out.println(client.checkExists().forPath("/curatorDemo"));
        client.close();
        System.out.println("OK！");
    }
}
