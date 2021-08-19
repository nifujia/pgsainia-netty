package com.pgsainia.client;

import com.pgsainia.util.MsgUtil;
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
 * @date 2021/8/19
 */
@Slf4j
public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 9999);
    }

    public void connect(String host, int port) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new MyChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("netty client is  started...");

            // 向服务端发送消息，MsgDemo01 、MsgDemo02 、MsgDemo03
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(MsgUtil.buildMsgDemo01(channel.id().toString(), "This is demo01 的消息，111"));
            channel.writeAndFlush(MsgUtil.buildMsgDemo02(channel.id().toString(), "This is demo02 的消息，222"));
            channel.writeAndFlush(MsgUtil.buildMsgDemo03(channel.id().toString(), "This is demo03 的消息，333"));

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
