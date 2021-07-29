package com.pgsainia;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public class ChannelHandler {

    private AsynchronousSocketChannel asynchronousSocketChannel;
    private Charset charset;

    public ChannelHandler(AsynchronousSocketChannel asynchronousSocketChannel, Charset charset) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
        this.charset = charset;
    }

    public void writeAndFlush(Object message) {
        byte[] bytes = message.toString().getBytes(charset);
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        asynchronousSocketChannel.write(byteBuffer);
    }

    public AsynchronousSocketChannel channel() {
        return this.asynchronousSocketChannel;
    }
}
