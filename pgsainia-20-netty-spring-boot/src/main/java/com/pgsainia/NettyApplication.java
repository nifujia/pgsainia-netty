package com.pgsainia;

import com.pgsainia.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @author nifujia
 * @date 2021/8/14 19:26
 * @description
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    @Value("${netty.host}")
    private String host;

    @Value(("${netty.port}"))
    private Integer port;

    @Resource
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        ChannelFuture channelFuture = nettyServer.bind(inetSocketAddress);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();

    }
}