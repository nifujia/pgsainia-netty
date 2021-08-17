package com.pgsainia.client;

import com.pgsainia.future.SyncWriteMap;
import com.pgsainia.future.WriteFuture;
import com.pgsainia.msg.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Response response = (Response) msg;
        String responseId = response.getResponseId();
        WriteFuture writeFuture = SyncWriteMap.syncKeyMap.get(responseId);
        if (writeFuture != null) {
            writeFuture.setResponse(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error(cause.getMessage());
    }
}
