package org.junle.system.mapper;

import java.util.List;
import org.junle.system.domain.SysRoleOrganization;

/**
 * 角色与组织架构关联表 数据层
 * 
 * @author elnujuw
 */
public interface SysRoleOrganizationMapper
{
    /**
     * 通过角色ID删除角色和组织架构关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleOrganizationByRoleId(Long roleId);

    /**
     * 批量删除角色组织架构关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleOrganization(Long[] ids);

    /**
     * 查询组织架构使用数量
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public int selectCountRoleOrganizationByOrganizationId(Long organizationId);

    /**
     * 批量新增角色组织架构信息
     * 
     * @param roleOrganizationList 角色组织架构列表
     * @return 结果
     */
    public int batchRoleOrganization(List<SysRoleOrganization> roleOrganizationList);
}
