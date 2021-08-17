package com.pgsainia;

import com.alibaba.fastjson.JSON;
import com.pgsainia.client.NettyClient;
import com.pgsainia.future.SyncWrite;
import com.pgsainia.msg.Request;
import com.pgsainia.msg.Response;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author nifujia
 * @description
 * @date 2021/8/17
 */
@Slf4j
public class ClientTest {

    private static ChannelFuture channelFuture;

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        new Thread(nettyClient).start();

        int index = 0;
        while (true) {
            try {
                if (channelFuture == null) {
                    channelFuture = nettyClient.channelFuture();
                    Thread.sleep(500);
                    continue;
                }

                SyncWrite syncWrite = new SyncWrite();
                Request request = new Request();
                request.setContent("我是 Client，这是我给你发的消息" + index);
                Response response = syncWrite.writeAndSync(channelFuture.channel(), request, 1000);
                log.info("调动的结果：{}", JSON.toJSONString(response));
                index++;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
