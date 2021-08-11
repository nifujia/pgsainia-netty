package com.pgsainia.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author nifujia
 * @description
 * @date 2021/8/11
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();

        log.info("开始报告连接...");
        log.info("有一个客户端连接上来了，连接IP：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接报告结束...");

        String returnMessage = String.format("我是 netty server, 一个客户端连接上来了，时间：%s，IP：%s，端口：%s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());
        //ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有一个客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到的信息为：{}", msg);
        String returnMessage = String.format("我是 netty server， 我已经接收到你的消息，消息内容为：%s%s", msg, "\r\n");
        //ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("发生错误了，错误信息为：{}", cause.getMessage());
    }
}
