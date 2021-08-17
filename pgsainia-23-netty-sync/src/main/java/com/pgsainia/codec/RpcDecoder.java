package com.pgsainia.codec;

import com.pgsainia.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author nifujia
 * @description 解码器
 * @date 2021/8/17
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private static final int BASE_LENGTH = 4;
    private Class<?> genericClazz;

    public RpcDecoder(Class<?> genericClazz) {
        this.genericClazz = genericClazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < BASE_LENGTH) return;
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);
        list.add(SerializationUtil.deserialize(bytes, genericClazz));
    }
}
