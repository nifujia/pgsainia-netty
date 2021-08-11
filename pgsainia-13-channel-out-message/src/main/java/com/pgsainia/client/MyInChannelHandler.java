package com.pgsainia.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/11
 */
@Slf4j
public class MyInChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始报告连接信息...");
        log.info("客户端已经建立连接了，远程的服务器 IP：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接报告结束...");

        // 通知服务端，建立连接成功
        String returnMessage = String.format("我是 netty client，我已经跟服务器建立连接了，IP：%s，端口：%s %s", channel.localAddress().getHostString(),
                channel.localAddress().getPort(), "\r\n");
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("断开连接...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("读取到服务端的信息：{}", msg);
        String returnMessage = String.format("我已经接收到你的信息，内容为：%s", msg);
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("netty client 发生异常了，异常信息：");
    }
}
