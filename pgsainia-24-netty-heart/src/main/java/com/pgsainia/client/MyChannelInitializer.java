package com.pgsainia.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/18
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
        pipeline.addLast(new MyClientHandler());
    }
}
