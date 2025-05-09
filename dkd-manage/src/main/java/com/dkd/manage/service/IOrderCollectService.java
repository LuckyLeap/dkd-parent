package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.OrderCollect;
import com.dkd.manage.domain.dto.OrderCollectDto;
import com.dkd.manage.domain.dto.OrderCollectQuery;

/**
 * 对帐统计Service接口
 */
public interface IOrderCollectService 
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
     * 
     * @param orderCollectQuery 对帐统计查询条件
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
     * @param ids 需要删除的对帐统计主键集合
     * @return 结果
     */
    int deleteOrderCollectByIds(Long[] ids);

    /**
     * 统计在条件下的账单详情
     */
    OrderCollectDto selectOrderCollectDto(OrderCollectQuery orderCollectQuery);
}