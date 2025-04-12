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
public class PartnerTop5ReportVO implements Serializable {
    // 合作商列表
    private String nameList;

    // 点位数列表
    private String countList;

    // 合作商总数[1,5]
    private Integer partnerSum;

    // 点位总数
    private Integer nodeSum;
}