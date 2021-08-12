package com.pgsainia.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;


/**
 * @author nifujia
 * @description
 * @date 2021/8/12
 */
@Slf4j
public class MyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String content = datagramPacket.content().toString(Charset.forName("GBK"));
        log.info("UDP 客户端接收到的数据为：{}", content);
    }
}
