package com.pgsainia.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author nifujia
 * @description 自定义编码器， 以 0x02 开始， 0x03 结尾
 * @date 2021/8/10
 */
public class MyEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] bytes = o.toString().getBytes();
        byte[] sendBytes = new byte[bytes.length + 2];
        System.arraycopy(bytes, 0, sendBytes, 1, bytes.length);

        sendBytes[0] = 0x02;
        sendBytes[sendBytes.length - 1] = 0x03;
        byteBuf.writeInt(sendBytes.length);
        byteBuf.writeBytes(sendBytes);
    }
}
