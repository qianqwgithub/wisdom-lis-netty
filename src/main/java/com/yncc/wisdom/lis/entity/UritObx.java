package com.yncc.wisdom.lis.entity;

import lombok.Data;

@Data
public class UritObx {
    /**
     * 检验结果值类型 NM表示数字，ST表示文字描述类 ED表示其他类型
     **/
    private String ValueType;

    /**
     * 项目编号
     **/
    private String Index;

    /**
     * 项目编号
     **/
    private String ItemID;

    /**
     * 项目名称（可选）
     **/
    private String ItemName;

    /**
     * 测试结果
     **/
    private String TestResult;

    /**
     * 单位
     **/
    private String Unit;

    /**
     * 参考值
     **/
    private String ConsultValue;

    /**
     * 结果异常标志 1字节 H表示高 N表示正常 L表示低
     **/
    private String Flag;

    /**
     * 原始结果（可选）
     **/
    private String OD;

    /**
     * 目结果发送时间（可选）yyyyMMddhhmmss 共计14位
     **/
    private String ItemResultTime;

    /**
     * 验医生所在科室编号（可选）
     **/
    private String DocDP;

    /**
     * 检验医生姓名（可选）
     **/
    private String DOCName;

    /**
     * 测试方法（可选）
     **/
    private String Method;


    /**
     * 图片
     **/
    private String image;

    /**
     * 备注ID
     **/
    private String InstrID;

    /**
     * 图片类型
     **/
    private String FormartName;
}
