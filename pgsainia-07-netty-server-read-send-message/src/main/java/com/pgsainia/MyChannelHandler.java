package com.pgsainia;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author nifujia
 * @description
 * @date 2021/8/4
 */
@Slf4j
public class MyChannelHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始报告连接信息...");
        log.info("连接信息：有一个客户端建立连接了...");
        log.info("连接 IP：{}", channel.remoteAddress().getHostString());
        log.info("连接端口：{}", channel.remoteAddress().getPort());
        log.info("连接信息报告完毕....");

        // 通知客户端连接建立成功
        String successMessage = "连接建立成功，时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.whiteAndFlush(channel, successMessage);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到信息为：{}", msg.toString());
        String message = String.format("Netty Server 收到信息了，信息内容为：%s，时间：%s",
                msg, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        this.whiteAndFlush((SocketChannel) ctx.channel(), message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接了...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("异常信息：{}", cause.getMessage());
    }

    /**
     * 回复信息
     *
     * @param channel
     * @param message
     */
    private void whiteAndFlush(SocketChannel channel, String message) {
        ByteBuf buffer = Unpooled.buffer(message.getBytes().length);
        buffer.writeBytes(message.getBytes(Charset.forName("GBK")));
        channel.writeAndFlush(buffer);
    }
}

