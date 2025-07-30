package org.junle.system.service;

import java.util.List;
import org.junle.common.core.domain.TreeSelect;
import org.junle.common.core.domain.entity.SysOrganization;

/**
 * 组织架构管理 服务层
 * 
 * @author elnujuw
 */
public interface ISysOrganizationService
{
    /**
     * 查询组织架构管理数据
     * 
     * @param organization 组织架构信息
     * @return 组织架构信息集合
     */
    public List<SysOrganization> selectOrganizationList(SysOrganization organization);

    /**
     * 查询组织架构树结构信息
     * 
     * @param organization 组织架构信息
     * @return 组织架构树信息集合
     */
    public List<TreeSelect> selectOrganizationTreeList(SysOrganization organization);

    /**
     * 构建前端所需要树结构
     * 
     * @param organizations 组织架构列表
     * @return 树结构列表
     */
    public List<SysOrganization> buildOrganizationTree(List<SysOrganization> organizations);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param organizations 组织架构列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildOrganizationTreeSelect(List<SysOrganization> organizations);

    /**
     * 根据角色ID查询组织架构树信息
     * 
     * @param roleId 角色ID
     * @return 选中组织架构列表
     */
    public List<Long> selectOrganizationListByRoleId(Long roleId);

    /**
     * 根据组织架构ID查询信息
     * 
     * @param organizationId 组织架构ID
     * @return 组织架构信息
     */
    public SysOrganization selectOrganizationById(Long organizationId);

    /**
     * 根据ID查询所有子组织架构（正常状态）
     * 
     * @param organizationId 组织架构ID
     * @return 子组织架构数
     */
    public int selectNormalChildrenOrganizationById(Long organizationId);

    /**
     * 是否存在组织架构子节点
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public boolean hasChildByOrganizationId(Long organizationId);

    /**
     * 查询组织架构是否存在用户
     * 
     * @param organizationId 组织架构ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkOrganizationExistUser(Long organizationId);

    /**
     * 校验组织架构名称是否唯一
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    public boolean checkOrganizationNameUnique(SysOrganization organization);

    /**
     * 校验组织架构是否有数据权限
     * 
     * @param organizationId 组织架构id
     */
    public void checkOrganizationDataScope(Long organizationId);

    /**
     * 新增保存组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    public int insertOrganization(SysOrganization organization);

    /**
     * 修改保存组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    public int updateOrganization(SysOrganization organization);

    /**
     * 删除组织架构管理信息
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public int deleteOrganizationById(Long organizationId);
}
