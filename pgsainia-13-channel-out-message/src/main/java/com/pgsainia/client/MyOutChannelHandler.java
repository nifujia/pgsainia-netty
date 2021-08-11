package com.pgsainia.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author nifujia
 * @description
 * @date 2021/8/11
 */
public class MyOutChannelHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        String returnMessage = "ChannelOutboundHandlerAdapter read 发来一条消息... \r\n";
        ctx.writeAndFlush(returnMessage);
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        String returnMessage = "ChannelOutboundHandlerAdapter write 发来一条消息... \r\n";
        ctx.writeAndFlush(returnMessage);
        super.write(ctx, msg, promise);
    }
}
