package com.pgsainia.server;

import com.pgsainia.ChannelAdapter;
import com.pgsainia.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/29
 */
@Slf4j
public class ServerHandler extends ChannelAdapter {
    public ServerHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            log.info("连接信息：{}", channelHandler.socketChannel().getLocalAddress());
            channelHandler.writeAndFlush("Hello，我是 server 端，I send message to you... \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object message) {
        log.info("接收到的信息为：{}", message.toString());
        channelHandler.writeAndFlush("Hi， 我接受到你的信息了... ");
    }
}
