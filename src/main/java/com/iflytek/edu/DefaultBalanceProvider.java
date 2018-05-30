package com.iflytek.edu;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/29
 * Time: 8:29
 * Description
 */

public class DefaultBalanceProvider extends AbstractBalanceProvider<ServerData> {

    private String zkServer;//zookeeper服务器地址
    private String serverPath;//zookeeper servers path

    private ZkClient zkClient;

    private static final Integer SESSION_TIME_OUT = 5000;
    private static final Integer CONNECT_TIME_OUT = 5000;

    public DefaultBalanceProvider(String zkServer, String serverPath) {
        this.zkServer = zkServer;
        this.serverPath = serverPath;
        this.zkClient = new ZkClient(zkServer,SESSION_TIME_OUT,CONNECT_TIME_OUT,new SerializableSerializer());
    }

    protected ServerData balanceAlgorithm(List<ServerData> items) {
        if(items.size()>0){
            Collections.sort(items);
            return items.get(0);
        }
        return null;
    }

    protected List<ServerData> getBalanceItems() {
        List<String> childList = zkClient.getChildren(this.serverPath);
        List<ServerData> serverDataList = new ArrayList<ServerData>();
        for(String item:childList){
            ServerData sd = zkClient.readData(serverPath+"/"+item);
            serverDataList.add(sd);
        }
        return serverDataList;
    }
}
