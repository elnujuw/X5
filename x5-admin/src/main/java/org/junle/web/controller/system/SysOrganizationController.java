package org.junle.web.controller.system;

import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.junle.common.annotation.Log;
import org.junle.common.constant.UserConstants;
import org.junle.common.core.controller.BaseController;
import org.junle.common.core.domain.AjaxResult;
import org.junle.common.core.domain.entity.SysOrganization;
import org.junle.common.enums.BusinessType;
import org.junle.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.junle.system.service.ISysOrganizationService;

/**
 * 组织架构信息
 * 
 * @author elnujuw
 */
@RestController
@RequestMapping("/system/organization")
public class SysOrganizationController extends BaseController
{
    @Autowired
    private ISysOrganizationService organizationService;

    /**
     * 获取组织架构列表
     */
    @PreAuthorize("@ss.hasPermi('system:organization:list')")
    @GetMapping("/list")
    public AjaxResult list(SysOrganization organization)
    {
        List<SysOrganization> organizations = organizationService.selectOrganizationList(organization);
        return success(organizations);
    }

    /**
     * 查询组织架构列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:organization:list')")
    @GetMapping("/list/exclude/{organizationId}")
    public AjaxResult excludeChild(@PathVariable(value = "organizationId", required = false) Long organizationId)
    {
        List<SysOrganization> organizations = organizationService.selectOrganizationList(new SysOrganization());
        organizations.removeIf(d -> d.getOrganizationId().intValue() == organizationId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), organizationId + ""));
        return success(organizations);
    }

    /**
     * 根据组织架构编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:organization:query')")
    @GetMapping(value = "/{organizationId}")
    public AjaxResult getInfo(@PathVariable Long organizationId)
    {
        organizationService.checkOrganizationDataScope(organizationId);
        return success(organizationService.selectOrganizationById(organizationId));
    }

    /**
     * 新增组织架构
     */
    @PreAuthorize("@ss.hasPermi('system:organization:add')")
    @Log(title = "组织架构管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysOrganization organization)
    {
        if (!organizationService.checkOrganizationNameUnique(organization))
        {
            return error("新增组织架构'" + organization.getOrganizationName() + "'失败，组织架构名称已存在");
        }
        organization.setCreateBy(getUsername());
        return toAjax(organizationService.insertOrganization(organization));
    }

    /**
     * 修改组织架构
     */
    @PreAuthorize("@ss.hasPermi('system:organization:edit')")
    @Log(title = "组织架构管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysOrganization organization)
    {
        Long organizationId = organization.getOrganizationId();
        organizationService.checkOrganizationDataScope(organizationId);
        if (!organizationService.checkOrganizationNameUnique(organization))
        {
            return error("修改组织架构'" + organization.getOrganizationName() + "'失败，组织架构名称已存在");
        }
        else if (organization.getParentId().equals(organizationId))
        {
            return error("修改组织架构'" + organization.getOrganizationName() + "'失败，上级组织架构不能是自己");
        }
        else if (StringUtils.equals(UserConstants.ORG_DISABLE, organization.getStatus()) && organizationService.selectNormalChildrenOrganizationById(organizationId) > 0)
        {
            return error("该组织架构包含未停用的子组织架构！");
        }
        organization.setUpdateBy(getUsername());
        return toAjax(organizationService.updateOrganization(organization));
    }

    /**
     * 删除组织架构
     */
    @PreAuthorize("@ss.hasPermi('system:organization:remove')")
    @Log(title = "组织架构管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{organizationId}")
    public AjaxResult remove(@PathVariable Long organizationId)
    {
        if (organizationService.hasChildByOrganizationId(organizationId))
        {
            return warn("存在下级组织架构,不允许删除");
        }
        if (organizationService.checkOrganizationExistUser(organizationId))
        {
            return warn("组织架构存在用户,不允许删除");
        }
        organizationService.checkOrganizationDataScope(organizationId);
        return toAjax(organizationService.deleteOrganizationById(organizationId));
    }
}
