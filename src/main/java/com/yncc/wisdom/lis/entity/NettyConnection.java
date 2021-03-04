package com.yncc.wisdom.lis.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class NettyConnection{
    private String localhost_ip;
    private int localhost_port;

    private String server_ip;
    private int server_port;

    private String delimiter;
    private int maxFrameLength;

    private String groupCode;
    private String equipCode;
}
