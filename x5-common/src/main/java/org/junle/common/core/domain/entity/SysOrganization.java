package org.junle.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junle.common.core.domain.BaseEntity;

/**
 * 组织架构表 sys_organization
 * 
 * @author elnujuw
 */
public class SysOrganization extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组织架构ID */
    private Long organizationId;

    /** 父组织架构ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 组织架构名称 */
    private String organizationName;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 组织架构状态:0正常,1停用 */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父组织架构名称 */
    private String parentName;
    
    /** 子组织架构 */
    private List<SysOrganization> children = new ArrayList<SysOrganization>();

    public Long getOrganizationId()
    {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId)
    {
        this.organizationId = organizationId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    @NotBlank(message = "组织架构名称不能为空")
    @Size(min = 0, max = 30, message = "组织架构名称长度不能超过30个字符")
    public String getOrganizationName()
    {
        return organizationName;
    }

    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }

    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<SysOrganization> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysOrganization> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("organizationId", getOrganizationId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("organizationName", getOrganizationName())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
