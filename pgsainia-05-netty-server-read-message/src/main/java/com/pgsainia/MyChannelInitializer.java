package com.pgsainia;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/2
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("链接报告开始...");
        log.info("连接报告信息：有一个客户端连接到服务端");
        log.info("连接客户端 IP：{}", socketChannel.remoteAddress().getHostString());
        log.info("连接客户端端口：{}", socketChannel.remoteAddress().getPort());
        log.info("链接报告结束...");

        socketChannel.pipeline().addLast(new MyChannelHandler());

    }
}
