package com.pgsainia.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/11
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 消息入站处理器
        socketChannel.pipeline().addLast(new MyInChannelHandler());
        // 消息出站处理器，在 client 发送消息的时候会触发此处理器
        socketChannel.pipeline().addLast(new MyOutChannelHandler());
    }
}
