package com.dkd.manage.domain;

import java.util.Date;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单按日统计对象 tb_task_collect
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 当日工单完成数 */
    @Excel(name = "当日工单完成数")
    private Long finishCount;

    /** 当日进行中的工单数 */
    @Excel(name = "当日进行中的工单数")
    private Long progressCount;

    /** 当日取消工单数 */
    @Excel(name = "当日取消工单数")
    private Long cancelCount;

    /** 汇总的日期 */
    private Date collectDate;
}