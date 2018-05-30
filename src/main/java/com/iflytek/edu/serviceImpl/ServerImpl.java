package com.iflytek.edu.serviceImpl;

import com.iflytek.edu.ServerData;
import com.iflytek.edu.ZookeeperRegistContext;
import com.iflytek.edu.service.RegistProvider;
import com.iflytek.edu.service.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2018/5/28
 * Time: 13:41
 * Description
 */

public class ServerImpl implements Server {

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private ChannelFuture cf;
    private String zkAddress;
    private String serverPath;
    private String currentServerPath;
    private ServerData sd;

    private volatile boolean binded = false;

    private final ZkClient zc;
    private final RegistProvider registProvider;

    private static final Integer SESSION_TIME_OUT = 10000;
    private static final Integer CONNECT_TIME_OUT = 10000;

    public void bind() {
        if(binded){
            return;
        }
        System.out.println("binding......");

        String mePath = serverPath.concat("/").concat(sd.getPort().toString());
        try {
            //注册服务
            registProvider.regist(new ZookeeperRegistContext(mePath,zc,sd));
            currentServerPath = mePath;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        bootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new ServerHandler(new DefaultBalanceUpdateProvider(currentServerPath,zc)));

            }
        }).option(ChannelOption.SO_BACKLOG, 1024);

        try {
            cf = bootstrap.bind(sd.getPort()).sync();
            binded = true;
            System.out.println(sd.getPort()+"...binded...");
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public ServerImpl(String zkAddress, String serverPath, ServerData sd) {
        this.zkAddress = zkAddress;
        this.serverPath = serverPath;
        this.sd = sd;
        this.zc = new ZkClient(this.zkAddress,SESSION_TIME_OUT,CONNECT_TIME_OUT,new SerializableSerializer());
        this.registProvider = new DefaultRegistProvider();
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getCurrentServerPath() {
        return currentServerPath;
    }

    public void setCurrentServerPath(String currentServerPath) {
        this.currentServerPath = currentServerPath;
    }

    public ServerData getSd() {
        return sd;
    }

    public void setSd(ServerData sd) {
        this.sd = sd;
    }
}
