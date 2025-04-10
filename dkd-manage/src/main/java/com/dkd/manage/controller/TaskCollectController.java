package com.dkd.manage.controller;

import java.util.List;

import com.dkd.manage.domain.vo.TaskCollectVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.enums.BusinessType;
import com.dkd.manage.domain.TaskCollect;
import com.dkd.manage.service.ITaskCollectService;
import com.dkd.common.core.page.TableDataInfo;

/**
 * 工单按日统计Controller
 */
@Api(tags = "工单量统计")
@RestController
@RequestMapping("/manage/collect")
public class TaskCollectController extends BaseController
{
    private final ITaskCollectService taskCollectService;
    @Autowired
    public TaskCollectController(ITaskCollectService taskCollectService)
    {
        this.taskCollectService = taskCollectService;
    }

    /**
     * 查询工单按月统计列表
     */
    @ApiOperation("查询工单按月统计列表")
    @PreAuthorize("@ss.hasPermi('manage:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskCollect taskCollect)
    {
        startPage();
        List<TaskCollectVo> list = taskCollectService.selectTaskCollectVoList(taskCollect);
        return getDataTable(list);
    }

    /**
     * 获取工单按月统计详细信息
     */
    @ApiOperation("获取工单按月统计详细信息")
    @PreAuthorize("@ss.hasPermi('manage:collect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(taskCollectService.selectTaskCollectVoById(id));
    }

    /**
     * 新增工单按日统计
     */
    @ApiOperation("新增工单按日统计")
    @PreAuthorize("@ss.hasPermi('manage:collect:add')")
    @Log(title = "工单按日统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskCollect taskCollect)
    {
        return toAjax(taskCollectService.insertOrUpdateTaskCollect(taskCollect));
    }

    /**
     * 修改工单按日统计
     */
    @ApiOperation("修改工单按日统计")
    @PreAuthorize("@ss.hasPermi('manage:collect:edit')")
    @Log(title = "工单按日统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskCollect taskCollect)
    {
        return toAjax(taskCollectService.updateTaskCollect(taskCollect));
    }
}