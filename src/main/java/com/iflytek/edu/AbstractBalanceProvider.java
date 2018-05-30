package com.iflytek.edu;

import com.iflytek.edu.service.BalanceProvider;

import java.util.List;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/29
 * Time: 8:16
 * Description
 */

public abstract class AbstractBalanceProvider<T> implements BalanceProvider {
    //负载均衡算法，从一系列服务器找出最合适的
    protected abstract T balanceAlgorithm(List<T> items);

    //获取所有负载均衡的列表
    protected abstract List<T> getBalanceItems();

    public T getBalanceItem() {
        return balanceAlgorithm(getBalanceItems());
    }
}
