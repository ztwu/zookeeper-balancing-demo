package com.iflytek.edu.serviceImpl;

import com.iflytek.edu.ServerData;
import com.iflytek.edu.service.BalanceUpdateProvider;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 11:02
 * Description
 * 进行负载计数器的增加和减少功能
 */

public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {

    private String serverPath;//server节点路径
    private ZkClient zkClient;//连接客户端

    public DefaultBalanceUpdateProvider(String serverPath, ZkClient zkClient) {
        this.serverPath = serverPath;
        this.zkClient = zkClient;
    }

    public boolean addBalance(Integer step) {
        Stat stat = new Stat();
        ServerData serverData;
        while(true){
            try {
                serverData = zkClient.readData(serverPath,stat);
                serverData.setBalance(serverData.getBalance()+step);
                zkClient.writeData(serverPath,serverData,stat.getVersion());
                return true;
            }catch (ZkBadVersionException e){

            }catch (Exception e){
                return false;
            }
        }
    }

    public boolean reduceBalance(Integer step) {
        Stat stat = new Stat();
        ServerData serverData;
        while(true){
            try {
                serverData = zkClient.readData(serverPath,stat);
                serverData.setBalance(serverData.getBalance()-step);
                zkClient.writeData(serverPath,serverData,stat.getVersion());
                return true;
            }catch (ZkBadVersionException e){

            }catch (Exception e){
                return false;
            }
        }
    }
}
