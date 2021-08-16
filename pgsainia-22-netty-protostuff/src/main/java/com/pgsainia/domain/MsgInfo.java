package com.pgsainia.domain;

/**
 * @author nifujia
 * @description
 * @date 2021/8/16
 */
public class MsgInfo {
    private String channelId;
    private String content;

    public MsgInfo() {
    }

    public MsgInfo(String channelId, String content) {
        this.channelId = channelId;
        this.content = content;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
