package com.yncc.wisdom.lis.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Gjing
 **/
@Slf4j
public class NettyClient {

    public void start(){
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1",10000).sync();
//            log.info("客户端连接成功....");
            //发送消息z
//            String putstr = "01 0d 0a\r\n";
//            future.channel().writeAndFlush(NettyUtils.string2HexString(putstr));
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}

