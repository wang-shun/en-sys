package com.chinacreator.asp.comp.sys.core.privilege.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 权限服务接口
 * 
 * @author 彭盛
 * 
 */
public interface PrivilegeService {

	/**
	 * 新增权限
	 * 
	 * @param privilegeDTO
	 *            权限数据传输对象
	 */
	public void create(PrivilegeDTO privilegeDTO);

	/**
	 * 修改权限
	 * 
	 * @param privilegeDTO
	 *            权限数据传输对象
	 */
	public void update(PrivilegeDTO privilegeDTO);

	/**
	 * 删除权限
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void deleteByPKs(String... privilegeIds);

	/**
	 * 查询所有权限
	 * 
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryAll();

	/**
	 * 查询权限
	 * 
	 * @param privilegeDTO
	 *            权限数据传输对象
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryByPrivilege(PrivilegeDTO privilegeDTO);

	/**
	 * 查询权限
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 权限数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public PrivilegeDTO queryByPK(String privilegeId);

	/**
	 * 查询拥有权限的用户
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryUsers(String privilegeId);

	/**
	 * 查询拥有权限的用户
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryUsers(String privilegeId, int scopeType,
			String scopeId);

	/**
	 * 查询拥有权限的角色
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRoles(String privilegeId);

	/**
	 * 查询拥有权限的用户组
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 用户组数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<GroupDTO> queryGroups(String privilegeId);

	/**
	 * 添加权限给角色
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param roleId
	 *            角色ID
	 */
	public void assignToRole(String privilegeId, String roleId);

	/**
	 * 批量添加权限给角色
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 * @param roleIds
	 *            角色ID数组
	 */
	public void assignToRoles(String[] privilegeIds, String[] roleIds);

	/**
	 * 设置权限给角色
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param roleIds
	 *            角色ID数组
	 */
	public void setToRole(String privilegeId, String[] roleIds);

	/**
	 * 批量设置权限给角色
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 * @param roleIds
	 *            角色ID数组
	 */
	public void setToRoles(String[] privilegeIds, String[] roleIds);

	/**
	 * 批量回收所有角色的特定权限
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void revokeFromAllRoles(String... privilegeIds);

	/**
	 * 回收角色的权限
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param roleId
	 *            角色ID
	 */
	public void revokeFromRole(String privilegeId, String roleId);

	/**
	 * 批量回收角色的权限
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeFromRoles(String[] privilegeIds, String[] roleIds);

	/**
	 * 判断权限是否分配给指定角色
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @param roleId
	 *            角色ID
	 * @return true:有，false:无
	 */
	public boolean isAssignedToRole(String privilegeId, String roleId);

	/**
	 * 判断权限编码是否存在
	 * 
	 * @param privilegeCode
	 *            权限编码
	 * @return true：存在，false：不存在
	 */
	public boolean existsByPrivilegeCode(String privilegeCode);

	/**
	 * 判断权限编码是否存在(忽略指定权限ID，编辑时用)
	 * 
	 * @param privilegeCode
	 *            权限编码
	 * @param privilegeId
	 *            权限ID
	 * @return true：存在，false：不存在
	 */
	public boolean existsByPrivilegeCodeIgnorePrivilegeId(String privilegeCode,
			String privilegeId);
}
