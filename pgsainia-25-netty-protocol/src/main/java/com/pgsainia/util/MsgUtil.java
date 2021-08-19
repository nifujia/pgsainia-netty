package com.pgsainia.util;

import com.pgsainia.domain.MsgDemo01;
import com.pgsainia.domain.MsgDemo02;
import com.pgsainia.domain.MsgDemo03;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class MsgUtil {
    public static MsgDemo01 buildMsgDemo01(String channelId, String demo01) {
        return new MsgDemo01(channelId, demo01);
    }

    public static MsgDemo02 buildMsgDemo02(String channelId, String demo02) {
        return new MsgDemo02(channelId, demo02);
    }

    public static MsgDemo03 buildMsgDemo03(String channelId, String demo03) {
        return new MsgDemo03(channelId, demo03);
    }
}
