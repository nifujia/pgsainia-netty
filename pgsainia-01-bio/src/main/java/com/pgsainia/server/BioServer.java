package com.pgsainia.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author nifujia
 * @description
 * @date 2021/7/28
 */
@Slf4j
public class BioServer extends Thread {

    private static final int PORT = 7999;
    ServerSocket serverSocket = null;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(PORT));
            log.info("server is started.... port：{}", PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ServerHandler serverHandler = new ServerHandler(socket, Charset.forName("GBK"));
                serverHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }
}
