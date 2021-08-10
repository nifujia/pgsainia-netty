package com.pgsainia.codec;

import com.sun.javafx.image.ByteToBytePixelConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author nifujia
 * @description 自定义解码器
 * @date 2021/8/10
 */
public class MyDecoder extends ByteToMessageDecoder {

    /**
     * 数据包基础长度
     */
    private static final int BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        // 基础长度不足
        if (byteBuf.readableBytes() < BASE_LENGTH)
            return;

        int beginIndex;
        while (true) {
            // 获取包头开始的 index
            beginIndex = byteBuf.readerIndex();
            // 标记包头的 index
            byteBuf.markReaderIndex();
            // 如果读到了协议的开始标志，直接结束
            if (byteBuf.readByte() == 0x02)
                break;

            // 未读到包头的标志位，略过一个字节，每次略过一个字节，重新读取包头的标志位
            byteBuf.resetReaderIndex();
            byteBuf.readByte();

            // 当略过一个字符后，数据包的长度又变得不满足，此时应该结束，等待后面的数据到达
            if (byteBuf.readableBytes() < BASE_LENGTH)
                return;
        }

        // 剩余长度不足可读取数量（没有内容长度位）
        int readableCount = byteBuf.readableBytes();
        if (readableCount <= 1) {
            byteBuf.readerIndex(beginIndex);
            return;
        }

        // 长度域占 4 字节，读取 int
        ByteBuf readByteBuf = byteBuf.readBytes(1);
        String msgLenStr = readByteBuf.toString(Charset.forName("GBK"));
        int msgLen = Integer.parseInt(msgLenStr);

        // 剩余长度不足可读取数量（没有结尾标识）
        readableCount = byteBuf.readableBytes();
        if (readableCount < msgLen + 1) {
            byteBuf.readerIndex(beginIndex);
            return;
        }

        ByteBuf msgContent = byteBuf.readBytes(msgLen);
        // 如果没有结尾标识，还原指针位置（其他标识结尾）
        byte end = byteBuf.readByte();
        if (end != 0x03) {
            byteBuf.readerIndex(beginIndex);
            return;
        }

        list.add(msgContent.toString(Charset.forName("GBK")));
    }
}
