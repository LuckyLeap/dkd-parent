package com.dkd.manage.service.impl;

import java.util.Arrays;
import java.util.List;

import com.dkd.common.exception.ServiceException;
import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.OrderCollect;
import com.dkd.manage.mapper.OrderCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.OrderMapper;
import com.dkd.manage.domain.Order;
import com.dkd.manage.service.IOrderService;

/**
 * 订单管理Service业务层处理
 */
@Service
public class OrderServiceImpl implements IOrderService 
{
    private final OrderMapper orderMapper;
    private final OrderCollectMapper orderCollectMapper;
    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, OrderCollectMapper orderCollectMapper) {
        this.orderMapper = orderMapper;
        this.orderCollectMapper = orderCollectMapper;
    }

    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    @Override
    public Order selectOrderById(Long id)
    {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单管理列表
     * 
     * @param order 订单管理
     * @return 订单管理
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        orderCollectMapper.insertOrderCollect(OrderCollect.builder()
                .nodeId(order.getNodeId()).nodeName(order.getNodeName())
                .partnerId(order.getPartnerId()).orderCount(order.getAmount())
                .orderTotalMoney(order.getPrice() * order.getAmount())
                .orderDate(DateUtils.getNowDate())
                .build());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        orderCollectMapper.updateOrderCollect(OrderCollect.builder()
                .nodeId(order.getNodeId()).nodeName(order.getNodeName())
                .partnerId(order.getPartnerId()).orderCount(order.getAmount())
                .orderTotalMoney(order.getPrice() * order.getAmount())
                .build());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ids)
    {
        orderCollectMapper.deleteOrderCollectByIds(ids);
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 取消订单管理信息
     *
     * @param id 订单管理主键
     * @return 结果
     */
    @Override
    public int cancelOrder(Long id) {
        Order order = orderMapper.selectOrderById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (!Arrays.asList(0L, 1L).contains(order.getStatus())) {
            throw new ServiceException("当前状态不可取消订单");
        }

        order.setStatus(4L); // 已取消状态
        order.setCancelDesc("用户手动取消");
        order.setUpdateTime(DateUtils.getNowDate());

        orderCollectMapper.deleteOrderCollectByIds(new Long[]{id});

        return orderMapper.updateOrder(order);
    }

    /**
     * 申请退款
     */
    @Override
    public int applyRefund(Long id) {
        Order order = orderMapper.selectOrderById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (!order.getPayStatus().equals(1L)) {
            throw new ServiceException("当前支付状态不可退款");
        }

        order.setPayStatus(2L); // 退款中
        order.setUpdateTime(DateUtils.getNowDate());

        orderCollectMapper.deleteOrderCollectByIds(new Long[]{id});

        return orderMapper.updateOrder(order);
    }

    /**
     * 撤销退款
     */
    @Override
    public int cancelRefund(Long id) {
        Order order = orderMapper.selectOrderById(id);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (!order.getPayStatus().equals(2L)) {
            throw new ServiceException("当前状态不可撤销退款");
        }

        order.setPayStatus(1L); // 恢复支付成功状态
        order.setUpdateTime(DateUtils.getNowDate());

        orderCollectMapper.insertOrderCollect(OrderCollect.builder()
                .nodeId(order.getNodeId()).nodeName(order.getNodeName())
                .partnerId(order.getPartnerId()).orderCount(order.getAmount())
                .orderTotalMoney(order.getPrice() * order.getAmount())
                .orderDate(DateUtils.getNowDate())
                .build());

        return orderMapper.updateOrder(order);
    }
}