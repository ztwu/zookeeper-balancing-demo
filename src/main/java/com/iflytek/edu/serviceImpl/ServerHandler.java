package com.iflytek.edu.serviceImpl;

import com.iflytek.edu.service.BalanceUpdateProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 14:39
 * Description
 * 处理服务器端和客户端的通信的类，主要进行负载的增加和减少操作
 */

public class ServerHandler extends ChannelHandlerAdapter {

    private final BalanceUpdateProvider balanceUpdateProvider;
    private static final Integer BALANCE_STEP = 1;

    public ServerHandler(BalanceUpdateProvider balanceUpdateProvider) {
        this.balanceUpdateProvider = balanceUpdateProvider;
    }

    public BalanceUpdateProvider getBalanceUpdateProvider() {
        return balanceUpdateProvider;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("客户端连上了...");
        balanceUpdateProvider.addBalance(BALANCE_STEP);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("客户端断开了...");
        balanceUpdateProvider.reduceBalance(BALANCE_STEP);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf=(ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        System.out.println("服务器端接收的消息："+new String(req));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        ctx.close();
    }

}
