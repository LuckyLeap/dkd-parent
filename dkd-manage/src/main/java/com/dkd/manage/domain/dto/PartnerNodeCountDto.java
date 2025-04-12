package com.dkd.manage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerNodeCountDto implements Serializable {
    // 合作商名称
    private String partnerName;

    // 合作商点位数
    private Integer NodeCount;
}