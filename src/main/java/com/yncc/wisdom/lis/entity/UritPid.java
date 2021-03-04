package com.yncc.wisdom.lis.entity;

import lombok.Data;

@Data
public class UritPid{
    /**
     * 病人编号 （可选）
     */
    private String patID;

    /**
     * 病人类型 1字节ASCII字符串 1正常2急诊
     */
    private String patType;

    /**
     * 病人姓名 （可选）
     */
    private String patName;

    /**
     * 条码号 （可选）
     */
    private String patBarCode;

    /**
     * 床位号 （可选）
     */
    private String patBedCode;

    /**
     * 病人生日（可选）yyyyMMdd 共计8位
     */
    private String patBirth;

    /**
     * 病人性别（可选） 1字节ASCII字符串 M男性 F女性
     */
    private String patSex;
}
