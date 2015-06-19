package com.chinacreator.asp.comp.sys.basic.user.service;

import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 用户服务接口
 * 
 * @author 彭盛
 * 
 */
public interface UserService extends
		com.chinacreator.asp.comp.sys.core.user.service.UserService {

	/**
	 * 在指定机构下新增用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param sn
	 *            用户在机构下的排序号
	 */
	public void create(UserDTO userDto, String orgId, int sn);

	/**
	 * 设置机构下用户的排序号
	 * 
	 * @param sortUserList
	 *            用户排序参数列表<br>
	 *            列表中map参数如下：
	 *            <table border="1">
	 *            <tr>
	 *            <td>key值</td>
	 *            <td>对象类型</td>
	 *            <td>说明</td>
	 *            </tr>
	 *            <tr>
	 *            <td>userId</td>
	 *            <td>java.lang.String</td>
	 *            <td>用户ID</td>
	 *            </tr>
	 *            <tr>
	 *            <td>orgId</td>
	 *            <td>java.lang.String</td>
	 *            <td>机构ID</td>
	 *            </tr>
	 *            <tr>
	 *            <td>sn</td>
	 *            <td>int</td>
	 *            <td>机构下用户的排序号</td>
	 *            </tr>
	 *            </table>
	 */
	public void setOrderInOrg(List<Map<String, Object>> sortUserList);

	/**
	 * 设置用户主机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void setMainOrg(String userId, String orgId);

	/**
	 * 删除机构下所有用户
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void deleteAllByOrg(String... orgIds);

	/**
	 * 删除机构下用户
	 * 
	 * @param orgId
	 *            机构
	 * @param userIds
	 *            用户ID数组
	 */
	public void deleteByOrg(String orgId, String... userIds);

	/**
	 * 分页查询所有用户
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的所有用户<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 分页查询用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryByUser(UserDTO userDto, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询指定机构下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByOrg(UserDTO userDto, String orgId);
	
	/**
	 * 分页查询指定机构下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryByOrg(UserDTO userDto, String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询指定用户所属机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OrgDTO> queryOrgs(String userId);

	/**
	 * 查询指定用户所属主机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public OrgDTO queryMainOrg(String userId);

	/**
	 * 查询指定用户创建的机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OrgDTO> queryCreatedOrgs(String userId);

	/**
	 * 查询指定用户可访问机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OrgDTO> queryOrgsPermmitedToAccess(String userId);

	/**
	 * 查询指定用户可管理机构
	 * 
	 * @param userId
	 *            用户ID
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OrgDTO> queryOrgsPermmitedToManage(String userId);

	/**
	 * 分页查询指定用户所拥有的直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<RoleDTO> queryDirectRoles(String userId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询指定机构下用户所拥有的直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryDirectRoles(String userId, String orgId);

	/**
	 * 查询指定机构下用户所拥有的直接角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<RoleDTO> queryDirectRoles(String userId, String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 分页查询指定用户所拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<RoleDTO> queryRoles(String userId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询指定机构下的用户所拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRoles(String userId, String orgId);

	/**
	 * 分页查询指定机构下的用户所拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<RoleDTO> queryRoles(String userId, String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询指定用户创建的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryCreatedRoles(String userId);

	/**
	 * 查询指定用户可访问的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRolesPermittedToAccess(String userId);

	/**
	 * 查询指定用户可管理的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<RoleDTO> queryRolesPermittedToManage(String userId);

	/**
	 * 查询用户拥有的直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryDirectPrivileges(String userId);

	/**
	 * 分页查询用户拥有的直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询机构下用户拥有的直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryDirectPrivileges(String userId, String orgId);

	/**
	 * 分页查询机构下用户拥有的直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
			String orgId, Pageable pageable, Sortable sortable);

	/**
	 * 分页查询用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<PrivilegeDTO> queryPrivileges(String userId, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询指定机构下用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<PrivilegeDTO> queryPrivileges(String userId, String orgId);

	/**
	 * 分页查询指定机构下用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的权限数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<PrivilegeDTO> queryPrivileges(String userId, String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询用户所拥有的菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<MenuDTO> queryMenus(String userId);

	/**
	 * 查询机构下用户所拥有的菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 菜单数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<MenuDTO> queryMenus(String userId, String orgId);

	/**
	 * 查询用户所创建的菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<MenuDTO> queryCreatedMenus(String userId);

	/**
	 * 查询用户可访问的菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<MenuDTO> queryMenusPermittedToAccess(String userId);

	/**
	 * 查询用户可管理的菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @return 菜单数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<MenuDTO> queryMenusPermittedToManage(String userId);

	/**
	 * 添加用户给机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void addToOrg(String userId, String orgId);

	/**
	 * 批量添加用户给机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void addToOrgs(String[] userIds, String[] orgIds);

	/**
	 * 设置用户给机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void setToOrg(String userId, String orgId);

	/**
	 * 批量设置用户给机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void setToOrgs(String[] userIds, String[] orgIds);

	/**
	 * 批量回收用户的所有机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void removeFromAllOrgs(String... userIds);

	/**
	 * 回收用户的机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 */
	public void removeFromOrg(String userId, String orgId);

	/**
	 * 批量回收用户的机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgIds
	 *            机构ID数组
	 */
	public void removeFromOrgs(String[] userIds, String[] orgIds);

	/**
	 * 添加指定机构下用户拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 */
	public void assignRole(String userId, String roleId, String orgId);

	/**
	 * 批量添加指定机构下用户拥有的角色
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void assignRoles(String[] userIds, String[] roleIds, String orgId);

	/**
	 * 设置指定机构下用户拥有的角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleIds
	 *            角色ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setRoles(String userId, String[] roleIds, String orgId);

	/**
	 * 批量设置指定机构下用户拥有的角色
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setRoles(String[] userIds, String[] roleIds, String orgId);

	/**
	 * 批量回收用户拥有的所有角色关系
	 * 
	 * @param userIds
	 *            用户IDs
	 * @param orgId
	 *            机构ID
	 */
	public void revokeAllRoles(String[] userIds, String orgId);

	/**
	 * 回收指定机构下用户拥有的指定角色关系
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 */
	public void revokeRole(String userId, String roleId, String orgId);

	/**
	 * 批量回收指定机构下用户拥有的指定角色关系
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param roleIds
	 *            角色ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void revokeRoles(String[] userIds, String[] roleIds, String orgId);

	/**
	 * 添加指定机构下用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @param orgId
	 *            机构ID
	 */
	public void assignPrivilege(String userId, String privilegeId, String orgId);

	/**
	 * 批量添加指定机构下用户拥有的权限
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void assignPrivileges(String[] userIds, String[] privilegeIds,
			String orgId);

	/**
	 * 设置指定机构下用户拥有的权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeIds
	 *            权限ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setPrivileges(String userId, String[] privilegeIds, String orgId);

	/**
	 * 批量设置指定机构下用户拥有的权限
	 * 
	 * @param userIds
	 *            角色ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void setPrivilegesToUsers(String[] userIds, String[] privilegeIds,
			String orgId);

	/**
	 * 回收用户的所有直接权限
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void revokeAllPrivileges(String... userIds);

	/**
	 * 回收指定机构下用户的所有直接权限
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void revokeAllPrivileges(String[] userIds, String orgId);

	/**
	 * 回收指定机构下用户的直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @param orgId
	 *            机构ID
	 */
	public void revokePrivilege(String userId, String privilegeId, String orgId);

	/**
	 * 批量回收指定机构下用户的直接权限
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param privilegeIds
	 *            权限ID数组
	 * @param orgId
	 *            机构ID
	 */
	public void revokePrivileges(String[] userIds, String[] privilegeIds,
			String orgId);

	/**
	 * 判断用户是否属于指定机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:是，false:否
	 */
	public boolean belongsToOrg(String userId, String orgId);

	/**
	 * 判断指定机构下用户是否拥有直接授予其的指定角色
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean hasDirectRole(String userId, String roleId, String orgId);

	/**
	 * 判断指定机构下用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean hasRole(String userId, String roleId, String orgId);

	/**
	 * 判断用户是否拥有指定直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @return true:有，false:无
	 */
	public boolean hasDirectPrivilege(String userId, String privilegeId);

	/**
	 * 判断用户是否拥有指定直接权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean hasDirectPrivilege(String userId, String privilegeId,
			String orgId);

	/**
	 * 判断指定机构下用户是否拥有指定权限
	 * 
	 * @param userId
	 *            用户ID
	 * @param privilegeId
	 *            权限ID
	 * @param orgId
	 *            机构ID
	 * @return true:有，false:无
	 */
	public boolean hasPrivilege(String userId, String privilegeId, String orgId);

	/**
	 * 设置指定机构下用户的有效状态
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @param enabled
	 *            是否有效（true:有效，false:无效）
	 */
	public void setEnabledByOrg(String userId, String orgId, boolean enabled);

	/**
	 * 判断指定机构下用户是否有效
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:有效，false:无效
	 */
	public boolean isEnabledByOrg(String userId, String orgId);

	/**
	 * 判断指定用户是否超级管理员
	 * 
	 * @param userId
	 *            用户ID
	 * @return true:是，false:否
	 */
	public boolean isAdmin(String userId);

	/**
	 * 判断指定机构是否用户的主机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:是，false:否
	 */
	public boolean isMainOrg(String userId, String orgId);

	/**
	 * 判断指定用户是否机构管理员
	 * 
	 * @param userId
	 *            用户ID
	 * @return true:是，false:否
	 */
	public boolean isOrgManager(String userId);

	/**
	 * 判断用户是否指定机构的机构管理员
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:是，false:否
	 */
	public boolean isOrgManager(String userId, String orgId);

	/**
	 * 判断用户是否是指定机构的创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:是，false:否
	 */
	public boolean isOrgSCreator(String userId, String orgId);

	/**
	 * 判断指定机构是否用户的可管理机构
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return true:是，false:否
	 */
	public boolean isMgtPermitted(String userId, String orgId);

	/**
	 * 判断用户是否是指定角色的创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @return true:是，false:否
	 */
	public boolean isRoleSCreator(String userId, String roleId);

	/**
	 * 判断指定用户是否拥有指定菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @param menuId
	 *            菜单ID
	 * @return true:是，false:否
	 */
	public boolean hasMenu(String userId, String menuId);

	/**
	 * 判断用户是否是指定菜单的创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param menuId
	 *            菜单ID
	 * @return true:是，false:否
	 */
	public boolean isMenuSCreator(String userId, String menuId);

	/**
	 * 重置密码
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void resetPasswords(String... userIds);

	/**
	 * 获取默认密码
	 * 
	 * @return 默认密码
	 */
	public String getDefaultPwd();

}
