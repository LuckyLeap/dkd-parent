package com.dkd.manage.domain.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DailyTurnoverDto {
    //日期
    private LocalDate orderDate;
    //营业额
    private Double sum;
    //订单量
    private Long count;
}