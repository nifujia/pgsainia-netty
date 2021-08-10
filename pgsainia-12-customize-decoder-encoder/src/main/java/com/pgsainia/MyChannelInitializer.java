package com.pgsainia;

import com.pgsainia.codec.MyDecoder;
import com.pgsainia.codec.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/10
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new MyEncoder());
        socketChannel.pipeline().addLast(new MyDecoder());
        socketChannel.pipeline().addLast(new MyServerHandler());

    }
}
