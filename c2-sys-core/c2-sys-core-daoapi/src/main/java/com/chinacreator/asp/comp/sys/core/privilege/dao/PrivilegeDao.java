package com.chinacreator.asp.comp.sys.core.privilege.dao;

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
 * 权限数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface PrivilegeDao {

	/**
	 * 新增权限
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int create(PrivilegeEO privilegeEO);

	/**
	 * 修改权限
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int update(PrivilegeEO privilegeEO);

	/**
	 * 批量删除权限
	 * 
	 * @param privilegeIds
	 *            权限ID数组
	 */
	public int deleteByPKs(String... privilegeIds);

	/**
	 * 批量删除权限
	 * 
	 * @param privilegeId
	 *            权限ID
	 */
	public int deleteByPK(String privilegeId);

	/**
	 * 查询权限
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 权限数据访问对象<br>
	 *         没查询到的情况下返回null
	 */
	public PrivilegeEO queryByPK(String privilegeId);

	/**
	 * 查询权限
	 * 
	 * @param privilegeCode
	 *            权限编码
	 * @return 权限数据访问对象<br>
	 *         没查询到的情况下返回null
	 */
	public PrivilegeEO queryByCode(String privilegeCode);

	/**
	 * 查询权限
	 * 
	 * @param permExpr
	 *            权限字符串
	 * @return 权限数据访问对象<br>
	 *         没查询到的情况下返回null
	 */
	public PrivilegeEO queryByPermExpr(String permExpr);

	/**
	 * 查询所有权限
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象（注意privilegeEO不能为空，属性全为空时查全部权限）
	 * @return 权限数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeEO> query(PrivilegeEO privilegeEO);

	/**
	 * 通过权限编码批量查询出权限ID集合
	 * 
	 * @param privilegeCodes
	 *            权限编码数组
	 * @return 权限ID集合
	 */
	public List<String> queryIdsByCodes(String... privilegeCodes);

	/**
	 * 查询拥有权限的用户
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryUsers(String privilegeId);

	/**
	 * 查询拥有权限的用户
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryUsersByScope(
			@Param("privilegeId") String privilegeId,
			@Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId);

	/**
	 * 查询拥有权限的用户组
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @return 用户组数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<GroupEO> queryGroups(PrivilegeEO privilegeEO);

	/**
	 * 查询拥有权限的角色
	 * 
	 * @param privilegeEO
	 *            权限数据访问对象
	 * @return 角色数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleEO> queryRoles(PrivilegeEO privilegeEO);

	/**
	 * 通过角色ID数组查询权限集合
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @return 权限列表
	 */
	public List<PrivilegeEO> queryPrivilegesByRoleIds(
			@Param("roleIds") List<String> roleIds);

	/**
	 * 通过角色ID数组查询权限集合(带分页)
	 * 
	 * @param roleIds
	 *            角色ID数组
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的权限列表
	 */
	public Page<PrivilegeEO> queryPrivilegesByRoleIds(
			@Param("roleIds") List<String> roleIds, Pageable pageable,
			Sortable sortable);

	/**
	 * 判断权限编码是否存在
	 * 
	 * @param code
	 *            权限编码
	 * @return 大于0：存在，否则不存在
	 */
	public int existsByCode(String code);

	/**
	 * 判断权限编码是否存在(忽略指定权限ID，编辑时用)
	 * 
	 * @param code
	 *            权限编码
	 * @param id
	 *            权限ID
	 * @return 大于0：存在，否则不存在
	 */
	public int existsByCodeIgnoreId(@Param("code") String code,
			@Param("id") String id);
}
