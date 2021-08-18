package com.pgsainia.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nifujia
 * @description
 * @date 2021/8/18
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("读取等待，READER_IDLE...");
                String returnMessage = String.format("读取等待，netty client 你还在嘛?");
                ctx.writeAndFlush(returnMessage);
                // 关闭连接，模拟重连的情况
                ctx.close();
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("写入等待，WRITER_IDLE...");
                String returnMessage = String.format("写入等待， netty client 你还在嘛？");
                ctx.writeAndFlush(returnMessage);
            } else if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                log.info("全部时间，ALL_IDLE...");
                String returnMessage = String.format("全部时间， netty client 你还在嘛？");
                ctx.writeAndFlush(returnMessage);
            }
        }
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始报告连接信息...");
        log.info("连接信息为：有一个客户端建立链接了，Host：{}， 端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("报告连接信息完毕...");
        String returnMessage = String.format("我是 netty server， 我已经接收到你的连接信息了，时间：%s，Host：%s，端口：%s \r\n",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());

        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有一个客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server 接收到的信息为：{}", msg);
        String returnMessage = String.format("netty server 已收到信息，内容：%s \r\n", msg);
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
