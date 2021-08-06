package com.pgsainia;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/6
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当有客户端连接时，添加到 ChannelGroup 通信组
        ChannelHandler.channelGroup.add(ctx.channel());
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        log.info("开始报告连接信息...");
        log.info("连接信息：有一个客户端建立连接了，连接IP：{}，端口：{}", socketChannel.remoteAddress().getHostString(),
                socketChannel.remoteAddress().getPort());
        log.info("连接信息报告完毕...");

        String returnMessage = String.format("我是 netty server，我已经接收到客户端的连接了，连接IP：%s，端口：%s",
                socketChannel.remoteAddress().getHostString(),
                socketChannel.remoteAddress().getPort());
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 当有客户端断开连接是，从 ChannelGroup 通讯组中删除
        ChannelHandler.channelGroup.remove(ctx.channel());
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("客户端断开连接了，IP：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收信息
        log.info("接收到信息：{}", msg);
        // 群发信息，不需要编解码
        String returnMessage = String.format("我是 netty server， 我已经接收到你的信息，信息为：%S", msg);
        ChannelHandler.channelGroup.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("发生异常，信息为：{}", cause.getMessage());
    }
}
