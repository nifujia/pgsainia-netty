package com.pgsainia.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author nifujia
 * @date 2021/8/14 19:31
 * @description
 */
@Component("nettyServer")
public class NettyServer {

    private final Logger log = LoggerFactory.getLogger(NettyServer.class);
    private final EventLoopGroup parentEventLoopGroup = new NioEventLoopGroup();
    private final EventLoopGroup childEventLoopGroup = new NioEventLoopGroup();

    private Channel channel;

    public ChannelFuture bind(InetSocketAddress inetSocketAddress) {

        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentEventLoopGroup, childEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            channelFuture = serverBootstrap.bind(inetSocketAddress).syncUninterruptibly();
            channel = channelFuture.channel();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("netty server is started....");
            } else {
                log.error("netty server is start error....");
            }
        }
        return channelFuture;
    }

    public void destroy() {
        if (channel == null) return;
        channel.close();
        childEventLoopGroup.shutdownGracefully();
        parentEventLoopGroup.shutdownGracefully();
    }

    public Channel channel() {
        return this.channel;
    }

}