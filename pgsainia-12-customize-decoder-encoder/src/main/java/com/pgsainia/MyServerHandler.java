package com.pgsainia;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nifujia
 * @description
 * @date 2021/8/10
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始报备连接信息...");
        log.info("连接信息为, IP：{}，端口：{}", channel.localAddress().getHostString(), channel.localAddress().getPort());
        log.info("报备连接信息完毕...");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收时间：{}，接收到的消息为：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), msg);
        String returnMessage = "hi I'm ok";
        ctx.writeAndFlush(returnMessage);
    }
}
