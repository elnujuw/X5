package org.junle.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 角色和组织架构关联 sys_role_organization
 * 
 * @author elnujuw
 */
public class SysRoleOrganization
{
    /** 角色ID */
    private Long roleId;
    
    /** 组织架构ID */
    private Long organizationId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId)
    {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("organizationId", getOrganizationId())
            .toString();
    }
}
