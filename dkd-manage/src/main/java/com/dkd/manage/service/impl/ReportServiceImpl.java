package com.dkd.manage.service.impl;

import com.dkd.manage.domain.dto.DailyTurnoverDto;
import com.dkd.manage.domain.dto.GoodsSalesDto;
import com.dkd.manage.domain.vo.OrderReportVO;
import com.dkd.manage.domain.vo.SalesTop10ReportVO;
import com.dkd.manage.mapper.OrderCollectMapper;
import com.dkd.manage.mapper.OrderMapper;
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
    @Autowired
    public ReportServiceImpl(OrderCollectMapper orderCollectMapper, OrderMapper orderMapper) {
        this.orderCollectMapper = orderCollectMapper;
        this.orderMapper = orderMapper;
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

        // 合并查询：一次性获取营业额和订单量
        List<DailyTurnoverDto> dailyTurnoverDtos = orderCollectMapper.selectDailyTurnoverList(beginTime, endTime);

        // 构建双数据映射
        Map<LocalDate, Double> turnoverMap = dailyTurnoverDtos.stream()
                .collect(Collectors.toMap(DailyTurnoverDto::getOrderDate, DailyTurnoverDto::getSum));
        Map<LocalDate, Long> orderCountMap = dailyTurnoverDtos.stream()
                .collect(Collectors.toMap(DailyTurnoverDto::getOrderDate, DailyTurnoverDto::getCount));

        // 填充数据列表
        List<Double> orderMoneyList = fillDataList(dateList, turnoverMap, 0.0);
        List<Long> orderCountList = fillDataList(dateList, orderCountMap, 0L);

        return OrderReportVO.builder()
                .dateList(joinList(dateList))
                .orderCountList(joinList(orderCountList))
                .orderMoneyList(joinList(orderMoneyList))
                .build();
    }

    /**
     * 根据时间统计销量排名
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        // 1. 校验日期
        Objects.requireNonNull(begin, "开始日期不能为空");
        Objects.requireNonNull(end, "结束日期不能为空");
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