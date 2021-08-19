package com.pgsainia.client;

import com.pgsainia.codec.ObjDecoder;
import com.pgsainia.codec.ObjEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ObjDecoder());
        pipeline.addLast(new MyClientHandler());
        pipeline.addLast(new ObjEncoder());

    }
}
