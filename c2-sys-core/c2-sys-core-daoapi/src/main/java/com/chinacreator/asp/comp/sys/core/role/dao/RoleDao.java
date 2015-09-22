package com.chinacreator.asp.comp.sys.core.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 角色数据访问接口
 * 
 * @author 蒋海杰
 * 
 */
@Repository
public interface RoleDao {

	/**
	 * 新增角色
	 * 
	 * @param roleEO
	 *            角色数据传输对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int create(RoleEO roleEO);

	/**
	 * 修改角色
	 * 
	 * @param roleEO
	 *            角色数据传输对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int update(RoleEO roleEO);

	/**
	 * 批量删除角色
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByPKs(String... roleIds);

	/**
	 * 批量删除角色
	 * 
	 * @param roleName
	 *            角色名数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByRoleNames(String... roleNames);

	/**
	 * 查询所有角色
	 * 
	 * @return 角色数据传输对象列表
	 */
	public List<RoleEO> queryAll();

	/**
	 * 查询所有角色（带分页）
	 * 
	 * @return 分页后的角色数据传输对象
	 */
	public Page<RoleEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询角色
	 * 
	 * @param roleEO
	 *            角色数据传输对象
	 * @return 角色数据传输对象列表
	 */
	public List<RoleEO> queryByRole(@Param("roleEO") RoleEO roleEO);

	/**
	 * 查询角色（带分页）
	 * 
	 * @param roleEO
	 *            角色数据传输对象
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的角色数据传输对象
	 */
	public Page<RoleEO> queryByRole(@Param("roleEO") RoleEO roleEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public RoleEO queryByPK(String roleId);

	/**
	 * 查询角色
	 * 
	 * @param roleName
	 *            角色名
	 * @return 角色数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public RoleEO queryByRoleName(String roleName);

	/**
	 * 查询拥有特定角色的所有用户
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 用户数据传输对象列表
	 */
	public List<UserEO> queryUsers(@Param("roleId") String roleId);

	/**
	 * 查询拥有特定角色的所有用户（带分页）
	 * 
	 * @param roleId
	 *            角色ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的用户数据传输对象
	 */
	public Page<UserEO> queryUsers(@Param("roleId") String roleId,
			Pageable pageable, Sortable sortable);

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
	 * @return 用户数据传输对象列表
	 */
	public List<UserEO> queryUsersByScope(@Param("roleId") String roleId,
			@Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId);

	/**
	 * 查询拥有特定角色的所有用户（带分页）
	 * 
	 * @param roleId
	 *            角色ID
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 用户数据传输对象列表
	 */
	public Page<UserEO> queryUsersByScope(@Param("roleId") String roleId,
			@Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询拥有特定角色的所有用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 用户组数据传输对象列表
	 */
	public List<GroupEO> queryGroups(String roleId);

	/**
	 * 查询角色所拥有的权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 权限数据传输对象列表
	 */
	public List<PrivilegeEO> queryPrivileges(@Param("roleId") String roleId);

	/**
	 * 查询角色所拥有的权限（带分页）
	 * 
	 * @param roleId
	 *            角色ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 权限数据传输对象列表
	 */
	public Page<PrivilegeEO> queryPrivileges(@Param("roleId") String roleId,
			Pageable pageable, Sortable sortable);

	/**
	 * 判断角色有效状态
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 大于0:有效，0:无效
	 */
	public Integer isEnabledByPK(String roleId);

	/**
	 * 判断角色有效状态
	 * 
	 * @param roleName
	 *            角色名
	 * @return 大于0:有效，0:无效
	 */
	public Integer isEnabledByRoleName(String roleName);

	/**
	 * 判断角色是否分配给用户组
	 * 
	 * @param roleId
	 *            角色ID
	 * @param groupId
	 *            用户组ID
	 * @return 大于0:有，0:无
	 */
	public int isAssignedToGroup(@Param("roleId") String roleId,
			@Param("groupId") String groupId);

	/**
	 * 判断角色名称是否存在
	 * 
	 * @param roleName
	 *            角色名称
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByRoleName(String roleName);

	/**
	 * 判断角色名称是否存在(忽略指定角色ID名称，编辑时用)
	 * 
	 * @param roleName
	 *            角色名称
	 * @param roleId
	 *            角色ID
	 * @return 大于0表示存在，否则不存在
	 */
	public int existsByRoleNameIgnoreRoleID(
			@Param("roleName") String roleName, @Param("roleId") String roleId);

	/**
	 * 判断角色是否拥有指定权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param privilegeId
	 *            权限ID
	 * @return 大于0:有，0:无
	 */
	public int hasPrivilege(@Param("roleId") String roleId,
			@Param("privilegeId") String privilegeId);

	/**
	 * 判断角色是否已分配给用户实例
	 * 
	 * @param roleId
	 *            角色ID
	 * @param userInstanceId
	 *            用户实例ID
	 * @return 大于0:有，0:无
	 */
	public int isAssingedToUserInstance(@Param("roleId") String roleId,
			@Param("userInstanceId") String userInstanceId);

	/**
	 * 判断角色角色名是否存在，排除自己，编辑时使用
	 * 
	 * @param roleId
	 *            角色ID
	 * @param roleName
	 *            角色名
	 * @return 角色数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public RoleEO queryByRoleIdAndRoleName(@Param("roleId") String roleId,
			@Param("roleName") String roleName);

	/**
	 * 通过角色类型ID数组查询出角色ID集合
	 * 
	 * @param roleTypeIds
	 *            角色类型ID数组
	 * @return 角色ID集合
	 */
	public List<String> queryRoleIdsByRoleTypeIds(String... roleTypeIds);
}
