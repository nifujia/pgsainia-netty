package com.pgsainia;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author nifujia
 * @description
 * @date 2021/8/2
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(9999);
    }

    private void bind(int port) {
        EventLoopGroup parentEventLoopGroup = null;
        EventLoopGroup childEventLoopGroup = null;

        try {
            parentEventLoopGroup = new NioEventLoopGroup();
            childEventLoopGroup = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentEventLoopGroup, childEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentEventLoopGroup.shutdownGracefully();
            childEventLoopGroup.shutdownGracefully();
        }
    }
}
