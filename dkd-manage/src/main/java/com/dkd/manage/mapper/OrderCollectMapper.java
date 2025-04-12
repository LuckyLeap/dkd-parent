package com.dkd.manage.mapper;

import java.time.LocalDateTime;
import java.util.List;
import com.dkd.manage.domain.OrderCollect;
import com.dkd.manage.domain.dto.DailyTurnoverDto;
import com.dkd.manage.domain.dto.OrderCollectDto;
import com.dkd.manage.domain.dto.OrderCollectQuery;
import com.dkd.manage.domain.dto.RegionSalesDto;
import org.apache.ibatis.annotations.Param;

/**
 * 对帐统计Mapper接口
 */
public interface OrderCollectMapper 
{
    /**
     * 查询对帐统计
     * 
     * @param id 对帐统计主键
     * @return 对帐统计
     */
    OrderCollect selectOrderCollectById(Long id);

    /**
     * 查询对帐统计列表
     * @return 对帐统计集合
     */
    List<OrderCollect> selectOrderCollectList(OrderCollectQuery orderCollectQuery);

    /**
     * 新增对帐统计
     * 
     * @param orderCollect 对帐统计
     * @return 结果
     */
    int insertOrderCollect(OrderCollect orderCollect);

    /**
     * 修改对帐统计
     * 
     * @param orderCollect 对帐统计
     * @return 结果
     */
    int updateOrderCollect(OrderCollect orderCollect);

    /**
     * 批量删除对帐统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteOrderCollectByIds(Long[] ids);

    /**
     * 统计在条件下的账单详情
     */
    OrderCollectDto selectOrderCollectDto(OrderCollectQuery orderCollectQuery);

    /**
     * 统计每日营业数据
     */
    List<DailyTurnoverDto> selectDailyTurnoverList(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 销售额分布统计
     */
    List<RegionSalesDto> selectRegionSalesList(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}