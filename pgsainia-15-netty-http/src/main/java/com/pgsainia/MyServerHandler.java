package com.pgsainia;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/12
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            DefaultHttpRequest defaultHttpRequest = (DefaultHttpRequest) msg;
            log.info("URI：{}", defaultHttpRequest.uri());
            log.info("msg：{}", msg);
        }

        if (msg instanceof HttpContent) {
            LastHttpContent lastHttpContent = (LastHttpContent) msg;
            ByteBuf content = lastHttpContent.content();
            if (!(content instanceof EmptyByteBuf)) {
                byte[] bytes = new byte[content.readableBytes()];
                content.readBytes(bytes);
                log.info("接收到的信息为：{}", new String(bytes, Charset.forName("UTF-8")));
            }
        }

        String responseMessage = "我是 response content，我是可爱的蓝精灵...";
        FullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(responseMessage.getBytes(Charset.forName("UTF-8"))));

        defaultHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        defaultHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, defaultHttpResponse.content().readableBytes());
        defaultHttpResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(defaultHttpResponse);
        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("netty server 发生错误了，错误信息为：{}", cause.getMessage());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}

