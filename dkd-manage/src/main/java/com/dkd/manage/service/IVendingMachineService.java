package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.VendingMachine;

/**
 * 设备管理Service接口
 */
public interface IVendingMachineService 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    VendingMachine selectVendingMachineById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param vendingMachine 设备管理
     * @return 设备管理集合
     */
    List<VendingMachine> selectVendingMachineList(VendingMachine vendingMachine);

    /**
     * 新增设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    int insertVendingMachine(VendingMachine vendingMachine);

    /**
     * 修改设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    int updateVendingMachine(VendingMachine vendingMachine);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理主键集合
     * @return 结果
     */
    int deleteVendingMachineByIds(Long[] ids);

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    int deleteVendingMachineById(Long id);

    /**
     * 根据设备编号查询设备信息
     *
     * @param innerCode 设备编号
     * @return VendingMachine
     */
    VendingMachine selectVendingMachineByInnerCode(String innerCode);
}