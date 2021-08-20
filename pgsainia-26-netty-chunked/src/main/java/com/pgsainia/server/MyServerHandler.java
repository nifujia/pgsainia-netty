package com.pgsainia.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/20
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始报告客户端连接...");
        log.info("有一个客户端连接了，连接 host：{}， 端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接报告完毕...");

        String returnMessage = String.format("我是 netty server，我已经接收到你连接信息，Host ：%s，端口：%s \r\n", channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有一个客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到的信息为：{}", msg);
        String returnMessage = String.format("netty server 接收到的信息为：%s \r\n", msg);
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
