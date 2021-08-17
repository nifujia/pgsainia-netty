package com.pgsainia.future;

import com.pgsainia.msg.Response;

import java.util.concurrent.Future;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public interface WriteFuture<T> extends Future<T> {

    /**
     * 获取异常
     *
     * @return
     */
    Throwable cause();

    /**
     * 设置异常
     *
     * @param cause
     */
    void setCause(Throwable cause);

    /**
     * 判断是否写入成功
     *
     * @return
     */
    boolean isWriteSuccess();

    /**
     * 设置写入结果
     *
     * @param writeResult
     */
    void setWriteResult(boolean writeResult);

    /**
     * 获取 requestId
     *
     * @return
     */
    String requestId();

    /**
     * 获取相应结果
     *
     * @return
     */
    T response();

    /**
     * 设置相应结果
     *
     * @param response
     */
    void setResponse(T response);


    /**
     * 判断是否超时
     *
     * @return
     */
    boolean isTimeOut();
}
