package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Partner;
import com.dkd.manage.domain.dto.PartnerNodeCountDto;
import com.dkd.manage.domain.vo.PartnerVo;

/**
 * 合作商管理Mapper接口
 */
public interface PartnerMapper 
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
     * 删除合作商管理
     * 
     * @param id 合作商管理主键
     * @return 结果
     */
    int deletePartnerById(Long id);

    /**
     * 批量删除合作商管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deletePartnerByIds(Long[] ids);

    /**
     * 查询合作商管理列表
     * @param partner 合作商列表
     * @return partnerVo集合
     */
    List<PartnerVo> selectPartnerVoList(Partner partner);

    /**
     * 查询合作商点位数 Top 5
     * @return partnerNodeCountDto集合
     */
    List<PartnerNodeCountDto> getPartnerSalesList();
}