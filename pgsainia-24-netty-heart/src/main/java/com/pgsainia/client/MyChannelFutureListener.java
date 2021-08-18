package com.pgsainia.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author nifujia
 * @description
 * @date 2021/8/18
 */
@Slf4j
public class MyChannelFutureListener implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.isSuccess()) {
            log.info("netty client is start done...");
            return;
        }

        EventLoop eventExecutors = channelFuture.channel().eventLoop();
        eventExecutors.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient().connect("127.0.0.1", 9999);
                    log.info("netty client reconnect is success....");
                    Thread.sleep(500);
                } catch (Exception e) {
                    log.error("netty client reconnect is fail....");
                    e.printStackTrace();
                }

            }
        }, 1L, TimeUnit.SECONDS);

    }
}
