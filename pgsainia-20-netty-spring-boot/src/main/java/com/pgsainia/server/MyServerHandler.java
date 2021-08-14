package com.pgsainia.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nifujia
 * @date 2021/8/14 19:47
 * @description
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(MyServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();

        log.info("开始报告连接信息...");
        log.info("连接信息：有一个客户端连接上来了，host：{}，端口：{}", channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());
        log.info("报告连接信息结束...");
        String returnMessage = String.format("我是 netty server，我已经接受到你的连接信息了，时间是：%s, host：%s，端口：%s %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort(), "\r\n");
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接了..");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接受到的信息为：{}", msg);
        String returnMessage = String.format("我已经接受到你的信息了，你发送的信息为：%s %s", msg, "\r\n");
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}