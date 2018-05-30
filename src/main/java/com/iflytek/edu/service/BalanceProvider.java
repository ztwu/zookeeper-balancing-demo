package com.iflytek.edu.service;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 16:05
 * Description
 */
public interface BalanceProvider<T> {
    public T getBalanceItem();//获取所有服务器的负载值
}
