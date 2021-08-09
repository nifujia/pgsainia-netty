package com.pgsainia;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/9
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("连接报告开始...");
        log.info("开始汇报连接信息，channelId ：{}", socketChannel.id());
        log.info("连接报告完毕");
    }
}
