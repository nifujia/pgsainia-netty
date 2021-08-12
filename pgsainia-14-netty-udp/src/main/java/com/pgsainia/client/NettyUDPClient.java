package com.pgsainia.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/8/12
 */
@Slf4j
public class NettyUDPClient {
    public static void main(String[] args) {
        new NettyUDPClient().connect("127.0.0.1", 9999, 9998);
    }

    public void connect(String host, int port, int clientPort) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .handler(new MyChannelInitializer());

            Channel channel = bootstrap.bind(clientPort).channel();
            log.info("netty udp client is started....");

            // 向目标端口发送信息
            String message = String.format("Hello ，我是客户端小帅，你好啊...");
            DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer(message.getBytes(Charset.forName("GBK"))),
                    new InetSocketAddress(host, port));

            channel.writeAndFlush(datagramPacket).sync();
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
