package com.dkd.manage.domain.dto;

import lombok.Data;

@Data
public class OrderCollectDto {
    //一段时期内总销售额
    private Long totalMoney;

    //一段时期内总订单量
    private Long totalOrderCount;

    //一段时期内总分成金额
    private Double totalBill;
}