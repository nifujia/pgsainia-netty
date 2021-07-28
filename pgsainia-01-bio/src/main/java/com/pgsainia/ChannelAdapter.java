package com.pgsainia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author nifujia
 * @description
 * @date 2021/7/28
 */
public abstract class ChannelAdapter extends Thread {
    private Socket socket;
    private Charset charset;
    private ChannelHandler channelHandler;

    public ChannelAdapter(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
        while (!socket.isConnected()) break;
        channelHandler = new ChannelHandler(this.socket, charset);
        this.channelActive(channelHandler);
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, this.charset));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                this.channelRead(channelHandler, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 连接通知
     *
     * @param channelHandler
     */
    public abstract void channelActive(ChannelHandler channelHandler);

    /**
     * 读取消息
     *
     * @param channelHandler
     * @param msg
     */
    public abstract void channelRead(ChannelHandler channelHandler, Object msg);

}
