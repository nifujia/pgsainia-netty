package com.pgsainia.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
@Slf4j
public class AioServer extends Thread {

    private AsynchronousServerSocketChannel socketChannel;

    @Override
    public void run() {
        try {
            socketChannel = AsynchronousServerSocketChannel.open(
                    AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10));
            socketChannel.bind(new InetSocketAddress(9999));
            log.info("服务端 server 已启动...");
            // 等待
            CountDownLatch countDownLatch = new CountDownLatch(1);
            socketChannel.accept(this, new AioServerChannelInitializer());
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AioServer().start();
    }
}
