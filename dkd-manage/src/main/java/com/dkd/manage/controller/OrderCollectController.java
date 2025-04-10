package com.dkd.manage.controller;

import java.util.List;
import com.dkd.manage.domain.dto.OrderCollectQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.enums.BusinessType;
import com.dkd.manage.domain.OrderCollect;
import com.dkd.manage.service.IOrderCollectService;
import com.dkd.common.core.page.TableDataInfo;

/**
 * 对帐统计Controller
 */
@Api(tags = "对帐统计")
@RestController
@RequestMapping("/manage/orderCollect")
public class OrderCollectController extends BaseController
{
    private final IOrderCollectService orderCollectService;
    @Autowired
    public OrderCollectController(IOrderCollectService orderCollectService)
    {
        this.orderCollectService = orderCollectService;
    }

    /**
     * 查询对帐统计列表
     */
    @ApiOperation("查询对帐统计列表")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderCollectQuery orderCollectQuery)
    {
        startPage();
        List<OrderCollect> list = orderCollectService.selectOrderCollectList(orderCollectQuery);
        return getDataTable(list);
    }

    /**
     * 获取对帐统计详细信息
     */
    @ApiOperation("获取对帐统计详细信息")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderCollectService.selectOrderCollectById(id));
    }

    /**
     * 新增对帐统计
     */
    @ApiOperation("新增对帐统计")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:add')")
    @Log(title = "对帐统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderCollect orderCollect)
    {
        return toAjax(orderCollectService.insertOrderCollect(orderCollect));
    }

    /**
     * 修改对帐统计
     */
    @ApiOperation("修改对帐统计")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:edit')")
    @Log(title = "对帐统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderCollect orderCollect)
    {
        return toAjax(orderCollectService.updateOrderCollect(orderCollect));
    }

    /**
     * 删除对帐统计
     */
    @ApiOperation("删除对帐统计")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:remove')")
    @Log(title = "对帐统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderCollectService.deleteOrderCollectByIds(ids));
    }

    /**
     * 统计在条件下的账单详情
     */
    @ApiOperation("统计在条件下的账单详情")
    @PreAuthorize("@ss.hasPermi('manage:orderCollect:data')")
    @GetMapping("/data")
    public AjaxResult selectOrderCollectDto(OrderCollectQuery orderCollectQuery)
    {
        return success(orderCollectService.selectOrderCollectDto(orderCollectQuery));
    }
}