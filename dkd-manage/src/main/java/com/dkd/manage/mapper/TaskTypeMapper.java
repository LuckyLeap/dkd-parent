package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.TaskType;

/**
 * 工单类型Mapper接口
 */
public interface TaskTypeMapper 
{
    /**
     * 查询工单类型
     * 
     * @param typeId 工单类型主键
     * @return 工单类型
     */
    TaskType selectTaskTypeByTypeId(Long typeId);

    /**
     * 查询工单类型列表
     * 
     * @param taskType 工单类型
     * @return 工单类型集合
     */
    List<TaskType> selectTaskTypeList(TaskType taskType);

    /**
     * 新增工单类型
     * 
     * @param taskType 工单类型
     * @return 结果
     */
    int insertTaskType(TaskType taskType);

    /**
     * 修改工单类型
     * 
     * @param taskType 工单类型
     * @return 结果
     */
    int updateTaskType(TaskType taskType);

    /**
     * 删除工单类型
     * 
     * @param typeId 工单类型主键
     * @return 结果
     */
    int deleteTaskTypeByTypeId(Long typeId);

    /**
     * 批量删除工单类型
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTaskTypeByTypeIds(Long[] typeIds);
}