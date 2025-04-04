package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.TaskDetails;

/**
 * 工单详情Mapper接口
 */
public interface TaskDetailsMapper 
{
    /**
     * 查询工单详情
     * 
     * @param detailsId 工单详情主键
     * @return 工单详情
     */
    TaskDetails selectTaskDetailsByDetailsId(Long detailsId);

    /**
     * 查询工单详情列表
     * 
     * @param taskDetails 工单详情
     * @return 工单详情集合
     */
    List<TaskDetails> selectTaskDetailsList(TaskDetails taskDetails);

    /**
     * 新增工单详情
     * 
     * @param taskDetails 工单详情
     * @return 结果
     */
    int insertTaskDetails(TaskDetails taskDetails);

    /**
     * 修改工单详情
     * 
     * @param taskDetails 工单详情
     * @return 结果
     */
    int updateTaskDetails(TaskDetails taskDetails);

    /**
     * 删除工单详情
     * 
     * @param detailsId 工单详情主键
     * @return 结果
     */
    int deleteTaskDetailsByDetailsId(Long detailsId);

    /**
     * 批量删除工单详情
     * 
     * @param detailsIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTaskDetailsByDetailsIds(Long[] detailsIds);

    /**
     * 批量新增工单详情
     * @param taskDetailsList 工单详情集合
     * @return 结果
     */
    int batchInsertTaskDetails(List<TaskDetails> taskDetailsList);
}