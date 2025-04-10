package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Emp;
import com.dkd.manage.domain.TaskCollect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCollectVo extends TaskCollect {
    //员工信息
    private Emp emp;

    //工单完成数【本月】
    private int finishCountY;
    //工单进行中数【本月】
    private int progressCountY;
    //工单取消数【本月】
    private int cancelCountY;

    //工单完成数【总】
    private int finishCountS;
    //工单进行中数【总】
    private int progressCountS;
    //工单取消数【总】
    private int cancelCountS;
}