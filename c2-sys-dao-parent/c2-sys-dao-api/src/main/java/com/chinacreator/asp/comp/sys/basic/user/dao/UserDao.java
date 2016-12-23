package com.chinacreator.asp.comp.sys.basic.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.menu.eo.MenuAllInfoEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 
 * 用户信息数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface UserDao {

	/**
	 * 查询用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<UserEO> queryByUser(@Param("userEO") UserEO userEO, Pageable pageable, Sortable sortable);

	/**
	 * 查询用户的直接权限
	 * 
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param userId
	 *            用户ID
	 * @return 直接权限列表
	 */
	public List<PrivilegeEO> queryDirectPrivileges(@Param("roleType") String roleType, @Param("userId") String userId);

	/**
	 * 查询用户的直接权限（带分页）
	 * 
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的直接权限列表
	 */
	public Page<PrivilegeEO> queryDirectPrivileges(@Param("roleType") String roleType, @Param("userId") String userId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询机构用户的直接权限
	 * 
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return 直接权限列表
	 */
	public List<PrivilegeEO> queryDirectOrgUserPrivileges(@Param("roleType") String roleType,
			@Param("orgId") String orgId, @Param("userId") String userId);

	/**
	 * 查询机构用户的直接权限(带分页)
	 * 
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的直接权限列表
	 */
	public Page<PrivilegeEO> queryDirectOrgUserPrivileges(@Param("roleType") String roleType,
			@Param("orgId") String orgId, @Param("userId") String userId, Pageable pageable, Sortable sortable);

	/**
	 * 查询用户的菜单信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryMenus(@Param("userId") String userId);

	/**
	 * 查询机构用户的菜单信息
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return 菜单信息列表
	 */
	public List<MenuAllInfoEO> queryOrgUserMenus(@Param("orgId") String orgId, @Param("userId") String userId);

	/**
	 * 查询用户的所有机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构ID集合
	 */
	public List<String> queryOrgIdsByUserId(@Param("userId") String userId);

	/**
	 * 查询机构下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByOrg(@Param("userEO") UserEO userEO, @Param("orgId") String orgId);

	/**
	 * 查询机构下用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserEO> queryByOrg(@Param("userEO") UserEO userEO, @Param("orgId") String orgId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询拥有角色的用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param roleId
	 *            角色ID
	 * @param everyoneRoleId
	 *            普通用户角色ID
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByRole(@Param("userEO") UserEO userEO, @Param("roleId") String roleId,
			@Param("everyoneRoleId") String everyoneRoleId);

	/**
	 * 分页查询拥有角色的用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param roleId
	 *            角色ID
	 * @param everyoneRoleId
	 *            普通用户角色ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserEO> queryByRole(@Param("userEO") UserEO userEO, @Param("roleId") String roleId,
			@Param("everyoneRoleId") String everyoneRoleId, Pageable pageable, Sortable sortable);

	/**
	 * 查询机构下拥有角色的用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param roleId
	 *            角色ID
	 * @param everyoneRoleId
	 *            普通用户角色ID
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByOrgRole(@Param("userEO") UserEO userEO, @Param("orgId") String orgId,
			@Param("roleId") String roleId, @Param("everyoneRoleId") String everyoneRoleId);

	/**
	 * 分页查询机构下拥有角色的用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @param orgId
	 *            机构ID
	 * @param roleId
	 *            角色ID
	 * @param everyoneRoleId
	 *            普通用户角色ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserEO> queryByOrgRole(@Param("userEO") UserEO userEO, @Param("orgId") String orgId,
			@Param("roleId") String roleId, @Param("everyoneRoleId") String everyoneRoleId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询用户实例的匿名角色ID
	 * 
	 * @param userInstanceId
	 *            用户实例ID
	 * @param anonymousRoleTypeId
	 *            匿名角色类型ID
	 * @return 用户实例的匿名角色ID，没有匿名角色则返回空
	 */
	public String queryAnonymousRoleId(@Param("userInstanceId") String userInstanceId,
			@Param("anonymousRoleTypeId") String anonymousRoleTypeId);

	/**
	 * 查询指定范围用户的匿名角色ID
	 * 
	 * @param userId
	 *            用户ID
	 * @param anonymousRoleTypeId
	 *            匿名角色类型ID
	 * @param scopeId
	 *            用户范围ID
	 * @param scopeType
	 *            用户范围类型
	 * @return 匿名角色ID
	 */
	public String queryAnonymousRoleIdByScope(@Param("userId") String userId,
			@Param("anonymousRoleTypeId") String anonymousRoleTypeId, @Param("scopeId") String scopeId,
			@Param("scopeType") String scopeType);

	/**
	 * 查询多个用户的匿名角色ID集合
	 * 
	 * @param anonymousRoleTypeId
	 *            匿名角色类型ID
	 * @param userIds
	 *            用户ID数组
	 * @return 匿名角色ID集合
	 */
	public List<String> queryAnonymousRoleIdsByUserIds(@Param("anonymousRoleTypeId") String anonymousRoleTypeId,
			@Param("userIds") String... userIds);

	/**
	 * 查询指定范围多个用户的匿名角色ID集合
	 * 
	 * @param anonymousRoleTypeId
	 *            匿名角色类型ID
	 * @param scopeId
	 *            用户ID数组
	 * @param scopeType
	 *            用户ID数组
	 * @param userIds
	 *            用户ID数组
	 * @return 匿名角色ID集合
	 */
	public List<String> queryAnonymousRoleIdsByUserIdsAndScope(@Param("anonymousRoleTypeId") String anonymousRoleTypeId,
			@Param("scopeId") String scopeId, @Param("scopeType") String scopeType,
			@Param("userIds") String... userIds);

	/**
	 * 查询指定范围下的用户是否拥有某直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户范围类型
	 * @param scopeId
	 *            范围ID
	 * @param roleId
	 *            角色ID
	 * @return 大于0表示有直接角色关联，否则没有直接角色关联
	 */
	public int hasDirectRole(@Param("userId") String userId, @Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId, @Param("roleId") String roleId);

	/**
	 * 查询指定范围下的用户是否拥有某角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param scopeType
	 *            用户范围类型
	 * @param scopeId
	 *            范围ID
	 * @param roleId
	 *            角色ID
	 * @return 大于0表示有角色关联，否则没有角色关联
	 */
	public int hasRole(@Param("userId") String userId, @Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId, @Param("roleId") String roleId);

	/**
	 * 查询用户是否拥有某直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param privilegeId
	 *            权限ID
	 * @return 大于0表示有直接权限关联，否则没有直接权限关联
	 */
	public int hasDirectPrivilege(@Param("userId") String userId, @Param("roleType") String roleType,
			@Param("privilegeId") String privilegeId);

	/**
	 * 查询机构用户是否拥有某直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param roleType
	 *            角色类型（匿名角色类型）
	 * @param privilegeId
	 *            权限ID
	 * @return 大于0表示有直接权限关联，否则没有直接权限关联
	 */
	public int hasDirectOrgUserPrivilege(@Param("userId") String userId, @Param("orgId") String orgId,
			@Param("roleType") String roleType, @Param("privilegeId") String privilegeId);
}
