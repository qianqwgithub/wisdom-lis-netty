package com.yncc.wisdom.lis.nettyService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Gjing
 *
 * netty服务初始化器
 **/

@Slf4j
@Data
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel>{
    private String DELIMITER;
    private int MAXFRAMELENGTH;

    public ServerChannelInitializer(String delimiter,int maxFrameLength){
        DELIMITER=delimiter;
        MAXFRAMELENGTH=maxFrameLength;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        //分隔符
        ByteBuf delimiterByteBuf=Unpooled.copiedBuffer(DELIMITER.getBytes());
        //设置log监听器，并且日志级别为debug，方便观察运行流程
        socketChannel.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(MAXFRAMELENGTH,delimiterByteBuf));
        //添加编解码
        socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new NettyServerHandler());
    }
}
