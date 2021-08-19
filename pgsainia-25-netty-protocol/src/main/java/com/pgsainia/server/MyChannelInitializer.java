package com.pgsainia.server;

import com.pgsainia.codec.ObjDecoder;
import com.pgsainia.codec.ObjEncoder;
import com.pgsainia.server.handler.MsgDemo01Handler;
import com.pgsainia.server.handler.MsgDemo02Handler;
import com.pgsainia.server.handler.MsgDemo03Handler;
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

        pipeline.addLast(new MsgDemo01Handler());
        pipeline.addLast(new MsgDemo02Handler());
        pipeline.addLast(new MsgDemo03Handler());

        pipeline.addLast(new ObjEncoder());
    }
}
