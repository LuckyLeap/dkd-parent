package com.dkd.manage.mapper;

import java.time.LocalDateTime;
import java.util.List;
import com.dkd.manage.domain.Order;
import com.dkd.manage.domain.dto.GoodsSalesDto;
import org.apache.ibatis.annotations.Param;

/**
 * 订单管理Mapper接口
 */
public interface OrderMapper 
{
    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    Order selectOrderById(Long id);

    /**
     * 查询订单管理列表
     * 
     * @param order 订单管理
     * @return 订单管理集合
     */
    List<Order> selectOrderList(Order order);

    /**
     * 新增订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    int insertOrder(Order order);

    /**
     * 修改订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    int updateOrder(Order order);

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteOrderByIds(Long[] ids);

    /**
     * 查询订单销售Top10
     */
    List<GoodsSalesDto> getSalesTop10(
            @Param("beginTime") LocalDateTime beginTime,
            @Param("endTime") LocalDateTime endTime);
}