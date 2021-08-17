package com.pgsainia.server;

import com.pgsainia.msg.Request;
import com.pgsainia.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;
        Response response = new Response();
        response.setResponseId(request.getRequestId());
        response.setContent(request.getContent() + "，服务端接收到消息，处理成功....");
        ctx.writeAndFlush(response);

        // 释放
        ReferenceCountUtil.release(request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
