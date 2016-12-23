package com.chinacreator.asp.comp.sys.basic.privilege.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;

/**
 * 权限数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface PrivilegeDao {

	/**
	 * 查询指定权限的子权限(递归)
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 指定权限的子权限数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeEO> queryChildPrivileges(
			@Param("privilegeId") String privilegeId);

	/**
	 * 查询指定权限的子权限(不递归)
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 指定权限的子权限数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeEO> queryChildPrivilegesUnRecursive(
			@Param("privilegeId") String privilegeId);

	/**
	 * 查询当前权限及其父权限（递归）
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 当前权限及其父权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeEO> queryFatherPrivileges(
			@Param("privilegeId") String privilegeId);

	/**
	 * 查询当前权限及其父权限（不递归）
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 当前权限及其父权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeEO> queryFatherPrivilegesUnRecursive(
			@Param("privilegeId") String privilegeId);

	/**
	 * 判断指定权限是否有子权限
	 * 
	 * @param privilegeId
	 *            权限ID
	 * @return 大于0：有子权限，否则：没有子权限
	 */
	public int existsChildPrivileges(@Param("privilegeId") String privilegeId);

}
