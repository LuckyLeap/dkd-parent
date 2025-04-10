package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Order;

/**
 * 订单管理Service接口
 */
public interface IOrderService 
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
     * @param ids 需要删除的订单管理主键集合
     * @return 结果
     */
    int deleteOrderByIds(Long[] ids);

    /**
     * 取消订单
     * @param id 订单id
     */
    int cancelOrder(Long id);

    /**
     * 申请退款
     * @param id 订单id
     */
    int applyRefund(Long id);

    /**
     * 撤销退款
     * @param id 订单id
     */
    int cancelRefund(Long id);
}