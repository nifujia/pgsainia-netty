package com.pgsainia.server;

import com.pgsainia.ChannelAdapter;
import com.pgsainia.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/28
 */
@Slf4j
public class ServerHandler extends ChannelAdapter {
    public ServerHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        log.info("连接报告：{}", channelHandler.socket().getInetAddress());
        channelHandler.writeAndFlush("Hello, I am BioServer's message... \r\n");
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        log.info("接收到消息， 消息内容：{}", msg);
        channelHandler.writeAndFlush("Hi, I am BioServer ,I received your message....");
    }
}
