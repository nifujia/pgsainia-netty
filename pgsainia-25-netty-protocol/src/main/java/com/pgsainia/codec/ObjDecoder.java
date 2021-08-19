package com.pgsainia.codec;

import com.pgsainia.domain.protocol.PacketClazzMap;
import com.pgsainia.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class ObjDecoder extends ByteToMessageDecoder {

    private static final int BASE_LEN = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < BASE_LEN) return;
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }

        // 读取指令
        byte command = byteBuf.readByte();
        // 将协议的一位剔除
        byte[] bytes = new byte[dataLength - 1];
        byteBuf.readBytes(bytes);
        list.add(SerializationUtil.deserialize(bytes, PacketClazzMap.packetTypeMap.get(command)));
    }
}
