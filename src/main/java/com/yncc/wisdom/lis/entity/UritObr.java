package com.yncc.wisdom.lis.entity;

import lombok.Data;

@Data
public class UritObr{
    /**
     * 样本类型（可选） 1字节ASCII字符串 1正常2质控3定标
     **/
    private String sampleType;

    /**
     * 申请号（可选）
     **/
    private String rEQID;

    /**
     * 样本号
     **/
    private String sampleID;

    /**
     * 公司名称
     **/
    private String companyName;

    /**
     * 仪器名称
     **/
    private String instrName;

    /**
     * 样本送检时间（可选） yyyyMMddhhmmss 共计14位
     **/
    private String sampleTime;

    /**
     * 开始检测时间（可选） yyyyMMddhhmmss 共计14位
     **/
    private String startTime;

    /**
     * 症状（可选）
     **/
    private String symptom;

    /**
     * 送验医生（可选）
     **/
    private String sendDOCName;

    /**
     * 送检科室编号（可选）
     **/
    private String sendDP;
}
