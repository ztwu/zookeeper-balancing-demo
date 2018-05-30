package com.iflytek.edu.serviceImpl;

import com.iflytek.edu.ServerData;
import com.iflytek.edu.service.BalanceProvider;
import com.iflytek.edu.service.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 16:04
 * Description
 */

public class ClientImpl implements Client {

    private final BalanceProvider<ServerData> provider;
    private EventLoopGroup group = null;
    private Channel channel = null;

    public ClientImpl(BalanceProvider<ServerData> provider) {
        this.provider = provider;
    }

    public BalanceProvider<ServerData> getProvider() {
        return provider;
    }

    public void connect() throws Exception {
        try {
            ServerData sd = provider.getBalanceItem();
            System.out.println("connected to......"+sd.getHost()+":"+sd.getPort());
            System.out.println("it's balance."+sd.getBalance());

            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                         @Override
                         protected void initChannel(SocketChannel ch) throws Exception {
                              ch.pipeline().addLast(new ClientHandler());
                         }
                    });

            ChannelFuture cf = bootstrap.connect(sd.getHost(),sd.getPort()).sync(); // 异步连接服务器
            channel = cf.channel();
            System.out.println("started success..."); // 连接完成

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void disconnect() throws Exception {

        try {
            if(channel!=null){
                channel.close().syncUninterruptibly();
            }
            group.shutdownGracefully();
            group = null;

            System.out.println("disconnect..."); // 断开连接

        }catch (Exception e){

        }

    }
}
