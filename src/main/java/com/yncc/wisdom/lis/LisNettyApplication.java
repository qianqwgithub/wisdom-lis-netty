package com.yncc.wisdom.lis;

import com.yncc.wisdom.lis.nettyClient.NettyClient;
import com.yncc.wisdom.lis.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LisNettyApplication {
    public static void main(String[] args){
        SpringApplication.run(LisNettyApplication.class,args);
    }
}


