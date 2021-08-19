package com.pgsainia.domain;

import com.pgsainia.domain.protocol.Command;
import com.pgsainia.domain.protocol.Packet;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class MsgDemo01 extends Packet {
    private String channelId;
    private String demo01;

    public MsgDemo01(String channelId, String demo01) {
        this.channelId = channelId;
        this.demo01 = demo01;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDemo01() {
        return demo01;
    }

    public void setDemo01(String demo01) {
        this.demo01 = demo01;
    }

    @Override
    public Byte getCommand() {
        return Command.demo01;
    }
}
