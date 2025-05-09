package com.dkd.manage.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.dkd.common.constant.DkdContants;
import com.dkd.common.exception.ServiceException;
import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.*;
import com.dkd.manage.domain.dto.TaskDetailsDto;
import com.dkd.manage.domain.dto.TaskDto;
import com.dkd.manage.domain.vo.TaskVo;
import com.dkd.manage.mapper.TaskCollectMapper;
import com.dkd.manage.service.IEmpService;
import com.dkd.manage.service.ITaskDetailsService;
import com.dkd.manage.service.IVendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.TaskMapper;
import com.dkd.manage.service.ITaskService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工单Service业务层处理
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    private final TaskMapper taskMapper;
    private final IVendingMachineService vendingMachineService;
    private final IEmpService empService;
    private final ITaskDetailsService taskDetailsService;
    private final RedisTemplate redisTemplate;
    private final TaskCollectMapper taskCollectMapper;
    @Autowired
    public TaskServiceImpl(TaskMapper taskMapper, IVendingMachineService vendingMachineService, IEmpService empService, ITaskDetailsService taskDetailsService, RedisTemplate redisTemplate, TaskCollectMapper taskCollectMapper) {
        this.taskMapper = taskMapper;
        this.vendingMachineService = vendingMachineService;
        this.empService = empService;
        this.taskDetailsService = taskDetailsService;
        this.redisTemplate = redisTemplate;
        this.taskCollectMapper = taskCollectMapper;
    }

    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    @Override
    public Task selectTaskByTaskId(Long taskId)
    {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    /**
     * 查询工单列表
     * 
     * @param task 工单
     * @return 工单
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskIds(Long[] taskIds)
    {
        return taskMapper.deleteTaskByTaskIds(taskIds);
    }

    /**
     * 查询运维工单列表
     * @param task 工单
     * @return TaskVo集合
     */
    @Override
    public List<TaskVo> selectTaskVoList(Task task) {
        return taskMapper.selectTaskVoList(task);
    }

    /**
     * 新增运营、运维工单
     *
     * @param taskDto 工单dto
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTaskDto(TaskDto taskDto) {
        //1. 查询售货机是否存在
        VendingMachine vm = vendingMachineService.selectVendingMachineByInnerCode(taskDto.getInnerCode());
        if (vm == null) {
            throw new ServiceException("设备不存在");
        }
        //2. 校验售货机状态与工单类型是否相符
        checkCreateTask(vm.getVmStatus(), taskDto.getProductTypeId());
        //3. 检查设备是否有未完成的同类型工单
        hasTask(taskDto);
        //4. 查询并校验员工是否存在
        Emp emp = empService.selectEmpById(taskDto.getUserId());
        if (emp == null) {
            throw new ServiceException("员工不存在");
        }
        //5. 校验员工区域是否匹配
        if (!emp.getRegionId().equals(vm.getRegionId())) {
            throw new ServiceException("员工区域与设备区域不一致，无法处理此工单");
        }
        //6. 将dto转为po并补充属性，保存工单
        Task task = BeanUtil.copyProperties(taskDto, Task.class);// 属性复制
        task.setTaskStatus(DkdContants.TASK_STATUS_CREATE);// 创建工单
        task.setUserName(emp.getUserName());// 执行人名称
        task.setRegionId(vm.getRegionId());// 所属区域id
        task.setAddr(vm.getAddr());// 地址
        task.setCreateTime(DateUtils.getNowDate());// 创建时间
        task.setTaskCode(generateTaskCode());// 工单编号
        int taskResult = taskMapper.insertTask(task);

        //7.向工单统计表中新增一条记录
        TaskCollect taskCollect = TaskCollect.builder()
                .userId(task.getUserId())
                .progressCount(1L)
                .collectDate(DateUtils.getNowDate())
                .build();
        taskCollectMapper.insertOrUpdateTaskCollect(taskCollect);

        //8. 判断是否为补货工单
        if (taskDto.getProductTypeId().equals(DkdContants.TASK_TYPE_SUPPLY)) {
            // 9.保存工单详情
            List<TaskDetailsDto> details = taskDto.getDetails();
            if (CollUtil.isEmpty(details)) {
                throw new ServiceException("补货工单详情不能为空");
            }
            // 将dto转为po补充属性
            List<TaskDetails> taskDetailsList = details.stream().map(dto -> {
                TaskDetails taskDetails = BeanUtil.copyProperties(dto, TaskDetails.class);
                taskDetails.setTaskId(task.getTaskId());
                return taskDetails;
            }).collect(Collectors.toList());
            // 批量新增
            taskDetailsService.batchInsertTaskDetails(taskDetailsList);
        }

        return taskResult;
    }

    /**
     * 取消工单
     * @param task 工单
     * @return 结果
     */
    @Override
    public int cancelTask(Task task) {
        //1. 判断工单状态是否可以取消
        // 先根据工单id查询数据库
        Task taskDb = taskMapper.selectTaskByTaskId(task.getTaskId());
        // 判断工单状态是否为已取消，如果是，则抛出异常
        if (taskDb.getTaskStatus().equals(DkdContants.TASK_STATUS_CANCEL)) {
            throw new ServiceException("该工单已取消了，不能再次取消");
        }
        // 判断工单状态是否为已完成，如果是，则抛出异常
        if (taskDb.getTaskStatus().equals(DkdContants.TASK_STATUS_FINISH)) {
            throw new ServiceException("该工单已完成了，不能取消");
        }
        //2. 设置更新字段
        task.setTaskStatus(DkdContants.TASK_STATUS_CANCEL);// 工单状态：取消
        task.setUpdateTime(DateUtils.getNowDate());// 更新时间

        //3. 更新工单统计表
        TaskCollect taskCollect = TaskCollect.builder()
                .userId(taskDb.getUserId())
                .progressCount(-1L)
                .cancelCount(1L)
                .collectDate(DateUtils.getNowDate())
                .build();
        taskCollectMapper.updateTaskCollect(taskCollect);

        //4. 更新工单
        return taskMapper.updateTask(task);
    }

    /**
     * 生成并获取当天任务代码的唯一标识。
     * 该方法首先尝试从Redis中获取当天的任务代码计数，如果不存在，则初始化为1并返回"日期0001"格式的字符串。
     * 如果存在，则对计数加1并返回更新后的任务代码。
     *
     * @return 返回当天任务代码的唯一标识，格式为"日期XXXX"，其中XXXX是四位数字的计数。
     */
    public String generateTaskCode() {
        // 获取当前日期并格式化为"yyyyMMdd"
        String dateStr = DateUtils.getDate().replaceAll("-", "");
        // 根据日期生成redis的键
        String key = "dkd.task.code." + dateStr;

        // 使用INCR命令进行原子操作，获取并增加计数器
        Long count = redisTemplate.opsForValue().increment(key, 1);

        // 如果计数器为1，说明是首次设置，设置过期时间为1天
        if (count == 1) {
            redisTemplate.expire(key, Duration.ofDays(1));
        }

        // 返回工单编号（日期+四位计数）
        return dateStr + String.format("%04d", count);
    }

    /**
     * 检查设备是否已有未完成的同类型工单。
     * 本方法用于在创建新工单前，验证指定设备是否已经有处于进行中的同类型工单。
     * 如果存在未完成的同类型工单，则抛出服务异常，阻止新工单的创建。
     *
     * @param taskDto 工单数据传输对象，包含设备编号和工单类型ID。
     */
    private void hasTask(TaskDto taskDto) {
        // 创建Task对象，并设置设备编号和工单类型ID，以及任务状态为进行中
        Task taskParam = new Task();
        taskParam.setInnerCode(taskDto.getInnerCode());
        taskParam.setProductTypeId(taskDto.getProductTypeId());
        taskParam.setTaskStatus(DkdContants.TASK_STATUS_PROGRESS);

        // 查询数据库中符合指定条件的工单列表
        List<Task> taskList = taskMapper.selectTaskList(taskParam);

        // 如果存在未完成的同类型工单，则抛出服务异常
        if (CollUtil.isNotEmpty(taskList)) {
            throw new ServiceException("该设备有未完成的同类型工单，不能重复创建");
        }
    }

    /**
     * 根据设备的状态和任务类型，验证是否可以创建相应的任务。
     * 如果条件不满足，抛出服务异常。
     *
     * @param vmStatus      设备的状态，表示设备是否在运行。
     * @param productTypeId 任务的类型，决定任务的性质（投放、维修、补货、撤机）。
     */
    private void checkCreateTask(Long vmStatus, Long productTypeId) {
        // 如果是投放工单，且设备状态为运行中，则抛出异常，因为设备已在运营中无法进行投放
        if (Objects.equals(productTypeId, DkdContants.TASK_TYPE_DEPLOY) && Objects.equals(vmStatus, DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("该设备状态为运行中，无法进行投放");
        }

        // 如果是维修工单，且设备状态不是运行中，则抛出异常，因为设备不在运营中无法进行维修
        if (Objects.equals(productTypeId, DkdContants.TASK_TYPE_REPAIR) && !Objects.equals(vmStatus, DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("该设备状态不是运行中，无法进行维修");
        }

        // 如果是补货工单，且设备状态不是运行中，则抛出异常，因为设备不在运营状态无法进行补货
        if (Objects.equals(productTypeId, DkdContants.TASK_TYPE_SUPPLY) && !Objects.equals(vmStatus, DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("该设备状态不是运行中，无法进行补货");
        }

        // 如果是撤机工单，且设备状态不是运行中，则抛出异常，因为设备不在运营状态无法进行撤机
        if (Objects.equals(productTypeId, DkdContants.TASK_TYPE_REVOKE) && !Objects.equals(vmStatus, DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("该设备状态不是运行中，无法进行撤机");
        }
    }
}