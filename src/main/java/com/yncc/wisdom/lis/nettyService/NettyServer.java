package com.yncc.wisdom.lis.nettyService;

import com.yncc.wisdom.lis.entity.NettyConnection;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.Delimiter;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Gjing
 * <p>
 * 服务启动监听器
 **/
@Component
public class NettyServer{
    private static final Logger logger= LoggerFactory.getLogger(NettyServer.class);

    public void start(InetSocketAddress socketAddress,String delimiter,int maxFrameLength){
        //new 一个主线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        //new 一个工作线程组
        EventLoopGroup workGroup=new NioEventLoopGroup(200);

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new com.yncc.wisdom.lis.nettyService.ServerChannelInitializer(delimiter, maxFrameLength))
                    .localAddress(socketAddress)
                    //设置队列大小
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定端口,开始接收进来的连接
            try {
                bootstrap.childHandler(new com.yncc.wisdom.lis.nettyService.ServerChannelInitializer(delimiter,maxFrameLength));
                ChannelFuture future = bootstrap.bind(socketAddress).sync();
                logger.info("服务器启动开始监听端口: " + socketAddress.getPort());
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //关闭主线程组
                bossGroup.shutdownGracefully();
                //关闭工作线程组
                workGroup.shutdownGracefully();
            }
    }
}
