package com.pgsainia.server;

import com.pgsainia.ChannelAdapter;
import com.pgsainia.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
@Slf4j
public class ServerHandler extends ChannelAdapter {
    public ServerHandler(AsynchronousSocketChannel asynchronousSocketChannel, Charset charset) {
        super(asynchronousSocketChannel, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            log.info("连接信息：{}", channelHandler.channel().getLocalAddress());
            channelHandler.writeAndFlush("通知服务端连接成功，连接信息：" + channelHandler.channel().getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object message) {
        log.info("接收到客户端信息为：{}", message.toString());
        channelHandler.writeAndFlush("已接收到客户端的信息，信息为：" + message);
    }

    @Override
    public void channelInActive(ChannelHandler channelHandler) {

    }
}
