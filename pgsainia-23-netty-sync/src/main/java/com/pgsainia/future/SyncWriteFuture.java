package com.pgsainia.future;

import com.pgsainia.msg.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class SyncWriteFuture implements WriteFuture<Response> {
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private final long start = System.currentTimeMillis();

    private long timeout;
    private boolean isTimeout = false;
    private Throwable cause;
    private boolean writerResult;
    private String requestId;
    private Response response;

    public SyncWriteFuture(String requestId) {
        this.requestId = requestId;
    }

    public SyncWriteFuture(String requestId, long timeout) {
        this.requestId = requestId;
        this.timeout = timeout;
        this.writerResult = true;
        this.isTimeout = false;
    }

    @Override
    public Throwable cause() {
        return this.cause;
    }

    @Override
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public boolean isWriteSuccess() {
        return writerResult;
    }

    @Override
    public void setWriteResult(boolean writeResult) {
        this.writerResult = writeResult;
    }

    @Override
    public String requestId() {
        return this.requestId;
    }

    @Override
    public Response response() {
        return this.response;
    }

    @Override
    public void setResponse(Response response) {
        this.response = response;
        countDownLatch.countDown();
    }

    @Override
    public boolean isTimeOut() {
        if (isTimeout) return isTimeout;
        return System.currentTimeMillis() - this.start > timeout;

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Response get() throws InterruptedException, ExecutionException {
        countDownLatch.await();
        return response;
    }

    @Override
    public Response get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (countDownLatch.await(timeout, unit)) {
            return response;
        }
        return null;
    }
}
