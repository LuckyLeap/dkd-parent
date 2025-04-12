package com.dkd.manage.service.impl;

import com.dkd.manage.domain.dto.*;
import com.dkd.manage.domain.vo.*;
import com.dkd.manage.mapper.OrderCollectMapper;
import com.dkd.manage.mapper.OrderMapper;
import com.dkd.manage.mapper.PartnerMapper;
import com.dkd.manage.mapper.VendingMachineMapper;
import com.dkd.manage.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final OrderCollectMapper orderCollectMapper;
    private final OrderMapper orderMapper;
    private final PartnerMapper partnerMapper;
    private final VendingMachineMapper vendingMachineMapper;
    @Autowired
    public ReportServiceImpl(OrderCollectMapper orderCollectMapper, OrderMapper orderMapper, PartnerMapper partnerMapper, VendingMachineMapper vendingMachineMapper) {
        this.orderCollectMapper = orderCollectMapper;
        this.orderMapper = orderMapper;
        this.partnerMapper = partnerMapper;
        this.vendingMachineMapper = vendingMachineMapper;
    }

    /**
     * 根据时间统计营业额
     */
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        // 公共校验并生成日期列表
        List<LocalDate> dateList = generateDateListAndValidate(begin, end);

        // 修正时间范围（确保覆盖 end 日全天）
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = end.plusDays(1).atStartOfDay().minusNanos(1);

        // 合并查询：一次性获取营业额
        List<DailyTurnoverDto> dailyTurnoverDtos = orderCollectMapper.selectDailyTurnoverList(beginTime, endTime);

        // 构建双数据映射
        Map<LocalDate, Double> turnoverMap = dailyTurnoverDtos.stream()
                .collect(Collectors.toMap(DailyTurnoverDto::getOrderDate, DailyTurnoverDto::getSum));

        // 填充数据列表
        List<Double> orderMoneyList = fillDataList(dateList, turnoverMap, 0.0);

        return OrderReportVO.builder()
                .dateList(joinList(dateList))
                .orderMoneyList(joinList(orderMoneyList))
                .build();
    }

    /**
     * 根据时间统计销量排名
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        // 1. 校验日期
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("日期范围错误");
        }

        // 2. 修正时间范围（覆盖 end 日全天）
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = end.plusDays(1).atStartOfDay().minusNanos(1);

        // 3. 查询销量Top10商品（状态=2的已完成订单）
        List<GoodsSalesDto> goodsSalesList = orderMapper.getSalesTop10(beginTime, endTime);

        // 4. 提取名称和销量列表
        List<String> nameList = goodsSalesList.stream().map(GoodsSalesDto::getName).collect(Collectors.toList());
        List<Integer> numberList = goodsSalesList.stream().map(GoodsSalesDto::getNumber).collect(Collectors.toList());

        // 5. 构建返回结果
        return SalesTop10ReportVO.builder()
                .nameList(joinList(nameList))
                .numberList(joinList(numberList))
                .build();
    }

    /**
     * 销售额分布统计
     */
    @Override
    public RegionReportOrdersVO getRegionReportOrders(LocalDate begin, LocalDate end) {
        // 校验日期
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("日期范围错误");
        }

        // 修正时间范围（覆盖 end 日全天）
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = end.plusDays(1).atStartOfDay().minusNanos(1);

        // 查询销售额（按地区）
        List<RegionSalesDto> regionSalesList = orderCollectMapper.selectRegionSalesList(beginTime, endTime);

        // 提取名称和销售额列表
        List<String> nameList = regionSalesList.stream().map(RegionSalesDto::getRegionName).collect(Collectors.toList());
        List<Double> moneyList = regionSalesList.stream().map(RegionSalesDto::getSum).collect(Collectors.toList());

        return RegionReportOrdersVO.builder()
                .nameList(joinList(nameList))
                .moneyList(joinList(moneyList))
                .build();
    }

    /**
     * 合作商点位数 Top 5
     */
    public PartnerTop5ReportVO getPartnerTop5() {
        // 1. 查询合作商点位数
        List<PartnerNodeCountDto> partnerNodeCountList = partnerMapper.getPartnerSalesList();

        // 2. 提取名称和点位数列表
        List<String> nameList = partnerNodeCountList.stream().map(PartnerNodeCountDto::getPartnerName).collect(Collectors.toList());
        List<Integer> nodeCountList = partnerNodeCountList.stream().map(PartnerNodeCountDto::getNodeCount).collect(Collectors.toList());

        return PartnerTop5ReportVO.builder()
                .nameList(joinList(nameList))
                .countList(joinList(nodeCountList))
                .partnerSum(partnerNodeCountList.size())
                .nodeSum(partnerNodeCountList.stream().mapToInt(PartnerNodeCountDto::getNodeCount).sum())
                .build();
    }

    /**
     * 获取异常设备监控
     */
    public AbnormalDeviceReportVO abnormalDeviceMonitoring() {
        // 1. 查询所有异常设备信息
        List<AbnormalDeviceDto> abnormalDeviceList = vendingMachineMapper.getAbnormalDeviceList();

        // 2. 提取更新时间、内部编号和地址列表
        List<LocalDateTime> updateTimeList = abnormalDeviceList.stream().map(AbnormalDeviceDto::getUpdateTime).collect(Collectors.toList());
        List<String> innerCodeList = abnormalDeviceList.stream().map(AbnormalDeviceDto::getInnerCode).collect(Collectors.toList());
        List<String> addrList = abnormalDeviceList.stream().map(AbnormalDeviceDto::getAddr).collect(Collectors.toList());

        // 3. 构建返回结果
        return AbnormalDeviceReportVO.builder()
                .updateTimeList(joinList(updateTimeList))
                .innerCodeList(joinList(innerCodeList))
                .addrList(joinList(addrList))
                .build();
    }

// 生成日期列表并校验
    private List<LocalDate> generateDateListAndValidate(LocalDate begin, LocalDate end) {
        Objects.requireNonNull(begin, "开始日期不能为空");
        Objects.requireNonNull(end, "结束日期不能为空");
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("日期范围错误");
        }
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate currentDate = begin;
        while (!currentDate.isAfter(end)) {
            dateList.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dateList;
    }

    // 泛型填充方法
    private <T> List<T> fillDataList(
            List<LocalDate> dateList,
            Map<LocalDate, T> dataMap,
            T defaultValue
    ) {
        return dateList.stream()
                .map(date -> dataMap.getOrDefault(date, defaultValue))
                .collect(Collectors.toList());
    }

    // 泛型拼接方法
    private <T> String joinList(List<T> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}