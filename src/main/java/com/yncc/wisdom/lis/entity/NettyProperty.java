package com.yncc.wisdom.lis.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class NettyProperty{
    @Value("${yncc.netty.filePath}")
    private String filePath;
}
