package com.pgsainia.msg;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class Request {
    private String requestId;
    private Object content;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
