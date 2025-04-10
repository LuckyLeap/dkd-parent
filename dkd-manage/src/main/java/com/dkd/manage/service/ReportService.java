package com.dkd.manage.service;

import com.dkd.manage.domain.vo.OrderReportVO;
import com.dkd.manage.domain.vo.SalesTop10ReportVO;

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
}