package com.pgsainia;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author nifujia
 * @description
 * @date 2021/8/3
 */
@Slf4j
public class MyChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始建立链接...");
        log.info("链接报告：开始有一个客户端连接到服务器...");
        log.info("链接 IP：{}", channel.remoteAddress().getHostString());
        log.info("链接端口：{}", channel.remoteAddress().getPort());
        log.info("链接建立完成，报告完毕...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 与上一节相比，不需要自己编解码
        log.info("接收到的信息为：{}", msg.toString());

    }
}
