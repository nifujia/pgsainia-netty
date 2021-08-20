package com.pgsainia.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nifujia
 * @description
 * @date 2021/8/20
 */
@Slf4j
public class MyChunkedServerHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 内容验证
        if (!(msg instanceof ByteBuf)) {
            super.write(ctx, msg, promise);
            return;
        }

        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] data = this.getData(byteBuf);
        // 写入流中
        ByteInputStream byteInputStream = new ByteInputStream();
        byteInputStream.setBuf(data);

        // 消息分块，10 个字节，可以调整
        ChunkedStream chunkedStream = new ChunkedStream(byteInputStream, 10);
        // 管道消息传输
        ChannelProgressivePromise channelProgressivePromise = ctx.channel().newProgressivePromise();
        channelProgressivePromise.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationComplete(ChannelProgressiveFuture channelProgressiveFuture) throws Exception {
                if (channelProgressiveFuture.isSuccess()) {
                    log.info("消息发送成功...");
                    promise.setSuccess();
                } else {
                    log.info("消息发送失败...");
                    promise.setFailure(channelProgressiveFuture.cause());
                }
            }

            @Override
            public void operationProgressed(ChannelProgressiveFuture channelProgressiveFuture, long l, long l1) throws Exception {

            }

        });
        ReferenceCountUtil.release(msg);
        ctx.write(chunkedStream, channelProgressivePromise);

    }

    private byte[] getData(ByteBuf byteBuf) {
        if (byteBuf.hasArray()) {
            return byteBuf.array().clone();
        }

        byte[] bytes = new byte[byteBuf.readableBytes() - 1];
        byteBuf.readBytes(bytes);
        return bytes;
    }
}
