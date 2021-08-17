package com.pgsainia.future;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class SyncWriteMap {
    public static Map<String, WriteFuture> syncKeyMap = new ConcurrentHashMap<>();
}
