package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Sku;

/**
 * 商品管理Mapper接口
 */
public interface SkuMapper 
{
    /**
     * 查询商品管理
     * 
     * @param skuId 商品管理主键
     * @return 商品管理
     */
    Sku selectSkuBySkuId(Long skuId);

    /**
     * 查询商品管理列表
     * 
     * @param sku 商品管理
     * @return 商品管理集合
     */
    List<Sku> selectSkuList(Sku sku);

    /**
     * 新增商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    int insertSku(Sku sku);

    /**
     * 修改商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    int updateSku(Sku sku);

    /**
     * 删除商品管理
     * 
     * @param skuId 商品管理主键
     * @return 结果
     */
    int deleteSkuBySkuId(Long skuId);

    /**
     * 批量删除商品管理
     * 
     * @param skuIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSkuBySkuIds(Long[] skuIds);

    /**
     * 批量新增商品管理
     * @param skuList 商品管理集合
     * @return 结果
     */
    int insertSkus(List<Sku> skuList);
}