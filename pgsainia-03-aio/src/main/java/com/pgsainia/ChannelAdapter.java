package com.pgsainia;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public abstract class ChannelAdapter implements CompletionHandler<Integer, Object> {

    private AsynchronousSocketChannel asynchronousSocketChannel;
    private Charset charset;

    public ChannelAdapter(AsynchronousSocketChannel asynchronousSocketChannel, Charset charset) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.charset = charset;
        if (asynchronousSocketChannel.isOpen()) {
            this.channelActive(new ChannelHandler(asynchronousSocketChannel, charset));
        }
    }

    public abstract void channelActive(ChannelHandler channelHandler);

    public abstract void channelRead(ChannelHandler channelHandler, Object message);

    public abstract void channelInActive(ChannelHandler channelHandler);

    @Override
    public void completed(Integer result, Object attachment) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        long time = 60 * 60L;
        asynchronousSocketChannel.read(byteBuffer, time, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                if (result == -1) {
                    channelInActive(new ChannelHandler(asynchronousSocketChannel, charset));
                    try {
                        asynchronousSocketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                byteBuffer.flip();
                channelRead(new ChannelHandler(asynchronousSocketChannel, charset), charset.decode(byteBuffer));
                byteBuffer.clear();
                asynchronousSocketChannel.read(byteBuffer, time, TimeUnit.SECONDS, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
    }
}
