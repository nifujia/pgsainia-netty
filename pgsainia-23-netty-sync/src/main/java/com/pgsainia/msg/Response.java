package com.pgsainia.msg;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class Response {
    private String responseId;
    private String content;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
