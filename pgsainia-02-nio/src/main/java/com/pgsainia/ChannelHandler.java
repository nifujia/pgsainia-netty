package com.pgsainia;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public class ChannelHandler {
    private SocketChannel socketChannel;
    private Charset charset;

    public ChannelHandler(SocketChannel socketChannel, Charset charset) {
        this.socketChannel = socketChannel;
        this.charset = charset;
    }

    public void writeAndFlush(Object message) {
        try {
            byte[] bytes = message.toString().getBytes(charset);
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketChannel socketChannel() {
        return this.socketChannel;
    }
}
