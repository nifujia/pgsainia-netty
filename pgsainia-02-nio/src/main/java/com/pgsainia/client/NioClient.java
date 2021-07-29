package com.pgsainia.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public class NioClient {
    public static void main(String[] args) {
        Selector selector = null;
        try {
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
            if (connect) {
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ClientHandler(selector, Charset.forName("GBK")).start();
    }
}
