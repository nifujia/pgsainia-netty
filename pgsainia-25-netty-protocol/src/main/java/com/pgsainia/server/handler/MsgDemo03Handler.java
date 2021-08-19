package com.pgsainia.server.handler;

import com.alibaba.fastjson.JSON;
import com.pgsainia.domain.MsgDemo03;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
@Slf4j
public class MsgDemo03Handler extends SimpleChannelInboundHandler<MsgDemo03> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgDemo03 msgDemo03) throws Exception {
        log.info("接收消息的时间：{}， 处理器：{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(new Date()), msgDemo03.getClass().getName());
        log.info("接收的消息 demo03：{}", JSON.toJSONString(msgDemo03));
    }
}
