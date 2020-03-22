import org.apache.zookeeper.*;

import java.io.IOException;

public class ZookeeperDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //连接zookeeper并且注册监听器
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.toString());
            }
        });
        System.out.println("OK!");
        // 创建一个目录节点
        /**
         * CreateMode:
         *       PERSISTENT (持续的，相对于EPHEMERAL，不会随着client的断开而消失)
         *       PERSISTENT_SEQUENTIAL（持久的且带顺序的）
         *       EPHEMERAL (短暂的，生命周期依赖于client session)
         *       EPHEMERAL_SEQUENTIAL  (短暂的，带顺序的)
         * 节点访问权限说明：
         * 节点访问权限由List确定，但是有几个便捷的静态属性可以选择：
         * Ids.CREATOR_ALL_ACL：只有创建节点的客户端才有所有权限\
         * Ids.OPEN_ACL_UNSAFE：这是一个完全开放的权限，所有客户端都有权限
         * Ids.READ_ACL_UNSAFE：所有客户端只有读取的
         */
        zk.create("/zookeeperDemo", "RopleData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/zookeeperDemo/type1", "RopleData/type1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/zookeeperDemo", false, null)));

        // 取出子目录节点列表
        System.out.println(zk.getChildren("/zookeeperDemo", true));
        // 创建另外一个子目录节点
        zk.create("/zookeeperDemo/type2", "RopleData/type2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zk.getChildren("/zookeeperDemo", true));

        // 修改子目录节点type1的数据
        zk.setData("/zookeeperDemo/type1", "RopleData/type3".getBytes(), -1);
        byte[] datas = zk.getData("/zookeeperDemo/type1", true, null);

        String str = new String(datas, "utf-8");
        System.out.println(str);
        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
//        zk.delete("/zookeeperDemo/type1", -1);
//        zk.delete("/zookeeperDemo/type2", -1);
//        zk.delete("/zookeeperDemo", -1);
//        System.out.println(str);
        Thread.sleep(10000);
        zk.close();
        System.out.println("OK");
    }


}
