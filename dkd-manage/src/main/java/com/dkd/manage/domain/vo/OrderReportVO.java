package com.dkd.manage.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    //日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    //每日销量数，以逗号分隔，例如：260,210,215
    private String orderCountList;

    //每日销售额，以逗号分隔，例如：20,21,10
    private String orderMoneyList;
}