package com.yncc.wisdom.lis.nettyClient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Gjing
 * 客户端初始化器
 **/
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new com.yncc.wisdom.lis.nettyClient.NettyClientHandler());
    }
}
