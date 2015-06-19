package com.chinacreator.asp.comp.sys.basic.role.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 角色服务接口
 * 
 * @author 彭盛
 * 
 */
public interface RoleService extends
		com.chinacreator.asp.comp.sys.core.role.service.RoleService {

	/**
	 * 创建匿名角色
	 * 
	 * @param roleDTO
	 *            角色业务对象
	 */
	public void createAnonymousRole(RoleDTO roleDTO);

	/**
	 * 分页查询所有角色
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<RoleDTO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 分页查询角色
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<RoleDTO> queryByRole(RoleDTO roleDTO, Pageable pageable,
			Sortable sortable);

	/**
	 * 分页查询拥有特定角色的所有用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<UserDTO> queryUsers(String roleId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询拥有特定角色的指定机构下用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryUsers(String roleId, String orgId);

	/**
	 * 分页查询拥有特定角色的指定机构下用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryUsers(String roleId, String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 分页查询角色所拥有的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<PrivilegeDTO> queryPrivileges(String roleId, Pageable pageable,
			Sortable sortable);

	/**
	 * 授予角色给机构下用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void assignToUser(String roleId, String userId, String orgId);

	/**
	 * 批量授予角色给机构下用户
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void assignToUsers(String[] roleIds, String[] userIds, String orgId);

	/**
	 * 设置角色给机构下用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setToUser(String roleId, String[] userIds, String orgId);

	/**
	 * 批量设置角色给机构下用户
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setToUsers(String[] roleIds, String[] userIds, String orgId);

	/**
	 * 添加角色给机构
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 */
	public void assignToOrg(String roleId, String orgId);

	/**
	 * 批量添加角色给机构
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void assignToOrgs(String[] roleIds, String[] orgIds);

	/**
	 * 设置角色给机构
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgIds
	 *            机构ID数组
	 */
	public void setToOrgs(String roleId, String[] orgIds);

	/**
	 * 批量设置角色给机构
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void setRolesToOrgs(String[] roleIds, String[] orgIds);

	/**
	 * 批量回收指定角色与其授予的所有机构关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeFromAllOrgs(String... roleIds);

	/**
	 * 回收指定角色与其授予的指定机构关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 */
	public void revokeFromOrg(String roleId, String orgId);

	/**
	 * 批量回收指定角色与其授予的指定机构关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void revokeFromOrgs(String[] roleIds, String[] orgIds);

	/**
	 * 回收指定角色与其授予的指定机构下用户关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void revokeFromUser(String roleId, String userId, String orgId);

	/**
	 * 批量回收指定角色与其授予的指定机构下用户关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void revokeFromUsers(String[] roleIds, String[] userIds, String orgId);

	/**
	 * 判断角色是否已分配给机构下用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean isAssignedToUser(String roleId, String userId, String orgId);

	/**
	 * 判断角色是否已分配给机构
	 * 
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean isAssignedToOrg(String roleId, String orgId);

	/**
	 * 判断指定角色是否用户的可管理角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @return true:是，false:否
	 */
	public boolean isMgtPermitted(String roleId, String userId);

	/**
	 * 获取超级管理员角色ID
	 * 
	 * @return 超级管理员角色ID
	 */
	public String getAdministratorRoleId();

	/**
	 * 获取超级管理员角色名称
	 * 
	 * @return 超级管理员角色名称
	 */
	public String getAdministratorRoleName();

	/**
	 * 获取每个用户都缺省拥有的角色ID
	 * 
	 * @return 每个用户都缺省拥有的角色ID
	 */
	public String getRoleofeveryoneRoleId();

	/**
	 * 获取每个用户都缺省拥有的角色名称
	 * 
	 * @return 每个用户都缺省拥有的角色名称
	 */
	public String getRoleofeveryoneRoleName();

	/**
	 * 获取机构管理员角色ID
	 * 
	 * @return 机构管理员角色ID
	 */
	public String getOrgManagerRoleId();

	/**
	 * 获取机构管理员角色名称
	 * 
	 * @return 机构管理员角色名称
	 */
	public String getOrgManagerRoleName();
}
