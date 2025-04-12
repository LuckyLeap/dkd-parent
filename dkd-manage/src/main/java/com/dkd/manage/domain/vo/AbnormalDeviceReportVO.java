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
public class AbnormalDeviceReportVO implements Serializable {
    // 故障时间
    private String updateTimeList;
    // 设备编号
    private String innerCodeList;
    // 设备地址
    private String addrList;
}