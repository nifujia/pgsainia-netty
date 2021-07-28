package com.pgsainia;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/27
 */
public class ChannelHandler {
    private Socket socket;
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.toString().getBytes(charset));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket socket() {
        return this.socket;
    }
}
