package com.pgsainia.client;

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
public class MyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        log.info("开始向服务端报告连接信息...");
        log.info("报告信息：客户端跟服务端建立连接了，远程服务端信息 host：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("向服务端报告连接完毕....");
        String returnMessage = String.format("我已经跟服务端建立连接了，时间：{} \r\n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ctx.writeAndFlush(MsgUtil.buildMsgBody(channel.id().toString(), returnMessage));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("我是客户端，我断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到服务端的信息为：{}", JsonFormat.printToString((MsgBody) msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
