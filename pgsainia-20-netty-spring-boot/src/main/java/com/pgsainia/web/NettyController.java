package com.pgsainia.web;

import com.pgsainia.server.NettyServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author nifujia
 * @date 2021/8/14 21:36
 * @description
 */
@RestController
public class NettyController {

    @Resource
    private NettyServer nettyServer;


    @RequestMapping("address")
    public String address() {
        return "服务启动地址信息为：" + nettyServer.channel().localAddress();
    }

    @RequestMapping("isOpen")
    public String isOpen() {
        return "netty server isOpen：" + nettyServer.channel().isOpen();
    }
}