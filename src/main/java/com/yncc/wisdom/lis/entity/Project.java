package com.yncc.wisdom.lis.entity;

import lombok.Data;

@Data
public class Project {
    private Long id;
    private Long groupId;
    private Long projectBaseId;
    private Long channelNum;
    private Long spareChannelNum;
}
