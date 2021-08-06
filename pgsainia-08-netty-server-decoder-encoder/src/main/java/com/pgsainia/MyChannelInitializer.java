package com.pgsainia;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/6
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 使用换行符作为分隔符
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 编码器
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码器
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));

        socketChannel.pipeline().addLast(new MyChannelHandler());

    }
}
