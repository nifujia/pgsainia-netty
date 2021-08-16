package com.pgsainia.util;

import com.pgsainia.domain.MsgBody;

/**
 * @author nifujia
 * @description
 * @date 2021/8/16
 */
public class MsgUtil {
    public static MsgBody buildMsgBody(String channelId, String msgContent) {
        MsgBody.Builder msg = MsgBody.newBuilder();
        msg.setChannelId(channelId);
        msg.setMsgInfo(msgContent);
        return msg.build();
    }
}
