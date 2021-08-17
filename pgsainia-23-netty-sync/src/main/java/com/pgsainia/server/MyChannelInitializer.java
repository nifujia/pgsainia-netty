package com.pgsainia.server;

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
        pipeline.addLast(new RpcDecoder(Request.class));
        pipeline.addLast(new RpcEncoder(Response.class));
        pipeline.addLast(new MyServerHandler());

    }
}
