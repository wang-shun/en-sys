package com.chinacreator.asp.comp.sys.core.user.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 用户服务接口
 * 
 * @author 彭盛
 * 
 */
public interface UserService {

	/**
	 * 新增用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * 
	 */
	public void create(UserDTO userDto);

	/**
	 * 新增用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void create(UserDTO userDto, int scopeType, String scopeId);

	/**
	 * 修改用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 */
	public void update(UserDTO userDto);

	/**
	 * 设置用户排序
	 * 
	 * @param userDTOList
	 *            用户数据传输对象列表<br>
	 *            用户数据传输对象必须包含用户ID和用户排序号
	 */
	public void setOrder(List<UserDTO> userDTOList);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            用户密码
	 */
	public void updatePassword(String userId, String password);

	/**
	 * 修改用户密码
	 * 
	 * @param userName
	 *            用户账号
	 * @param oldPassword
	 *            用户原密码
	 * @param newPassword
	 *            用户新密码
	 */
	public void updatePassword(String userName, String oldPassword,
			String newPassword);

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void deleteByPKs(String... userIds);

	/**
	 * 删除用户
	 * 
	 * @param userName
	 *            用户账号
	 */
	public void deleteByUserName(String userName);

	/**
	 * 删除用户实例
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void deleteUserInstanceByScope(String userId, int scopeType,
			String scopeId);

	/**
	 * 删除用户实例
	 * 
	 * @param userInstanceIds
	 *            用户实例ID数组
	 */
	public void deleteUserInstancesByUserInstanceIds(String... userInstanceIds);

	/**
	 * 查询所有用户
	 * 
	 * @return 所有用户<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryAll();

	/**
	 * 查询用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByUser(UserDTO userDto);

	/**
	 * 查询用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public UserDTO queryByPK(String userId);

	/**
	 * 查询用户
	 * 
	 * @param userName
	 *            用户账号
	 * @return 用户数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public UserDTO queryByUserName(String userName);

	/**
	 * 查询用户
	 * 
	 * @param userRealname
	 *            用户实名
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByUserRealName(String userRealname);

	/**
	 * 查询指定用户活动范围下的所有用户
	 * 
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByScope(int scopeType, String scopeId);

	/**
	 * 查询指定用户所拥有的直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryDirectRoles(String userId);

	/**
	 * 查询指定用户所拥有的直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryDirectRoles(String userId, int scopeType,
			String scopeId);

	/**
	 * 查询指定用户所拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRoles(String userId);

	/**
	 * 查询指定用户所拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRoles(String userId, int scopeType, String scopeId);

	/**
	 * 查询用户所属用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户组数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<GroupDTO> queryGroups(String userId);

	/**
	 * 查询用户所属用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户组数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<GroupDTO> queryGroups(String userId, int scopeType,
			String scopeId);

	/**
	 * 查询用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryPrivileges(String userId);

	/**
	 * 查询用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryPrivileges(String userId, int scopeType,
			String scopeId);

	/**
	 * 添加用户拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void assignRole(String userId, String roleId, int scopeType,
			String scopeId);

	/**
	 * 批量添加用户拥有的角色
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void assignRoles(String[] userIds, String[] roleIds, int scopeType,
			String scopeId);

	/**
	 * 设置用户拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleIds
	 *            角色ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setRoles(String userId, String[] roleIds, int scopeType,
			String scopeId);

	/**
	 * 批量设置用户拥有的角色
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setRoles(String[] userIds, String[] roleIds, int scopeType,
			String scopeId);

	/**
	 * 批量回收用户拥有的所有角色关系
	 * 
	 * @param userIds
	 *            用户IDs
	 */
	public void revokeAllRoles(String... userIds);

	/**
	 * 批量回收用户拥有的所有角色关系
	 * 
	 * @param userIds
	 *            用户IDs
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void revokeAllRoles(String[] userIds, int scopeType, String scopeId);

	/**
	 * 回收用户拥有的指定角色关系
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void revokeRole(String userId, String roleId, int scopeType,
			String scopeId);

	/**
	 * 批量回收用户拥有的指定角色关系
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void revokeRoles(String[] userIds, String[] roleIds, int scopeType,
			String scopeId);

	/**
	 * 添加用户到用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void addToGroup(String userId, String groupId, int scopeType,
			String scopeId);

	/**
	 * 批量添加用户到用户组
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param groupIds
	 *            用户组ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void addToGroups(String[] userIds, String[] groupIds, int scopeType,
			String scopeId);

	/**
	 * 设置用户所属用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setGroup(String userId, String groupId, int scopeType,
			String scopeId);

	/**
	 * 批量设置用户所属用户组
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param groupIds
	 *            用户组ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void setGroups(String[] userIds, String[] groupIds, int scopeType,
			String scopeId);

	/**
	 * 将用户从其所属所有用户组中删除
	 * 
	 * @param userIds
	 *            用户IDs
	 */
	public void removeFromAllGroups(String... userIds);

	/**
	 * 将用户从指定用户组中删除
	 * 
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void removeFromGroup(String userId, String groupId, int scopeType,
			String scopeId);

	/**
	 * 将用户从指定用户组列表中删除
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param groupIds
	 *            用户组ID数组
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 */
	public void removeFromGroups(String[] userIds, String[] groupIds,
			int scopeType, String scopeId);

	/**
	 * 设置用户在指定活动范围内的有效状态
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @param enabled
	 *            是否有效（true:有效，false:无效）
	 */
	public void setEnabledByScope(String userId, int scopeType, String scopeId,
			boolean enabled);

	/**
	 * 判断用户是否拥有直接授予其的指定角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @return true:有，false:无
	 */
	public boolean hasDirectRole(String userId, String roleId);

	/**
	 * 判断用户是否拥有直接授予其的指定角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有，false:无
	 */
	public boolean hasDirectRole(String userId, String roleId, int scopeType,
			String scopeId);

	/**
	 * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @return true:有，false:无
	 */
	public boolean hasRole(String userId, String roleId);

	/**
	 * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有，false:无
	 */
	public boolean hasRole(String userId, String roleId, int scopeType,
			String scopeId);

	/**
	 * 判断用户是否属于指定用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 * @return true:有，false:无
	 */
	public boolean belongsToGroup(String userId, String groupId);

	/**
	 * 判断用户是否属于指定用户组
	 * 
	 * @param userId
	 *            用户ID
	 * @param groupId
	 *            用户组ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有，false:无
	 */
	public boolean belongsToGroup(String userId, String groupId, int scopeType,
			String scopeId);

	/**
	 * 判断用户是否拥有指定权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @return true:有，false:无
	 */
	public boolean hasPrivilege(String userId, String privilegeId);

	/**
	 * 判断用户是否拥有指定权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有，false:无
	 */
	public boolean hasPrivilege(String userId, String privilegeId,
			int scopeType, String scopeId);

	/**
	 * 判断用户在指定活动范围内是否有效
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return true:有效，false:无效
	 */
	public boolean isEnabledByScope(String userId, int scopeType, String scopeId);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByPK(String userId);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userName
	 *            用户帐号
	 * @return true:存在，false:不存在
	 */
	public boolean existsByUserName(String userName);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userRealname
	 *            用户实名
	 * @return true:存在，false:不存在
	 */
	public boolean existsByUserRealname(String userRealname);

}
