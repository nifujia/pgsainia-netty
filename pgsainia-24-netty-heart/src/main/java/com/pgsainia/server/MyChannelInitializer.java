package com.pgsainia.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

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

        /**
         * 心跳检测
         * readerIdleTimeSeconds 读超时时间
         * writerIdleTimeSeconds 写超时时间
         * allIdleTimeSeconds 读写超时时间
         * TimeUnit.Seconds 默认为秒，可以指定
         */
        pipeline.addLast(new IdleStateHandler(2, 2, 2));

        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
        pipeline.addLast(new MyServerHandler());
    }
}
