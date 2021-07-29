package com.pgsainia;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public abstract class ChannelAdapter extends Thread {

    private ChannelHandler channelHandler;
    private Charset charset;
    private Selector selector;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handleInput(selectionKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        if (!selectionKey.isValid()) return;
        Class<?> superclass = selectionKey.channel().getClass().getSuperclass();
        // 客户端 socketChannel
        if (superclass == SocketChannel.class) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (channel.finishConnect()) {
                    channelHandler = new ChannelHandler(channel, charset);
                    this.channelActive(channelHandler);
                    channel.register(selector, SelectionKey.OP_READ);
                } else {
                    System.exit(1);
                }
            }
        }

        // 服务端 serverSocketChannel
        if (superclass == ServerSocketChannel.class) {
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel channel = serverSocketChannel.accept();
                channel.configureBlocking(false);
                channelHandler = new ChannelHandler(channel, charset);
                this.channelActive(channelHandler);
                channel.register(selector, SelectionKey.OP_READ);
            }
        }

        if (selectionKey.isReadable()) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            if (read > 0) {
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);
                this.channelRead(channelHandler, new String(bytes, charset));
            } else {
                selectionKey.channel();
                channel.close();
            }
        }
    }


    /**
     * 连接通知
     *
     * @param channelHandler
     */
    public abstract void channelActive(ChannelHandler channelHandler);

    /**
     * 读取信息
     *
     * @param channelHandler
     * @param message
     */
    public abstract void channelRead(ChannelHandler channelHandler, Object message);
}
