package com.pgsainia.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/28
 */
@Slf4j
public class BioClient {
    private static final int PORT = 7999;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", PORT);
            log.info("BioClient is started.....");
            ClientHandler clientHandler = new ClientHandler(socket, Charset.forName("UTF-8"));
            clientHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
