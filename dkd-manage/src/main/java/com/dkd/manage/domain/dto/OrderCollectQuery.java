package com.dkd.manage.domain.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class OrderCollectQuery implements Serializable {
    // 合作商id
    private Long partnerId;
    // 开始时间
    private Date startDate;
    // 结束时间
    private Date endDate;
}