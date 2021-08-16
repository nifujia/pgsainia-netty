package com.pgsainia.client;

import com.alibaba.fastjson.JSON;
import com.pgsainia.util.MsgUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

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
        log.info("客户端开始报告连接信息....");
        log.info("客户端连接服务端，host ：{}，端口：{}", channel.remoteAddress().getHostString(), channel.remoteAddress().getPort());
        log.info("客户端报告连接信息完毕...");
        String returnMessage = String.format("我是 client，我开始跟 netty server 建立连接了...");
        ctx.writeAndFlush(MsgUtil.buildMsgInfo(channel.id().toString(), returnMessage));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("接收到服务端的信息为：{}", JSON.toJSONString(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
