package com.pgsainia;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/9
 */
@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动连接服务端之后，这个通道就是活跃的了，也就是服务端和客户端建立了连接并且可以相互传数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始汇报连接...");
        log.info("连接信息，IP：{}，端口：{}", channel.localAddress().getHostString(), channel.localAddress().getPort());
        log.info("汇报连接结束...");

        String returnMessage = String.format("我是 netty client ，我已经建立连接成功了，IP：%s， 端口：%s", channel.localAddress().getHostString(),
                channel.localAddress().getPort());
        ctx.writeAndFlush(returnMessage);

    }

    /**
     * 客户端主动断开与服务端的连接，通道不活跃，不能相互传数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端接收到服务端的信息，信息为：{}", msg);
        String returnMessage = String.format("我是 netty client，我已经接收到你的消息，消息为：%s", msg);
        ctx.writeAndFlush(returnMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("发生错误，错误信息：{}", cause.getMessage());
    }
}
