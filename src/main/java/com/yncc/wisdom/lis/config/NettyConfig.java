package com.yncc.wisdom.lis.config;

import com.yncc.wisdom.lis.entity.NettyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {

    @Autowired
    private NettyProperty nettyProperty;
}
