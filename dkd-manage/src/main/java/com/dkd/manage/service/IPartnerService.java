package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Partner;
import com.dkd.manage.domain.vo.PartnerVo;

/**
 * 合作商管理Service接口
 */
public interface IPartnerService 
{
    /**
     * 查询合作商管理
     *
     * @param id 合作商管理主键
     * @return 合作商管理
     */
    Partner selectPartnerById(Long id);

    /**
     * 查询合作商管理列表
     *
     * @param partner 合作商管理
     * @return 合作商管理集合
     */
    List<Partner> selectPartnerList(Partner partner);

    /**
     * 新增合作商管理
     * 
     * @param partner 合作商管理
     * @return 结果
     */
    int insertPartner(Partner partner);

    /**
     * 修改合作商管理
     * 
     * @param partner 合作商管理
     * @return 结果
     */
    int updatePartner(Partner partner);

    /**
     * 批量删除合作商管理
     * 
     * @param ids 需要删除的合作商管理主键集合
     * @return 结果
     */
    int deletePartnerByIds(Long[] ids);

    /**
     * 删除合作商管理信息
     * 
     * @param id 合作商管理主键
     * @return 结果
     */
    int deletePartnerById(Long id);

    /**
     * 查询合作商管理列表
     *
     * @param partner 合作商管理
     * @return 合作商管理集合
     */
    List<PartnerVo> selectPartnerVoList(Partner partner);
}