package com.yncc.wisdom.lis.nettyService;

import com.yncc.wisdom.lis.service.UritParse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Gjing
 *
 * netty服务端处理器
 **/

@Slf4j
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger=LoggerFactory.getLogger(NettyServerHandler.class);

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("客户端连接:"+ctx);
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        Channel channel=ctx.channel();
        logger.info("通讯接收到消息.");
        logger.info("客户端地址:"+channel.remoteAddress());
        ByteBuf byteBuf=Unpooled.copiedBuffer(msg.toString(),CharsetUtil.UTF_8);
        String message=byteBuf.toString(CharsetUtil.UTF_8);

        UritParse uritParse=new UritParse();
        logger.info("客户端消息:"+ctx);
        uritParse.parse(message);
        try {
            int readable=byteBuf.readableBytes();
            logger.info("消息长度:"+readable);
			ctx.close();
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        super.channelReadComplete(ctx);
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        cause.printStackTrace();
        ctx.close();
    }
}
