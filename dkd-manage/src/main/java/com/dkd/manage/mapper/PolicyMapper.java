package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Policy;

/**
 * 策略管理Mapper接口
 */
public interface PolicyMapper 
{
    /**
     * 查询策略管理
     * 
     * @param policyId 策略管理主键
     * @return 策略管理
     */
    Policy selectPolicyByPolicyId(Long policyId);

    /**
     * 查询策略管理列表
     * 
     * @param policy 策略管理
     * @return 策略管理集合
     */
    List<Policy> selectPolicyList(Policy policy);

    /**
     * 新增策略管理
     * 
     * @param policy 策略管理
     * @return 结果
     */
    int insertPolicy(Policy policy);

    /**
     * 修改策略管理
     * 
     * @param policy 策略管理
     * @return 结果
     */
    int updatePolicy(Policy policy);

    /**
     * 删除策略管理
     * 
     * @param policyId 策略管理主键
     * @return 结果
     */
    int deletePolicyByPolicyId(Long policyId);

    /**
     * 批量删除策略管理
     * 
     * @param policyIds 需要删除的数据主键集合
     * @return 结果
     */
    int deletePolicyByPolicyIds(Long[] policyIds);
}