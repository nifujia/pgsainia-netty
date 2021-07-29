package com.pgsainia.server;

import com.pgsainia.ChannelInitializer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public class AioServerChannelInitializer extends ChannelInitializer {
    @Override
    public void initChannel(AsynchronousSocketChannel channel) {
        channel.read(ByteBuffer.allocate(1024), 10, TimeUnit.SECONDS, null, new ServerHandler(channel, Charset.forName("GBK")));
    }
}
