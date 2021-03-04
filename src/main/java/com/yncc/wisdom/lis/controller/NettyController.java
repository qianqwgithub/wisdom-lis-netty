package com.yncc.wisdom.lis.controller;

import com.yncc.wisdom.lis.nettyClient.NettyClient;
import com.yncc.wisdom.lis.service.CreateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 服务端模拟socket
 */
@Slf4j
@RestController
@RequestMapping("/socket")
public class NettyController {
    @Autowired
    private CreateService createService;

    /**
     * @功能：通用消息发送接口
     * @return
     */
    @GetMapping("/send")
    public void channelRead(){
        createService.createConnection();
        NettyClient nettyClient=new NettyClient();
        nettyClient.start();
    }
}
