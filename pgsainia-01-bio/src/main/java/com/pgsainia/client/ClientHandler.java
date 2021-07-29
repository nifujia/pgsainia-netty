package com.pgsainia.client;

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
public class ClientHandler extends ChannelAdapter {
    public ClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        log.info("连接信息：{}", channelHandler.socket().getInetAddress());
        channelHandler.writeAndFlush("Hi, I am BioServer's message... \r\n");
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        log.info("接收到的消息：{}", msg);
        channelHandler.writeAndFlush("Hello，I amd BioClient, I received you  message...");
    }
}
