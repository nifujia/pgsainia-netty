package com.pgsainia;

import com.pgsainia.server.AioServer;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
public abstract class ChannelInitializer implements CompletionHandler<AsynchronousSocketChannel, AioServer> {
    @Override
    public void completed(AsynchronousSocketChannel result, AioServer attachment) {
        try {
            initChannel(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void failed(Throwable exc, AioServer attachment) {
        exc.printStackTrace();
    }

    public abstract void initChannel(AsynchronousSocketChannel channel);
}
