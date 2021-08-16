package com.pgsainia.client;

import com.pgsainia.codec.ObjectDecoder;
import com.pgsainia.codec.ObjectEncoder;
import com.pgsainia.domain.MsgInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/16
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new ObjectDecoder(MsgInfo.class));
        socketChannel.pipeline().addLast(new ObjectEncoder(MsgInfo.class));
        socketChannel.pipeline().addLast(new MyClientHandler());

    }
}
