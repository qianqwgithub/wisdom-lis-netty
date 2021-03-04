package com.yncc.wisdom.lis.entity;

import lombok.Data;

@Data
public class UritMsh{
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 仪器名称
     */
    private String instrName;

    /**
     * 结果发送时间 yyyyMMddhhmmss 共计14位
     */
    private String resultTime;

    /**
     * 仪器类型
     */
    private String instrType;
}
