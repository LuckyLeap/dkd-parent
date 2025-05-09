package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Emp;

/**
 * 人员列表Service接口
 */
public interface IEmpService 
{
    /**
     * 查询人员列表
     * 
     * @param id 人员列表主键
     * @return 人员列表
     */
    Emp selectEmpById(Long id);

    /**
     * 查询人员列表列表
     * 
     * @param emp 人员列表
     * @return 人员列表集合
     */
    List<Emp> selectEmpList(Emp emp);

    /**
     * 新增人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    int insertEmp(Emp emp);

    /**
     * 修改人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    int updateEmp(Emp emp);

    /**
     * 批量删除人员列表
     * 
     * @param ids 需要删除的人员列表主键集合
     * @return 结果
     */
    int deleteEmpByIds(Long[] ids);
}