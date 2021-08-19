package com.pgsainia.domain.protocol;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public abstract class Packet {

    /**
     * 获取协议指令
     *
     * @return
     */
    public abstract Byte getCommand();
}
