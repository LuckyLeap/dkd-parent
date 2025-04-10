package com.dkd.manage.controller;

import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.manage.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 数据统计
 */
@Api(tags = "订单数据统计")
@RestController
@RequestMapping("/manage/report")
public class ReportController extends BaseController {

    private final ReportService reportService;
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * 订单统计
     */
    @ApiOperation("查询订单统计详情")
    @PreAuthorize("@ss.hasPermi('manage:ordersStatistics:data')")
    @GetMapping("/ordersStatistics")
    public AjaxResult ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return success(reportService.getOrdersStatistics(begin, end));
    }

    /**
     * 销量排名 Top 10
     */
    @ApiOperation("查询销量排名 Top 10")
    @PreAuthorize("@ss.hasPermi('manage:top10:data')")
    @GetMapping("/top10")
    public AjaxResult getSalesTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return success(reportService.getSalesTop10(begin, end));
    }
}