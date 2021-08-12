package com.pgsainia.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/12
 */
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
        nioDatagramChannel.pipeline().addLast(new MyClientHandler());
    }
}
