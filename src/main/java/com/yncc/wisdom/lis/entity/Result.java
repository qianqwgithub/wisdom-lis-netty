package com.yncc.wisdom.lis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Result{
    /**
     * 样本号
     **/
    private String sampleNo;
    /**
     * 样本分发时间
     **/
    private Long sampleDispenseGroupId;
    /**
     * 检验时间(样本分发时间,结果发送时间)
     **/
    private String inspectTime;

    /**
     * 小组id
     **/
    private Long groupId;
    /**
     * 项目id
     **/
    private Long projectBaseId;
    /**
     * 项目id
     **/
    private Long projectId;
    /**
     * 通道号
     **/
    private String channelNum;
    /**
     * 备用通道号
     **/
    private String spareChannelNum;
    /**
     * 结果
     **/
    private String result;
}
