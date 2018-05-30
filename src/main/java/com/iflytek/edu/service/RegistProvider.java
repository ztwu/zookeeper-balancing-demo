package com.iflytek.edu.service;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 9:51
 * Description
 */
public interface RegistProvider {
    /**
     * 注册
     * @param context
     * @throws Exception
     */
    public void regist(Object context) throws Exception;

    /**
     * 取消注册
     * @param context
     * @throws Exception
     */
    public void unregist(Object context) throws Exception;

}
