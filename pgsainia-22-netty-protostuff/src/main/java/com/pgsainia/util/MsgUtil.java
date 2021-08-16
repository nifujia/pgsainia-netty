package com.pgsainia.util;

import com.pgsainia.domain.MsgInfo;

/**
 * @author nifujia
 * @description
 * @date 2021/8/16
 */
public class MsgUtil {
    public static MsgInfo buildMsgInfo(String channelId, String content) {
        return new MsgInfo(channelId, content);
    }
}
