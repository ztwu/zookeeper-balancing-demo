package com.iflytek.edu.service;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 16:02
 * Description
 */
public interface Client {
    public void connect() throws Exception;//连接到服务器
    public void disconnect() throws Exception;//断开到服务器的连接
}
