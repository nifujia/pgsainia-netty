package com.pgsainia;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/6
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(9999);

    }

    public void bind(int port) {
        EventLoopGroup parentEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup childEventLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentEventLoopGroup, childEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("netty server is started...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childEventLoopGroup.shutdownGracefully();
            parentEventLoopGroup.shutdownGracefully();
        }
    }

}
