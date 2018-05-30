package com.iflytek.edu;

import org.I0Itec.zkclient.ZkClient;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 10:37
 * Description
 * 新建一个zookeeper上下文对象数据类，用于保存zookeeper中一些信息
 */

public class ZookeeperRegistContext {
    private String path;//server节点路径
    private ZkClient zkClient;//连接客户端
    private Object data;//数据

    public ZookeeperRegistContext(String path, ZkClient zkClient, Object data) {
        this.path = path;
        this.zkClient = zkClient;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
