package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Job;

/**
 * 自动补货任务Mapper接口
 */
public interface JobMapper 
{
    /**
     * 查询自动补货任务
     * 
     * @param id 自动补货任务主键
     * @return 自动补货任务
     */
    Job selectJobById(Long id);

    /**
     * 查询自动补货任务列表
     * 
     * @param job 自动补货任务
     * @return 自动补货任务集合
     */
    List<Job> selectJobList(Job job);

    /**
     * 新增自动补货任务
     * 
     * @param job 自动补货任务
     * @return 结果
     */
    int insertJob(Job job);

    /**
     * 修改自动补货任务
     * 
     * @param job 自动补货任务
     * @return 结果
     */
    int updateJob(Job job);

    /**
     * 删除自动补货任务
     * 
     * @param id 自动补货任务主键
     * @return 结果
     */
    int deleteJobById(Long id);

    /**
     * 批量删除自动补货任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteJobByIds(Long[] ids);
}