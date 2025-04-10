package com.dkd.manage.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.dkd.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对帐统计对象 tb_order_collect
 */
  
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;
    /** 合作商Id */
    private Long partnerId;
    /** 合作商 */
    @Excel(name = "合作商")
    private String partnerName;
    /** 日收入 */
    @Excel(name = "日收入")
    private Long orderTotalMoney;
    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderDate;
    /** 分成金额 */
    @Excel(name = "分成金额")
    private Double totalBill;
    /** 点位Id */
    private Long nodeId;
    /** 点位名 */
    private String nodeName;
    /** 订单数 */
    @Excel(name = "订单数")
    private Long orderCount;
    /** 分成比例 */
    @Excel(name = "分成比例")
    private Long ratio;
    /** 区域名称 */
    private String regionName;

}