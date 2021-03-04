package com.yncc.wisdom.lis.nettyClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Gjing
 *
 * 客户端处理器
 **/
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private GenericFutureListener listener;
    private ScheduledFuture<?> schedule;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("客户端Active .....");
        sendMessageTimer(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        log.info("与服务器断开连接 .....");
        //移除监听器
        schedule.removeListener(listener);
    }

    /**
     * 定时发送消息
     * @param channel
     */
    private void sendMessageTimer(Channel channel) {
        //每10秒查询并发送一次，如果想让发送更加准时，可以将查询的时间设得稍短一点
        int heartBeatInterval = 10;
        //为eventLoop添加异步定时任务，heartBeatInterval秒后发送指令
        schedule=channel.eventLoop().schedule(()->{
            if (channel.isActive()){
                String instruction="测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";
                byte[] hexBytes=instruction.getBytes();
                ByteBuf buffer=Unpooled.buffer(instruction.getBytes().length);
                buffer.writeBytes(hexBytes);
                channel.writeAndFlush(buffer);
            }else{
                System.out.println("与服务器的连接已断开");
            }
        },heartBeatInterval, TimeUnit.SECONDS);
        //定义监听器
        listener = future -> {
            sendMessageTimer(channel);
        };
        //为异步定时任务添加监听器
        schedule.addListener(listener);
    }

    //将16进制的字符串转成字符数组
    private byte[] getHexBytes(String str) {
        str = str.replaceAll(" ", "");
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("客户端收到消息: {}", msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
