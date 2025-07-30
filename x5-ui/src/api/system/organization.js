import request from '@/utils/request'

// 查询组织架构列表
export function listOrganization(query) {
  return request({
    url: '/system/organization/list',
    method: 'get',
    params: query
  })
}

// 查询组织架构列表（排除节点）
export function listOrganizationExcludeChild(organizationId) {
  return request({
    url: '/system/organization/list/exclude/' + organizationId,
    method: 'get'
  })
}

// 查询组织架构详细
export function getOrganization(organizationId) {
  return request({
    url: '/system/organization/' + organizationId,
    method: 'get'
  })
}

// 新增组织架构
export function addOrganization(data) {
  return request({
    url: '/system/organization',
    method: 'post',
    data: data
  })
}

// 修改组织架构
export function updateOrganization(data) {
  return request({
    url: '/system/organization',
    method: 'put',
    data: data
  })
}

// 删除组织架构
export function delOrganization(organizationId) {
  return request({
    url: '/system/organization/' + organizationId,
    method: 'delete'
  })
}