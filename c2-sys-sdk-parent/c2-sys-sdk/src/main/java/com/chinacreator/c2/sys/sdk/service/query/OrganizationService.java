package com.chinacreator.c2.sys.sdk.service.query;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Organization;

/**
 * 机构信息查询服务
 * <p>集成版和分布式版本系统管理的统一接口，其中查询类接口包括：
 * <ul>
 * 	<li>{@link UserService}</li>
 *  <li>{@link OrganizationService}</li>
 *  <li>{@link RoleService}</li>
 *  <li>{@link OrgUserTreeService}</li>
 * </ul>
 * 管理类接口包括：
 * <ul>
 * 	<li>{@link UserManageService}(暂未实现)</li>
 * </ul>
 *
 * @see UserService
 * @see OrgUserTreeService
 * @see RoleService
 * @author Vurt
 * @since 5.0
 */
public interface OrganizationService {
	/**
	 * 查询机构
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public Organization get(String orgId);

	/**
	 * 查询子机构，不包含自身
	 * 
	 * @param orgId
	 *            机构ID
	 * @param cascade
	 *            是否递归查询所有子机构
	 * @return 子机构数据传输对象列表
	 */
	public List<Organization> getChildren(String orgId, boolean cascade);

	/**
	 * 查询当前机构及其所有父机构，不包含自身
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 当前机构及其父机构数据传输对象列表，第一个是顶级机构<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<Organization> getParents(String orgId);

//	/**
//	 * 判断机构是否拥有指定角色
//	 * 
//	 * @param orgId
//	 *            机构ID
//	 * @param roleId
//	 *            角色ID
//	 * @return true:有，false:无
//	 */
//	public boolean hasRole(String orgId, @NotNull String roleId);

	/**
	 * 查询机构
	 * 
	 * @param org
	 *            机构数据传输对象,null查所有机构
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	//TODO 暂不提供条件查询接口
	//public List<Organization> query(Organization org);

	/**
	 * 判断机构下是否有指定用户
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return true:有，false:无
	 */
	public boolean containsUser(String orgId, String userId);
}
