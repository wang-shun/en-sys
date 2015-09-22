package com.chinacreator.asp.comp.sys.core.role.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 角色服务接口
 * 
 * @author 彭盛
 * 
 */
public interface RoleService {

	/**
	 * 新增角色
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 */
	public void create(RoleDTO roleDTO);

	/**
	 * 修改角色
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 */
	public void update(RoleDTO roleDTO);

	/**
	 * 删除角色
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void deleteByPKs(String... roleIds);

	/**
	 * 删除角色
	 * 
	 * @param roleName
	 *            角色名
	 */
	public void deleteByRoleName(String roleName);

	/**
	 * 查询所有角色
	 * 
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryAll();

	/**
	 * 查询角色
	 * 
	 * @param roleDTO
	 *            角色数据传输对象
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryByRole(RoleDTO roleDTO);

	/**
	 * 查询角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public RoleDTO queryByPK(String roleId);

	/**
	 * 查询角色
	 * 
	 * @param roleName
	 *            角色名
	 * @return 角色数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public RoleDTO queryByRoleName(String roleName);

	/**
	 * 查询拥有特定角色的所有用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryUsers(String roleId);

	/**
	 * 查询拥有特定角色的所有用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryUsers(String roleId, int scopeType, String scopeId);

	/**
	 * 查询拥有特定角色的所有用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 用户组数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<GroupDTO> queryGroups(String roleId);

	/**
	 * 查询角色所拥有的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryPrivileges(String roleId);

	/**
	 * 授予角色给用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void assignToUser(String roleId, String userId, int scopeType, String scopeId);

	/**
	 * 批量授予角色给用户
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void assignToUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId);

	/**
	 * 设置角色给用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userIds
	 *            用户ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setToUser(String roleId, String[] userIds, int scopeType, String scopeId);

	/**
	 * 批量设置角色给用户
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setToUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId);

	/**
	 * 批量回收指定角色与其授予的所有用户关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeFromAllUsers(String... roleIds);

	/**
	 * 回收指定角色与其授予的指定用户关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void revokeFromUser(String roleId, String userId, int scopeType, String scopeId);

	/**
	 * 批量回收指定角色与其授予的指定用户关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param userIds
	 *            用户ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void revokeFromUsers(String[] roleIds, String[] userIds, int scopeType, String scopeId);

	/**
	 * 添加角色给用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupId
	 *            用户组ID
	 */
	public void assingToGroup(String roleId, String groupId);

	/**
	 * 批量添加角色给用户组
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param groupIds
	 *            用户组ID数组
	 */
	public void assingToGroups(String[] roleIds, String[] groupIds);

	/**
	 * 设置角色给用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupIds
	 *            用户组ID数组
	 */
	public void setToGroups(String roleId, String[] groupIds);

	/**
	 * 批量设置角色给用户组
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param groupIds
	 *            用户组ID数组
	 */
	public void setRolesToGroups(String[] roleIds, String[] groupIds);

	/**
	 * 批量回收指定角色与其授予的所有用户组关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeFromAllGroups(String... roleIds);

	/**
	 * 回收指定角色与其授予的指定用户组关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupId
	 *            用户组ID
	 */
	public void revokeFromGroup(String roleId, String groupId);

	/**
	 * 批量回收指定角色与其授予的指定用户组关系
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param groupIds
	 *            用户组ID数组
	 */
	public void revokeFromGroups(String[] roleIds, String[] groupIds);

	/**
	 * 添加角色的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param privilegeId
	 *            权限ID
	 */
	public void assignPrivilege(String roleId, String privilegeId);

	/**
	 * 批量添加角色的权限
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void assignPrivileges(String[] roleIds, String[] privilegeIds);

	/**
	 * 设置角色的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void setPrivileges(String roleId, String[] privilegeIds);

	/**
	 * 批量设置角色的权限
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void setPrivilegesToRoles(String[] roleIds, String[] privilegeIds);

	/**
	 * 回收角色的所有权限
	 * 
	 * @param roleIds
	 *            角色ID数组
	 */
	public void revokeAllPrivileges(String... roleIds);

	/**
	 * 回收角色的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param privilegeId
	 *            权限ID
	 */
	public void revokePrivilege(String roleId, String privilegeId);

	/**
	 * 批量回收角色的权限
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public void revokePrivileges(String[] roleIds, String[] privilegeIds);

	/**
	 * 判断角色有效状态
	 * 
	 * @param roleId
	 *            角色ID
	 * @return true:有效，false:无效
	 */
	public boolean isEnabledByPK(String roleId);

	/**
	 * 判断角色有效状态
	 * 
	 * @param roleName
	 *            角色名
	 * @return true:有效，false:无效
	 */
	public boolean isEnabledByRoleName(String roleName);

	/**
	 * 判断角色名称是否存在
	 * 
	 * @param roleName
	 *            角色名称
	 * @return true:存在，false:不存在
	 */
	public boolean existsByRoleName(String roleName);

	/**
	 * 判断角色名称是否存在(忽略指定角色ID名称，编辑时用)
	 * 
	 * @param roleName
	 *            角色名称
	 * @param roleId
	 *            角色ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByRoleNameIgnoreRoleID(String roleName, String roleId);

	/**
	 * 判断角色是否已分配给用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @return true:有，false:无
	 */
	public boolean isAssingedToUser(String roleId, String userId);

	/**
	 * 判断角色是否已分配给用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有，false:无
	 */
	public boolean isAssingedToUser(String roleId, String userId, int scopeType, String scopeId);

	/**
	 * 判断角色是否分配给用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupId
	 *            用户组ID
	 * @return true:有，false:无
	 */
	public boolean isAssignedToGroup(String roleId, String groupId);

	/**
	 * 判断角色是否拥有指定权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param privilegeId
	 *            权限ID
	 * @return true:有，false:无
	 */
	public boolean hasPrivilege(String roleId, String privilegeId);

}
