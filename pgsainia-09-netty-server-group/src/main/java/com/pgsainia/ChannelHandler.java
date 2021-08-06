package com.pgsainia;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author nifujia
 * @description
 * @date 2021/8/6
 */
public class ChannelHandler {

    /**
     * 用于存放用户 channel 信息，如果有多个群组，可以使用 Map 来存放
     */
    protected static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
