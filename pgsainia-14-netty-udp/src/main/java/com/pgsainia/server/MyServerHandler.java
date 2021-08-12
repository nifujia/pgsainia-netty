package com.pgsainia.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
public class MyServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String content = datagramPacket.content().toString(Charset.forName("GBK"));
        log.info("接收到的信息为：{}", content);

        // 给客户端回复消息
        String returnMessage = String.format("我是 netty server, 我已经接收到你的消息，消息为：%s", content);

        //数据包的数据是以字符串数组的形式存储的
        byte[] bytes = returnMessage.getBytes(Charset.forName("GBK"));
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
        DatagramPacket returnPackage = new DatagramPacket(byteBuf, datagramPacket.sender());
        channelHandlerContext.writeAndFlush(returnPackage);
    }
}
