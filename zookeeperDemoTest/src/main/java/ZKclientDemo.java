import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZKclientDemo {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");//建立连接
        zkClient.create("/zkClientDemo", "RopleData", CreateMode.PERSISTENT);//创建目录并写入数据
        // 获取节点数据并输出
        String data = zkClient.readData("/zkClientDemo");
        System.out.println(data);
        //zkClient.delete("/zkClientDemo");//删除目录
        //zkClient.deleteRecursive("/zkClientDemo");//递归删除节目录
    }


}
