package com.iflytek.edu;

import java.io.Serializable;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 10:42
 * Description
 */

public class ServerData implements Serializable,Comparable<ServerData> {

    private Integer balance;//负载数

    private String host;//服务器地址

    private Integer port;//端口号

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int compareTo(ServerData o) {
        return this.getBalance().compareTo(o.getBalance());
    }
}
