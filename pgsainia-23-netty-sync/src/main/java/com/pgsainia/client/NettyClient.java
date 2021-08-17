package com.pgsainia.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
@Slf4j
public class NettyClient implements Runnable {

    private static final String host = "127.0.0.1";
    private static final int port = 9999;

    private ChannelFuture channelFuture;

    @Override
    public void run() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new MyChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("netty client is started....");
            this.channelFuture = channelFuture;
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public ChannelFuture channelFuture() {
        return this.channelFuture;
    }
}
