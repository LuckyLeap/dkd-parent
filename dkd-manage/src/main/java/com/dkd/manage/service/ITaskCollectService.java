package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.TaskCollect;
import com.dkd.manage.domain.vo.TaskCollectVo;

/**
 * 工单按日统计Service接口
 */
public interface ITaskCollectService 
{
    /**
     * 查询工单按日统计
     * 
     * @param id 工单按日统计主键
     * @return 工单按日统计
     */
    TaskCollectVo selectTaskCollectVoById(Long id);

    /**
     * 查询工单按日统计列表
     * 
     * @param taskCollect 工单按日统计
     * @return 工单按日统计集合
     */
    List<TaskCollectVo> selectTaskCollectVoList(TaskCollect taskCollect);

    /**
     * 修改工单按日统计
     *
     * @param taskCollect 工单按日统计
     * @return 结果
     */
    int updateTaskCollect(TaskCollect taskCollect);

    /**
     * 新增工单按日统计
     *
     * @param taskCollect 工单按日统计
     */
    int insertOrUpdateTaskCollect(TaskCollect taskCollect);
}