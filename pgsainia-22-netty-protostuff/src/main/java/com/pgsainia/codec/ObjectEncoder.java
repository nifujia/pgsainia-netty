package com.pgsainia.codec;

import com.pgsainia.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author nifujia
 * @description 自定义解码器
 * @date 2021/8/16
 */
public class ObjectEncoder extends MessageToByteEncoder {

    private Class<?> genericClazz;

    public ObjectEncoder(Class<?> genericClazz) {
        this.genericClazz = genericClazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (this.genericClazz.isInstance(o)) {
            byte[] bytes = SerializationUtil.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
