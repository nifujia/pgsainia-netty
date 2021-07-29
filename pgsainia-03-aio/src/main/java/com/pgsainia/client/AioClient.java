package com.pgsainia.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
@Slf4j
public class AioClient {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
        Future<Void> future = asynchronousSocketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        log.info("客户端 client 已经启动....");
        future.get();
        asynchronousSocketChannel.read(ByteBuffer.allocate(1024), null, new ClientHandler(asynchronousSocketChannel, Charset.forName("GBK")));
    }
}
