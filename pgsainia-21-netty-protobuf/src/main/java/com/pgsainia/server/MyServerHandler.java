package com.pgsainia.server;

import com.googlecode.protobuf.format.JsonFormat;
import com.pgsainia.domain.MsgBody;
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
        log.info("开始报告连接信息...");
        log.info("连接信息，有一个客户端建立连接了，host：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("连接报告结束...");

        String returnMessage = String.format("我已经接收到你的连接信息了，连接时间：%s \r\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ctx.writeAndFlush(MsgUtil.buildMsgBody(channel.id().toString(), returnMessage));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("有一个客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到消息了，时间：{}，消息内容：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), JsonFormat.printToString((MsgBody) msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
