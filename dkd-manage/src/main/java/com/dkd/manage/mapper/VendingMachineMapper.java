package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.VendingMachine;
import com.dkd.manage.domain.dto.AbnormalDeviceDto;
import org.apache.ibatis.annotations.Select;

/**
 * 设备管理Mapper接口
 */
public interface VendingMachineMapper 
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
     * 删除设备管理
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    int deleteVendingMachineById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteVendingMachineByIds(Long[] ids);

    /**
     * 根据设备编号查询设备信息
     *
     * @param innerCode 设备编号
     * @return VendingMachine
     */
    @Select("select * from tb_vending_machine where inner_code=#{innerCode}")
    VendingMachine selectVendingMachineByInnerCode(String innerCode);

    /**
     * 查询异常设备列表
     */
    List<AbnormalDeviceDto> getAbnormalDeviceList();
}