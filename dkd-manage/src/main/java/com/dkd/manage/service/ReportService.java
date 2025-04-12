package com.dkd.manage.service;

import com.dkd.manage.domain.vo.*;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 订单统计
     */
    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量排名 Top 10
     */
    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    /**
     * 销售额分布统计
     */
    RegionReportOrdersVO getRegionReportOrders(LocalDate begin, LocalDate end);

    /**
     * 合作商点位数 Top 5
     */
    PartnerTop5ReportVO getPartnerTop5();

    /**
     * 异常设备监控
     */
    AbnormalDeviceReportVO abnormalDeviceMonitoring();
}