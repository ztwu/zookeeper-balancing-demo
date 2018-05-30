package com.iflytek.edu.service;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 9:58
 * Description 添加或者减少负载
 */

public interface BalanceUpdateProvider {

    /**
     * 添加负载
     * @param step
     */
    public boolean addBalance(Integer step);

    /**
     * 减少负载
     * @param step
     */
    public boolean reduceBalance(Integer step);

}
