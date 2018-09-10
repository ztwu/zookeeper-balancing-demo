package com.iflytek.edu;

import com.iflytek.edu.service.BalanceProvider;
import com.iflytek.edu.service.Client;
import com.iflytek.edu.service.Server;
import com.iflytek.edu.serviceImpl.ClientImpl;
import com.iflytek.edu.serviceImpl.ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/29
 * Time: 8:49
 * Description
 */

public class ClientRunner {

    private static final Integer SERVER_QTY = 5;
    private static final String ZOOKEEPER_SERVER = "192.168.1.101:2181";
    private static final String SERVERS_APTH = "/servers";

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Thread> threadList = new ArrayList<Thread>();
        final List<Client> clientList = new ArrayList<Client>();
        final BalanceProvider<ServerData> balanceProvider = new DefaultBalanceProvider(ZOOKEEPER_SERVER,SERVERS_APTH);
        for(int i=0; i<SERVER_QTY;i++){
            final Integer count = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Client client = new ClientImpl(balanceProvider);
                    clientList.add(client);
                    try {
                        client.connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
            Thread.sleep(2000);
        }

        System.out.println("输出回车键退出");
        new BufferedReader(new InputStreamReader(System.in)).readLine();

    }

}
