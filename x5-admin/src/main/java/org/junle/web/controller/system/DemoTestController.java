package org.junle.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.junle.common.annotation.Log;
import org.junle.common.core.controller.BaseController;
import org.junle.common.core.domain.AjaxResult;
import org.junle.common.core.page.TableDataInfo;
import org.junle.common.enums.BusinessType;
import org.junle.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.junle.system.domain.DemoTest;
import org.junle.system.service.IDemoTestService;

/**
 * 测试Controller
 * 
 * @author elnujuw
 * @date 2025-05-28
 */
@RestController
@RequestMapping("/system/test")
public class DemoTestController extends BaseController
{
    @Autowired
    private IDemoTestService demoTestService;

    /**
     * 查询测试列表
     */
    @PreAuthorize("@ss.hasPermi('system:test:list')")
    @GetMapping("/list")
    public TableDataInfo list(DemoTest demoTest)
    {
        startPage();
        List<DemoTest> list = demoTestService.selectDemoTestList(demoTest);
        return getDataTable(list);
    }

    /**
     * 导出测试列表
     */
    @PreAuthorize("@ss.hasPermi('system:test:export')")
    @Log(title = "测试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DemoTest demoTest)
    {
        List<DemoTest> list = demoTestService.selectDemoTestList(demoTest);
        ExcelUtil<DemoTest> util = new ExcelUtil<DemoTest>(DemoTest.class);
        util.exportExcel(response, list, "测试数据");
    }

    /**
     * 获取测试详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:test:query')")
    @GetMapping(value = "/{objectId}")
    public AjaxResult getInfo(@PathVariable("objectId") Long objectId)
    {
        return success(demoTestService.selectDemoTestByObjectId(objectId));
    }

    /**
     * 新增测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:add')")
    @Log(title = "测试", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DemoTest demoTest)
    {
        return toAjax(demoTestService.insertDemoTest(demoTest));
    }

    /**
     * 修改测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:edit')")
    @Log(title = "测试", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DemoTest demoTest)
    {
        return toAjax(demoTestService.updateDemoTest(demoTest));
    }

    /**
     * 删除测试
     */
    @PreAuthorize("@ss.hasPermi('system:test:remove')")
    @Log(title = "测试", businessType = BusinessType.DELETE)
	@DeleteMapping("/{objectIds}")
    public AjaxResult remove(@PathVariable Long[] objectIds)
    {
        return toAjax(demoTestService.deleteDemoTestByObjectIds(objectIds));
    }
}
