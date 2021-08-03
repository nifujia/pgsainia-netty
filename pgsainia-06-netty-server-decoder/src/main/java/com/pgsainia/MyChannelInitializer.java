package com.pgsainia;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/3
 */
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        // 解码器，基于换行符
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码器，基于指定字符，等同于 LineBaseFrameDecoder
        //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, false, Delimiters.lineDelimiter()));
        // 解码器，基于字符串长度
        //socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(4));

        // 解码转字符串
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        socketChannel.pipeline().addLast(new MyChannelHandler());

    }
}
