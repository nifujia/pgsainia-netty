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
public class MyChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("报告连接开始....");
        log.info("连接信息：有一个客户端连接进来了，连接IP：{}，端口：{}",
                channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接建立完毕，报告完毕...");

        // 建立连接之后，回复信息
        String returnMessage = String.format("客户端建立连接成功，客户端IP为：%s，端口为 %s", channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());
        // 此处不用自己编解码
        channel.writeAndFlush(returnMessage);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端接收到信息：{}", msg);
        String returnMessage = String.format("我是 netty server， 我已经接收到你的信息，信息内容为：%s", msg);
        // 此处不用自己编解码了
        ctx.channel().writeAndFlush(returnMessage);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("异常了，信息为：{}", cause.getMessage());
    }
}
