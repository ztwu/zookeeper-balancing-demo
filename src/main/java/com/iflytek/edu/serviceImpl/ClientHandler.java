package com.iflytek.edu.serviceImpl;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/29
 * Time: 16:18
 * Description
 */

public class ClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
     public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channelActive..");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8)); // 必须有flush

        // 必须存在flush
        // ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
        // ctx.flush();
     }

   @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
       ctx.close();
    }

    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client channelRead..");
        ByteBuf buf = msg.readBytes(msg.readableBytes());
        System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
        //ctx.channel().close().sync();// client关闭channel连接
    }
}