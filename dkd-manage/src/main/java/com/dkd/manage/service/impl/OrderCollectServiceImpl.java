package com.dkd.manage.service.impl;

import java.util.List;

import com.dkd.manage.domain.dto.OrderCollectDto;
import com.dkd.manage.domain.dto.OrderCollectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.OrderCollectMapper;
import com.dkd.manage.domain.OrderCollect;
import com.dkd.manage.service.IOrderCollectService;

/**
 * 对帐统计Service业务层处理
 */
@Service
public class OrderCollectServiceImpl implements IOrderCollectService 
{
    private final  OrderCollectMapper orderCollectMapper;
    @Autowired
    public OrderCollectServiceImpl(OrderCollectMapper orderCollectMapper)
    {
        this.orderCollectMapper = orderCollectMapper;
    }

    /**
     * 查询对帐统计
     * 
     * @param id 对帐统计主键
     * @return 对帐统计
     */
    @Override
    public OrderCollect selectOrderCollectById(Long id)
    {
        return orderCollectMapper.selectOrderCollectById(id);
    }

    /**
     * 查询对帐统计列表
     * @return 对帐统计
     */
    @Override
    public List<OrderCollect> selectOrderCollectList(OrderCollectQuery orderCollectQuery)
    {
        return orderCollectMapper.selectOrderCollectList(orderCollectQuery);
    }

    /**
     * 新增对帐统计
     * 
     * @param orderCollect 对帐统计
     * @return 结果
     */
    @Override
    public int insertOrderCollect(OrderCollect orderCollect)
    {
        return orderCollectMapper.insertOrderCollect(orderCollect);
    }

    /**
     * 修改对帐统计
     * 
     * @param orderCollect 对帐统计
     * @return 结果
     */
    @Override
    public int updateOrderCollect(OrderCollect orderCollect)
    {
        return orderCollectMapper.updateOrderCollect(orderCollect);
    }

    /**
     * 批量删除对帐统计
     * 
     * @param ids 需要删除的对帐统计主键
     * @return 结果
     */
    @Override
    public int deleteOrderCollectByIds(Long[] ids)
    {
        return orderCollectMapper.deleteOrderCollectByIds(ids);
    }

    /**
     * 统计在条件下的账单详情
     */
    @Override
    public OrderCollectDto selectOrderCollectDto(OrderCollectQuery orderCollectQuery)
    {
        return orderCollectMapper.selectOrderCollectDto(orderCollectQuery);
    }
}