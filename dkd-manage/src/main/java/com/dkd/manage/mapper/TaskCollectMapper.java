package com.dkd.manage.mapper;

import java.util.Date;
import java.util.List;
import com.dkd.manage.domain.TaskCollect;
import com.dkd.manage.domain.vo.TaskCollectVo;
import org.apache.ibatis.annotations.Param;

/**
 * 工单按日统计Mapper接口
 */
public interface TaskCollectMapper 
{
    /**
     * 查询工单按月统计
     * 
     * @param id 工单按日统计主键
     * @return 工单按日统计
     */
    TaskCollectVo selectDailyDataById(Long id);

    /**
     * 查询工单按月统计列表
     *
     * @param taskCollect 工单按月统计
     * @return 工单按日统计
     */
    List<TaskCollectVo> selectMonthlyCollectList(
            @Param("taskCollect") TaskCollect taskCollect,
            @Param("startDate") Date startDate
    );

    /**
     * 修改工单按月统计
     *
     * @param taskCollect 工单按日统计
     * @return 结果
     */
    int updateTaskCollect(TaskCollect taskCollect);

    /**
     * 查询工单统计详情
     *
     * @param userId 工单按日统计
     * @return 工单统计详情
     */
    TaskCollectVo selectTaskCollectVoDetail(@Param("userId") Long userId, @Param("startDate") Date startDate);

    /**
     * 新增或更新工单统计
     * @param taskCollect 工单统计信息
     * @return 影响行数
     */
    int insertOrUpdateTaskCollect(TaskCollect taskCollect);

    /**
     * 根据用户ID和日期查询现有记录
     * @param userId 用户ID
     * @param collectDate 统计日期
     * @return 工单统计记录
     */
    TaskCollect selectByUserAndDate(@Param("userId") Long userId, @Param("collectDate") Date collectDate);
}