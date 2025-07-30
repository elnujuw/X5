package org.junle.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junle.common.annotation.DataScope;
import org.junle.common.constant.UserConstants;
import org.junle.common.core.domain.TreeSelect;
import org.junle.common.core.domain.entity.SysOrganization;
import org.junle.common.core.domain.entity.SysRole;
import org.junle.common.core.domain.entity.SysUser;
import org.junle.common.core.text.Convert;
import org.junle.common.exception.ServiceException;
import org.junle.common.utils.SecurityUtils;
import org.junle.common.utils.StringUtils;
import org.junle.common.utils.spring.SpringUtils;
import org.junle.system.mapper.SysOrganizationMapper;
import org.junle.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.junle.system.service.ISysOrganizationService;

/**
 * 组织架构管理 服务实现
 * 
 * @author elnujuw
 */
@Service
public class SysOrganizationServiceImpl implements ISysOrganizationService
{
    @Autowired
    private SysOrganizationMapper organizationMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询组织架构管理数据
     * 
     * @param organization 组织架构信息
     * @return 组织架构信息集合
     */
    @Override
    @DataScope(organizationAlias = "d")
    public List<SysOrganization> selectOrganizationList(SysOrganization organization)
    {
        return organizationMapper.selectOrganizationList(organization);
    }

    /**
     * 查询组织架构树结构信息
     * 
     * @param organization 组织架构信息
     * @return 组织架构树信息集合
     */
    @Override
    public List<TreeSelect> selectOrganizationTreeList(SysOrganization organization)
    {
        List<SysOrganization> organizations = SpringUtils.getAopProxy(this).selectOrganizationList(organization);
        return buildOrganizationTreeSelect(organizations);
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param organizations 组织架构列表
     * @return 树结构列表
     */
    @Override
    public List<SysOrganization> buildOrganizationTree(List<SysOrganization> organizations)
    {
        List<SysOrganization> returnList = new ArrayList<SysOrganization>();
        List<Long> tempList = organizations.stream().map(SysOrganization::getOrganizationId).collect(Collectors.toList());
        for (SysOrganization organization : organizations)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(organization.getParentId()))
            {
                recursionFn(organizations, organization);
                returnList.add(organization);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = organizations;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param organizations 组织架构列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildOrganizationTreeSelect(List<SysOrganization> organizations)
    {
        List<SysOrganization> organizationTrees = buildOrganizationTree(organizations);
        return organizationTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询组织架构树信息
     * 
     * @param roleId 角色ID
     * @return 选中组织架构列表
     */
    @Override
    public List<Long> selectOrganizationListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return organizationMapper.selectOrganizationListByRoleId(roleId, role.isOrganizationCheckStrictly());
    }

    /**
     * 根据组织架构ID查询信息
     * 
     * @param organizationId 组织架构ID
     * @return 组织架构信息
     */
    @Override
    public SysOrganization selectOrganizationById(Long organizationId)
    {
        return organizationMapper.selectOrganizationById(organizationId);
    }

    /**
     * 根据ID查询所有子组织架构（正常状态）
     * 
     * @param organizationId 组织架构ID
     * @return 子组织架构数
     */
    @Override
    public int selectNormalChildrenOrganizationById(Long organizationId)
    {
        return organizationMapper.selectNormalChildrenOrganizationById(organizationId);
    }

    /**
     * 是否存在子节点
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOrganizationId(Long organizationId)
    {
        int result = organizationMapper.hasChildByOrganizationId(organizationId);
        return result > 0;
    }

    /**
     * 查询组织架构是否存在用户
     * 
     * @param organizationId 组织架构ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkOrganizationExistUser(Long organizationId)
    {
        int result = organizationMapper.checkOrganizationExistUser(organizationId);
        return result > 0;
    }

    /**
     * 校验组织架构名称是否唯一
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    @Override
    public boolean checkOrganizationNameUnique(SysOrganization organization)
    {
        Long organizationId = StringUtils.isNull(organization.getOrganizationId()) ? -1L : organization.getOrganizationId();
        SysOrganization info = organizationMapper.checkOrganizationNameUnique(organization.getOrganizationName(), organization.getParentId());
        if (StringUtils.isNotNull(info) && info.getOrganizationId().longValue() != organizationId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验组织架构是否有数据权限
     * 
     * @param organizationId 组织架构id
     */
    @Override
    public void checkOrganizationDataScope(Long organizationId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()) && StringUtils.isNotNull(organizationId))
        {
            SysOrganization organization = new SysOrganization();
            organization.setOrganizationId(organizationId);
            List<SysOrganization> organizations = SpringUtils.getAopProxy(this).selectOrganizationList(organization);
            if (StringUtils.isEmpty(organizations))
            {
                throw new ServiceException("没有权限访问组织架构数据！");
            }
        }
    }

    /**
     * 新增保存组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    @Override
    public int insertOrganization(SysOrganization organization)
    {
        SysOrganization info = organizationMapper.selectOrganizationById(organization.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.ORG_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("组织架构停用，不允许新增");
        }
        organization.setAncestors(info.getAncestors() + "," + organization.getParentId());
        return organizationMapper.insertOrganization(organization);
    }

    /**
     * 修改保存组织架构信息
     * 
     * @param organization 组织架构信息
     * @return 结果
     */
    @Override
    public int updateOrganization(SysOrganization organization)
    {
        SysOrganization newParentOrganization = organizationMapper.selectOrganizationById(organization.getParentId());
        SysOrganization oldOrganization = organizationMapper.selectOrganizationById(organization.getOrganizationId());
        if (StringUtils.isNotNull(newParentOrganization) && StringUtils.isNotNull(oldOrganization))
        {
            String newAncestors = newParentOrganization.getAncestors() + "," + newParentOrganization.getOrganizationId();
            String oldAncestors = oldOrganization.getAncestors();
            organization.setAncestors(newAncestors);
            updateOrganizationChildren(organization.getOrganizationId(), newAncestors, oldAncestors);
        }
        int result = organizationMapper.updateOrganization(organization);
        if (UserConstants.ORG_NORMAL.equals(organization.getStatus()) && StringUtils.isNotEmpty(organization.getAncestors())
                && !StringUtils.equals("0", organization.getAncestors()))
        {
            // 如果该组织架构是启用状态，则启用该组织架构的所有上级组织架构
            updateParentOrganizationStatusNormal(organization);
        }
        return result;
    }

    /**
     * 修改该组织架构的父级组织架构状态
     * 
     * @param organization 当前组织架构
     */
    private void updateParentOrganizationStatusNormal(SysOrganization organization)
    {
        String ancestors = organization.getAncestors();
        Long[] organizationIds = Convert.toLongArray(ancestors);
        organizationMapper.updateOrganizationStatusNormal(organizationIds);
    }

    /**
     * 修改子元素关系
     * 
     * @param organizationId 被修改的组织架构ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateOrganizationChildren(Long organizationId, String newAncestors, String oldAncestors)
    {
        List<SysOrganization> children = organizationMapper.selectChildrenOrganizationById(organizationId);
        for (SysOrganization child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            organizationMapper.updateOrganizationChildren(children);
        }
    }

    /**
     * 删除组织架构管理信息
     * 
     * @param organizationId 组织架构ID
     * @return 结果
     */
    @Override
    public int deleteOrganizationById(Long organizationId)
    {
        return organizationMapper.deleteOrganizationById(organizationId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysOrganization> list, SysOrganization t)
    {
        // 得到子节点列表
        List<SysOrganization> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysOrganization tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysOrganization> getChildList(List<SysOrganization> list, SysOrganization t)
    {
        List<SysOrganization> tlist = new ArrayList<SysOrganization>();
        Iterator<SysOrganization> it = list.iterator();
        while (it.hasNext())
        {
            SysOrganization n = (SysOrganization) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getOrganizationId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysOrganization> list, SysOrganization t)
    {
        return getChildList(list, t).size() > 0;
    }
}
