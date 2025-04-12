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
@Api(tags = "首页-数据统计")
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
    @PreAuthorize("@ss.hasPermi('manage:salesTop10:data')")
    @GetMapping("/salesTop10")
    public AjaxResult getSalesTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return success(reportService.getSalesTop10(begin, end));
    }

    /**
     * 销售额分布统计
     */
    @ApiOperation("查询销售额分布统计")
    @PreAuthorize("@ss.hasPermi('manage:regionReportOrders:data')")
    @GetMapping("/regionReportOrders")
    public AjaxResult getRegionReportOrders(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        return success(reportService.getRegionReportOrders(begin, end));
    }

    /**
     * 合作商点位数 Top 5
     */
    @ApiOperation("查询合作商点位数 Top 5")
    @PreAuthorize("@ss.hasPermi('manage:partnerTop5:data')")
    @GetMapping("/partnerTop5")
    public AjaxResult getPartnerTop5() {
        return success(reportService.getPartnerTop5());
    }

    /**
     * 异常设备监控【故障时间 updateTime 】
     */
    @ApiOperation("异常设备监控")
    @PreAuthorize("@ss.hasPermi('manage:abnormalDeviceMonitoring:data')")
    @GetMapping("/abnormalDeviceMonitoring")
    public AjaxResult abnormalDeviceMonitoring() {
        return success(reportService.abnormalDeviceMonitoring());
    }
}