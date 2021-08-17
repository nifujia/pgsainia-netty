package com.pgsainia;

import com.pgsainia.server.NettyServer;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
public class ServerTest {
    public static void main(String[] args) {
        new Thread(new NettyServer()).start();
    }
}
