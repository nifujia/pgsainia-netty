package com.pgsainia.domain.protocol;

import com.pgsainia.domain.MsgDemo01;
import com.pgsainia.domain.MsgDemo02;
import com.pgsainia.domain.MsgDemo03;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nifujia
 * @description
 * @date 2021/8/19
 */
public class PacketClazzMap {

    public static Map<Byte, Class<? extends Packet>> packetTypeMap = new ConcurrentHashMap<>();

    static {
        packetTypeMap.put(Command.demo01, MsgDemo01.class);
        packetTypeMap.put(Command.demo02, MsgDemo02.class);
        packetTypeMap.put(Command.demo03, MsgDemo03.class);
    }
}
