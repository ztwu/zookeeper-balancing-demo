package com.iflytek.edu.serviceImpl;

import com.iflytek.edu.ZookeeperRegistContext;
import com.iflytek.edu.service.RegistProvider;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 9:56
 * Description
 * 服务器注册和取消注册方法
 * 主要是在zookeeper中创建临时节点和删除临时节点的操作
 */

public class DefaultRegistProvider implements RegistProvider {
    public void regist(Object context) throws Exception {
        ZookeeperRegistContext zookeeperRegistContext = (ZookeeperRegistContext)context;
        String path = zookeeperRegistContext.getPath();
        ZkClient zkClient = zookeeperRegistContext.getZkClient();
        try {
            //在zookeeper上创建临时节点，一旦这个服务端出问题异常，失去与zookeeper的心跳连接，就会自动删除掉这个临时节点
            zkClient.createEphemeral(path,zookeeperRegistContext.getData());
        }catch (ZkNoNodeException e){
            zkClient.createEphemeral(path.substring(0,path.lastIndexOf("/")),true);
            regist(zookeeperRegistContext);
        }
    }

    public void unregist(Object context) throws Exception {
        ZookeeperRegistContext zookeeperRegistContext = (ZookeeperRegistContext)context;
        String path = zookeeperRegistContext.getPath();
        ZkClient zkClient = zookeeperRegistContext.getZkClient();
        try {
            //也可以主动删除
            zkClient.deleteRecursive(path);
        }catch (ZkNoNodeException e){
            e.printStackTrace();
        }
    }
}
