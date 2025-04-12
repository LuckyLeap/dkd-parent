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
public class RegionReportOrdersVO implements Serializable {
    //区域名称列表，以逗号分隔，例如：北京,上海,深圳
    private String nameList;

    //销量额列表，以逗号分隔，例如：260,215,200
    private String moneyList;
}