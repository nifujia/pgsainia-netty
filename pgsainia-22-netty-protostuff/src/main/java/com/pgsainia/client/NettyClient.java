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
 * @date 2021/8/16
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
            log.info("netty client is started...");

            log.info("客户端连接成功，开始给 server 发送数据....");
            channelFuture.channel().writeAndFlush(MsgUtil.buildMsgInfo(channelFuture.channel().id().toString(), "This is my first message..."));
            channelFuture.channel().writeAndFlush(MsgUtil.buildMsgInfo(channelFuture.channel().id().toString(), "这是我的第二条信息。"));
            channelFuture.channel().writeAndFlush(MsgUtil.buildMsgInfo(channelFuture.channel().id().toString(), "我是小帅哥，欢迎来撩我"));
            log.info("客户端链接之后，信息发送完毕....");

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
