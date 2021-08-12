package com.pgsainia.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/12
 */
@Slf4j
public class NettyUDPServer {
    public static void main(String[] args) {
        new NettyUDPServer().bind(9999);
    }

    public void bind(int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    // 广播
                    .option(ChannelOption.SO_BROADCAST, true)
                    // 设置 udp 读缓冲区为 2M
                    .option(ChannelOption.SO_RCVBUF, 2048 * 1024)
                    // 设置 udp 写缓冲区为 1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                    .handler(new MyChannelInitializer());

            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("netty udp server is started...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭
            eventLoopGroup.shutdownGracefully();
        }
    }
}
