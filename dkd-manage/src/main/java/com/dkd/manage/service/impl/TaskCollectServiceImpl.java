package com.dkd.manage.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dkd.manage.domain.vo.TaskCollectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.TaskCollectMapper;
import com.dkd.manage.domain.TaskCollect;
import com.dkd.manage.service.ITaskCollectService;

/**
 * 工单按日统计Service业务层处理
 */
@Service
public class TaskCollectServiceImpl implements ITaskCollectService 
{
    private final  TaskCollectMapper taskCollectMapper;
    @Autowired
    public TaskCollectServiceImpl(TaskCollectMapper taskCollectMapper)
    {
        this.taskCollectMapper = taskCollectMapper;
    }

    /**
     * 查询工单按月统计
     * 
     * @param id 工单按日统计主键
     * @return 工单按日统计
     */
    @Override
    public TaskCollectVo selectTaskCollectVoById(Long id) {
        // 1. 根据ID查询当日基础数据
        TaskCollectVo dailyData = taskCollectMapper.selectDailyDataById(id);
        if (dailyData == null) {
            return null;
        }

        // 2. 获取当月起始日期
        Date startDate = getMonthStartDate();

        // 3. 根据userId查询统计信息
        TaskCollectVo statsData = taskCollectMapper.selectTaskCollectVoDetail(dailyData.getUserId(), startDate);

        // 4. 合并数据
        if (statsData != null) {
            dailyData.setFinishCountY(statsData.getFinishCountY());
            dailyData.setProgressCountY(statsData.getProgressCountY());
            dailyData.setCancelCountY(statsData.getCancelCountY());
            dailyData.setFinishCountS(statsData.getFinishCountS());
            dailyData.setProgressCountS(statsData.getProgressCountS());
            dailyData.setCancelCountS(statsData.getCancelCountS());
        }

        return dailyData;
    }

    /**
     * 查询工单按月统计列表
     * 
     * @param taskCollect 工单按日统计
     * @return 工单按日统计
     */
    @Override
    public List<TaskCollectVo> selectTaskCollectVoList(TaskCollect taskCollect) {
        Date startDate = getMonthStartDate();
        return taskCollectMapper.selectMonthlyCollectList(taskCollect, startDate);
    }

    /**
     * 新增工单按月统计
     * @param taskCollect 工单按日统计
     */
    @Override
    public int insertOrUpdateTaskCollect(TaskCollect taskCollect) {
        // 设置当前日期如果未指定
        if(taskCollect.getCollectDate() == null) {
            taskCollect.setCollectDate(new Date());
        }

        // 检查记录是否存在
        TaskCollect existing = taskCollectMapper.selectByUserAndDate(
                taskCollect.getUserId(),
                taskCollect.getCollectDate()
        );

        if(existing != null) {
            // 记录存在，更新统计值
            taskCollect.setFinishCount(
                    (taskCollect.getFinishCount() != null ? taskCollect.getFinishCount() : 0) +
                            (existing.getFinishCount() != null ? existing.getFinishCount() : 0)
            );
            taskCollect.setProgressCount(
                    (taskCollect.getProgressCount() != null ? taskCollect.getProgressCount() : 0) +
                            (existing.getProgressCount() != null ? existing.getProgressCount() : 0)
            );
            taskCollect.setCancelCount(
                    (taskCollect.getCancelCount() != null ? taskCollect.getCancelCount() : 0) +
                            (existing.getCancelCount() != null ? existing.getCancelCount() : 0)
            );
            taskCollect.setId(existing.getId());
        }

        return taskCollectMapper.insertOrUpdateTaskCollect(taskCollect);
    }

    /**
     * 修改工单按月统计
     *
     * @param taskCollect 工单按日统计
     * @return 结果
     */
    @Override
    public int updateTaskCollect(TaskCollect taskCollect)
    {
        return taskCollectMapper.updateTaskCollect(taskCollect);
    }

    /**
     * 获取当月的开始日期
     */
    private Date getMonthStartDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}