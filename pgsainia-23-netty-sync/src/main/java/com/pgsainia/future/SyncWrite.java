package com.pgsainia.future;

import com.pgsainia.msg.Request;
import com.pgsainia.msg.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class SyncWrite {
    public Response writeAndSync(final Channel channel, final Request request, final long timeout) throws Exception {
        if (channel == null) throw new NullPointerException("channel");
        if (request == null) throw new NullPointerException("request");
        if (timeout <= 0) throw new IllegalArgumentException("timeout < 0");

        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        WriteFuture<Response> syncWriteFuture = new SyncWriteFuture(request.getRequestId());
        SyncWriteMap.syncKeyMap.put(request.getRequestId(), syncWriteFuture);

        Response response = this.doWriteAndSync(channel, request, timeout, syncWriteFuture);

        SyncWriteMap.syncKeyMap.remove(request.getRequestId());

        return response;
    }

    private Response doWriteAndSync(final Channel channel, final Request request,
                                    final long timeout, final WriteFuture<Response> syncWriteFuture) throws Exception {
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                syncWriteFuture.setWriteResult(channelFuture.isSuccess());
                syncWriteFuture.setCause(channelFuture.cause());

                // 失败移除
                if (!syncWriteFuture.isWriteSuccess()) {
                    SyncWriteMap.syncKeyMap.remove(syncWriteFuture.requestId());
                }
            }
        });

        Response response = syncWriteFuture.get(timeout, TimeUnit.MILLISECONDS);
        if (response == null) {
            if (syncWriteFuture.isTimeOut()) {
                throw new TimeoutException();
            } else {
                throw new Exception(syncWriteFuture.cause().getMessage());
            }
        }

        return response;
    }
}
