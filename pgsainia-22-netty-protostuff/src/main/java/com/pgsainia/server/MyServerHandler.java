package com.pgsainia.server;

import com.alibaba.fastjson.JSON;
import com.pgsainia.util.MsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nifujia
 * @description
 * @date 2021/8/16
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始上报连接信息...");
        log.info("连接信息，有一个客户端跟服务端建立连接了，连接 host ：{}， 端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("报告连接信息结束...");
        String returnMessage = String.format("我是 netty server, 我已经接收到了你的连接信息，时间：%s，host：%s， 端口：%s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                channel.remoteAddress().getHostString(),
                channel.remoteAddress().getPort());

        ctx.writeAndFlush(MsgUtil.buildMsgInfo(channel.id().toString(), returnMessage));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有一个客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("netty server 接收到的信息为：{}", JSON.toJSONString(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
