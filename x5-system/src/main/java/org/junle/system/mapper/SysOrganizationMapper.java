package org.junle.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.junle.common.core.domain.entity.SysOrganization;

/**
 * 组织架构管理 数据层
 * 
 * @author elnujuw
 */
public interface SysOrganizationMapper
{
    /**
     * 查询组织架构管理数据
     * 
     * @param organization 组织架构信息
     * @return 组织架构信息集合
     */
    public List<SysOrganization> selectOrganizationList(SysOrganization organization);

    /**
     * 根据角色ID查询组织架构树信息
     * 
     * @param roleId 角色ID
     * @param organizationCheckStrictly 组织架构树选择项是否关联显示
     * @return 选中组织架构列表
     */
    public List<Long> selectOrganizationListByRoleId(@Param("roleId") Long roleId, @Param("organizationCheckStrictly") boolean organizationCheckStrictly);

    /**
     * 根据组织架构ID查询信息
     * 
     * @param organizationId 组织架构ID
     * @return 组织架构信息
     */
    public SysOrganization selectOrganizationById(Long organizationId);

    /**
     * 根据ID查询所有子组织架构
     * 
     * @param organizationId 组织架构ID
     * @return 组织架构列表
     */
    public List<SysOrganization> selectChildrenOrganizationById(Long organizationId);

    /**
     * 根据ID查询所有子组织架构（正常状态）
     * 
     * @param organizationId 组织架构ID
     * @return 子组织架构数
     */
    public int selectNormalChildrenOrganizationById(Long organizationId);

    /**
     * 是否存在子节点
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public int hasChildByOrganizationId(Long organizationId);

    /**
     * 查询组织架构是否存在用户
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public int checkOrganizationExistUser(Long organizationId);

    /**
     * 校验组织架构名称是否唯一
     * 
     * @param organizationName 组织架构名称
     * @param parentId 父组织架构ID
     * @return 结果
     */
    public SysOrganization checkOrganizationNameUnique(@Param("organizationName") String organizationName, @Param("parentId") Long parentId);

    /**
     * 新增组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    public int insertOrganization(SysOrganization organization);

    /**
     * 修改组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    public int updateOrganization(SysOrganization organization);

    /**
     * 修改所在组织架构正常状态
     * 
     * @param organizationIds 组织架构ID组
     */
    public void updateOrganizationStatusNormal(Long[] organizationIds);

    /**
     * 修改子元素关系
     * 
     * @param organizations 子元素
     * @return 结果
     */
    public int updateOrganizationChildren(@Param("organizations") List<SysOrganization> organizations);

    /**
     * 删除组织架构管理信息
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    public int deleteOrganizationById(Long organizationId);
}
