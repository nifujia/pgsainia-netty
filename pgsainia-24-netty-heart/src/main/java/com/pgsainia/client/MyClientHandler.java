package com.pgsainia.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/18
 */
@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始跟报告跟 netty server 的连接信息...");
        log.info("连接信息，Host：{}，port：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接信息报告完毕...");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("客户端断开连接了，连接信息 Host：{}， port：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        // 重连
        log.info("断开连接之后，开始重连...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient().connect("127.0.0.1", 9999);
                    log.info("netty client 重连成功....");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error("netty client 重连失败....");
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到 netty server 的回复信息：{}", msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("失败，错误信息：{}", cause.getMessage());
        ctx.close();
    }
}
