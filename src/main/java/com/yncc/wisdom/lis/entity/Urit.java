package com.yncc.wisdom.lis.entity;

import lombok.Data;

import java.util.List;

@Data
public class Urit{
    /**
     * MSH 消息头的定义
     **/
    private UritMsh uritMsh;

    /**
     * PID病人资料段定义
     **/
    private UritPid uritPid;

    /**
     * OBR医嘱信息字段定义
     **/
    private UritObr uritObr;

    /**
     * OBX项目信息字段定义
     **/
    private List<UritObx> uritObxs;
}
