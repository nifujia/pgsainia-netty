package com.pgsainia.codec;

import com.pgsainia.domain.protocol.Packet;
import com.pgsainia.util.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class ObjEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        byte[] bytes = SerializationUtil.serialize(packet);
        byteBuf.writeInt(bytes.length + 1);
        // 添加协议指令
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeBytes(bytes);
    }
}
