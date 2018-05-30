package com.iflytek.edu;

import com.iflytek.edu.service.Server;
import com.iflytek.edu.serviceImpl.ServerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 14:56
 * Description
 */

public class ServerRunner {

    private static final Integer SERVER_QTY = 2;
    private static final String ZOOKEEPER_SERVER = "192.168.1.101:2181";
    private static final String SERVERS_APTH = "/servers";

    public static void main(String[] args){

        List<Thread> threadList = new ArrayList<Thread>();
        for(int i=0; i<SERVER_QTY;i++){
            final Integer count = i;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    ServerData sd = new ServerData();
                    sd.setBalance(0);
                    sd.setHost("127.0.0.1");
                    sd.setPort(5000+count);
                    Server server = new ServerImpl(ZOOKEEPER_SERVER,SERVERS_APTH,sd);
                    server.bind();
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for(int i =0 ;i<threadList.size(); i++){
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
