package com.pgsainia.client;

import com.pgsainia.codec.RpcDecoder;
import com.pgsainia.codec.RpcEncoder;
import com.pgsainia.msg.Request;
import com.pgsainia.msg.Response;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcDecoder(Response.class));
        pipeline.addLast(new RpcEncoder(Request.class));
        pipeline.addLast(new MyClientHandler());

    }
}
