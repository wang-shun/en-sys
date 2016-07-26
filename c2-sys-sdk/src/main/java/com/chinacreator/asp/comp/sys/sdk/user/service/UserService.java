package com.chinacreator.asp.comp.sys.sdk.user.service;

import com.chinacreator.asp.comp.sys.sdk.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.sdk.menu.dto.MenuDTO;
import com.chinacreator.asp.comp.sys.sdk.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.sdk.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;
import java.util.Map;


/**
 * 用户服务接口
 * @author 彭盛
 */
public interface UserService {
    /**
    * 给机构下用户添加岗位
    * @param userId用户ID
    * @param jobIds岗位ID数组
    */
    public void addJobs(String userId, String... jobIds);

    /**
    * 批量给机构下用户添加岗位
    * @param userIds用户ID数组
    * @param jobIds岗位ID数组
    */
    public void addJobs(String[] userIds, String[] jobIds);

    /**
    * 给机构下用户设置岗位
    * @param userId用户ID
    * @param jobIds岗位ID数组
    */
    public void setJobs(String userId, String... jobIds);

    /**
    * 批量给机构下用户设置岗位
    * @param userIds用户ID数组
    * @param jobIds岗位ID数组
    */
    public void setJobs(String[] userIds, String[] jobIds);

    /**
    * 批量回收用户的所有岗位
    * @param userIds用户ID数组
    */
    public void removeAllJobs(String... userIds);

    /**
    * 批量回收机构下用户的所有岗位
    * @param userIds用户ID数组
    */
    public void removeAllJobs(String orgId, String... userIds);

    /**
    * 回收机构下用户的岗位
    * @param userId用户ID
    * @param jobIds岗位ID数组
    */
    public void removeJobs(String userId, String... jobIds);

    /**
    * 回收机构下用户的岗位
    * @param userIds用户ID数组
    * @param jobIds岗位ID数组
    */
    public void removeJobs(String[] userIds, String[] jobIds);

    /**
    * 判断用户是否属于指定岗位
    * @param userId用户ID
    * @param jobId岗位ID
    * @return true:是，false:否
    */
    public boolean belongsJob(String userId, String jobId);

    /**
    * 判断机构下用户是否属于指定岗位
    * @param orgId机构ID
    * @param userId用户ID
    * @param jobId岗位ID
    * @return true:是，false:否
    */
    public boolean belongsJob(String orgId, String userId, String jobId);

    /**
    * 查询指定机构岗位下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param jobId岗位ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByOrgJob(UserDTO userDto, String orgId,
        String jobId);

    /**
    * 分页查询指定机构岗位下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param jobId岗位ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryByOrgJob(UserDTO userDto, String orgId,
        String jobId, Pageable pageable, Sortable sortable);

    /**
    * 查询指定岗位下用户
    * @param userDto用户数据传输对象
    * @param jobId岗位ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByJob(UserDTO userDto, String jobId);

    /**
    * 分页查询指定岗位下用户
    * @param userDto用户数据传输对象
    * @param jobId岗位ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryByJob(UserDTO userDto, String jobId,
        Pageable pageable, Sortable sortable);

    /**
    * 在指定机构下新增用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param sn用户在机构下的排序号
    */
    public void create(UserDTO userDto, String orgId, int sn);

    /**
    * 设置机构下用户的排序号
    * @param sortUserList用户排序参数列表<br>
    * 列表中map参数如下：
    * <table border="1">
    * <tr>
    * <td>key值</td>
    * <td>对象类型</td>
    * <td>说明</td>
    * </tr>
    * <tr>
    * <td>userId</td>
    * <td>java.lang.String</td>
    * <td>用户ID</td>
    * </tr>
    * <tr>
    * <td>orgId</td>
    * <td>java.lang.String</td>
    * <td>机构ID</td>
    * </tr>
    * <tr>
    * <td>sn</td>
    * <td>int</td>
    * <td>机构下用户的排序号</td>
    * </tr>
    * </table>
    */
    public void setOrderInOrg(List<Map<String, Object>> sortUserList);

    /**
    * 设置用户主机构
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void setMainOrg(String userId, String orgId);

    /**
    * 删除机构下所有用户
    * @param orgIds机构ID数组
    */
    public void deleteAllByOrg(String... orgIds);

    /**
    * 删除机构下用户
    * @param orgId机构
    * @param userIds用户ID数组
    */
    public void deleteByOrg(String orgId, String... userIds);

    /**
    * 分页查询所有用户
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的所有用户<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 分页查询用户
    * @param userDto用户数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryByUser(UserDTO userDto, Pageable pageable,
        Sortable sortable);

    /**
    * 查询指定机构下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByOrg(UserDTO userDto, String orgId);

    /**
    * 分页查询指定机构下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryByOrg(UserDTO userDto, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询指定机构及角色下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param roleId角色ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByOrgRole(UserDTO userDto, String orgId,
        String roleId);

    /**
    * 分页查询指定机构及角色下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param roleId角色ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryByOrgRole(UserDTO userDto, String orgId,
        String roleId, Pageable pageable, Sortable sortable);

    /**
    * 查询指定用户所属机构
    * @param userId用户ID
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryOrgs(String userId);

    /**
    * 查询指定用户所属主机构
    * @param userId用户ID
    * @return 机构数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public OrgDTO queryMainOrg(String userId);

    /**
    * 查询指定用户创建的机构
    * @param userId用户ID
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryCreatedOrgs(String userId);

    /**
    * 查询指定用户可访问机构
    * @param userId用户ID
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryOrgsPermmitedToAccess(String userId);

    /**
    * 查询指定用户可管理机构
    * @param userId用户ID
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryOrgsPermmitedToManage(String userId);

    /**
    * 分页查询指定用户所拥有的直接角色
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<RoleDTO> queryDirectRoles(String userId, Pageable pageable,
        Sortable sortable);

    /**
    * 查询指定机构下用户所拥有的直接角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryDirectRoles(String userId, String orgId);

    /**
    * 查询指定机构下用户所拥有的直接角色
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<RoleDTO> queryDirectRoles(String userId, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 分页查询指定用户所拥有的角色
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryRoles(String userId, Pageable pageable,
        Sortable sortable);

    /**
    * 查询指定机构下的用户所拥有的角色
    * @param userId用户ID
    * @param orgId机构ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String userId, String orgId);

    /**
    * 分页查询指定机构下的用户所拥有的角色
    * @param userId用户ID
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryRoles(String userId, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询指定用户创建的角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryCreatedRoles(String userId);

    /**
    * 查询指定用户可访问的角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRolesPermittedToAccess(String userId);

    /**
    * 查询指定用户可管理的角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRolesPermittedToManage(String userId);

    /**
    * 查询用户拥有的直接权限
    * @param userId用户ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryDirectPrivileges(String userId);

    /**
    * 分页查询用户拥有的直接权限
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询机构下用户拥有的直接权限
    * @param userId用户ID
    * @param orgId机构ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryDirectPrivileges(String userId, String orgId);

    /**
    * 分页查询机构下用户拥有的直接权限
    * @param userId用户ID
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<PrivilegeDTO> queryDirectPrivileges(String userId,
        String orgId, Pageable pageable, Sortable sortable);

    /**
    * 分页查询用户拥有的权限
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<PrivilegeDTO> queryPrivileges(String userId, Pageable pageable,
        Sortable sortable);

    /**
    * 查询指定机构下用户拥有的权限
    * @param userId用户ID
    * @param orgId机构ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryPrivileges(String userId, String orgId);

    /**
    * 分页查询指定机构下用户拥有的权限
    * @param userId用户ID
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<PrivilegeDTO> queryPrivileges(String userId, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询用户所拥有的菜单
    * @param userId用户ID
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryMenus(String userId);

    /**
    * 查询机构下用户所拥有的菜单
    * @param userId用户ID
    * @param orgId机构ID
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryMenus(String userId, String orgId);

    /**
    * 查询用户所创建的菜单
    * @param userId用户ID
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryCreatedMenus(String userId);

    /**
    * 查询用户可访问的菜单
    * @param userId用户ID
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryMenusPermittedToAccess(String userId);

    /**
    * 查询用户可管理的菜单
    * @param userId用户ID
    * @return 菜单数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<MenuDTO> queryMenusPermittedToManage(String userId);

    /**
    * 添加用户给机构
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void addToOrg(String userId, String orgId);

    /**
    * 批量添加用户给机构
    * @param userIds用户ID数组
    * @param orgIds机构ID数组
    */
    public void addToOrgs(String[] userIds, String[] orgIds);

    /**
    * 设置用户给机构
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void setToOrg(String userId, String orgId);

    /**
    * 批量设置用户给机构
    * @param userIds用户ID数组
    * @param orgIds机构ID数组
    */
    public void setToOrgs(String[] userIds, String[] orgIds);

    /**
    * 批量回收用户的所有机构
    * @param userIds用户ID数组
    */
    public void removeFromAllOrgs(String... userIds);

    /**
    * 回收用户的机构
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void removeFromOrg(String userId, String orgId);

    /**
    * 批量回收用户的机构
    * @param userIds用户ID数组
    * @param orgIds机构ID数组
    */
    public void removeFromOrgs(String[] userIds, String[] orgIds);

    /**
    * 添加指定机构下用户拥有的角色
    * @param userId用户ID
    * @param roleId角色ID
    * @param orgId机构ID
    */
    public void assignRole(String userId, String roleId, String orgId);

    /**
    * 批量添加指定机构下用户拥有的角色
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param orgId机构ID
    */
    public void assignRoles(String[] userIds, String[] roleIds, String orgId);

    /**
    * 设置指定机构下用户拥有的角色
    * @param userId用户ID
    * @param roleIds角色ID数组
    * @param orgId机构ID
    */
    public void setRoles(String userId, String[] roleIds, String orgId);

    /**
    * 批量设置指定机构下用户拥有的角色
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param orgId机构ID
    */
    public void setRoles(String[] userIds, String[] roleIds, String orgId);

    /**
    * 批量回收用户拥有的所有角色关系
    * @param userIds用户IDs
    * @param orgId机构ID
    */
    public void revokeAllRoles(String[] userIds, String orgId);

    /**
    * 回收指定机构下用户拥有的指定角色关系
    * @param userId用户ID
    * @param roleId角色ID
    * @param orgId机构ID
    */
    public void revokeRole(String userId, String roleId, String orgId);

    /**
    * 批量回收指定机构下用户拥有的指定角色关系
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param orgId机构ID
    */
    public void revokeRoles(String[] userIds, String[] roleIds, String orgId);

    /**
    * 添加指定机构下用户拥有的权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @param orgId机构ID
    */
    public void assignPrivilege(String userId, String privilegeId, String orgId);

    /**
    * 批量添加指定机构下用户拥有的权限
    * @param userIds用户ID数组
    * @param privilegeIds权限ID数组
    * @param orgId机构ID
    */
    public void assignPrivileges(String[] userIds, String[] privilegeIds,
        String orgId);

    /**
    * 设置指定机构下用户拥有的权限
    * @param userId用户ID
    * @param privilegeIds权限ID数组
    * @param orgId机构ID
    */
    public void setPrivileges(String userId, String[] privilegeIds, String orgId);

    /**
    * 批量设置指定机构下用户拥有的权限
    * @param userIds角色ID数组
    * @param privilegeIds权限ID数组
    * @param orgId机构ID
    */
    public void setPrivilegesToUsers(String[] userIds, String[] privilegeIds,
        String orgId);

    /**
    * 回收用户的所有直接权限
    * @param userIds用户ID数组
    */
    public void revokeAllPrivileges(String... userIds);

    /**
    * 回收指定机构下用户的所有直接权限
    * @param userIds用户ID数组
    * @param orgId机构ID
    */
    public void revokeAllPrivileges(String[] userIds, String orgId);

    /**
    * 回收指定机构下用户的直接权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @param orgId机构ID
    */
    public void revokePrivilege(String userId, String privilegeId, String orgId);

    /**
    * 批量回收指定机构下用户的直接权限
    * @param userIds用户ID数组
    * @param privilegeIds权限ID数组
    * @param orgId机构ID
    */
    public void revokePrivileges(String[] userIds, String[] privilegeIds,
        String orgId);

    /**
    * 判断用户是否属于指定机构
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean belongsToOrg(String userId, String orgId);

    /**
    * 判断指定机构下用户是否拥有直接授予其的指定角色
    * @param userId用户ID
    * @param roleId角色ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean hasDirectRole(String userId, String roleId, String orgId);

    /**
    * 判断指定机构下用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
    * @param userId用户ID
    * @param roleId角色ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean hasRole(String userId, String roleId, String orgId);

    /**
    * 判断用户是否拥有指定直接权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @return true:有，false:无
    */
    public boolean hasDirectPrivilege(String userId, String privilegeId);

    /**
    * 判断用户是否拥有指定直接权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean hasDirectPrivilege(String userId, String privilegeId,
        String orgId);

    /**
    * 判断指定机构下用户是否拥有指定权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean hasPrivilege(String userId, String privilegeId, String orgId);

    /**
    * 设置指定机构下用户的有效状态
    * @param userId用户ID
    * @param orgId机构ID
    * @param enabled是否有效（true:有效，false:无效）
    */
    public void setEnabledByOrg(String userId, String orgId, boolean enabled);

    /**
    * 判断指定机构下用户是否有效
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:有效，false:无效
    */
    public boolean isEnabledByOrg(String userId, String orgId);

    /**
    * 判断指定用户是否超级管理员
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isAdmin(String userId);

    /**
    * 判断指定机构是否用户的主机构
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean isMainOrg(String userId, String orgId);

    /**
    * 判断指定用户是否机构管理员
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isOrgManager(String userId);

    /**
    * 判断用户是否指定机构的机构管理员
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean isOrgManager(String userId, String orgId);

    /**
    * 判断用户是否是指定机构的创建者
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean isOrgSCreator(String userId, String orgId);

    /**
    * 判断指定机构是否用户的可管理机构
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean isMgtPermitted(String userId, String orgId);

    /**
    * 判断用户是否是指定角色的创建者
    * @param userId用户ID
    * @param roleId角色ID
    * @return true:是，false:否
    */
    public boolean isRoleSCreator(String userId, String roleId);

    /**
    * 判断指定用户是否拥有指定菜单
    * @param userId用户ID
    * @param menuId菜单ID
    * @return true:是，false:否
    */
    public boolean hasMenu(String userId, String menuId);

    /**
    * 判断用户是否是指定菜单的创建者
    * @param userId用户ID
    * @param menuId菜单ID
    * @return true:是，false:否
    */
    public boolean isMenuSCreator(String userId, String menuId);

    /**
    * 重置密码
    * @param userIds用户ID数组
    */
    public void resetPasswords(String... userIds);

    /**
    * 获取默认密码
    * @return 默认密码
    */
    public String getDefaultPwd();

    /**
    * 新增用户
    * @param userDto用户数据传输对象
    */
    public void create(UserDTO userDto);

    /**
    * 新增用户
    * @param userDto用户数据传输对象
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void create(UserDTO userDto, int scopeType, String scopeId);

    /**
    * 修改用户
    * @param userDto用户数据传输对象
    */
    public void update(UserDTO userDto);

    /**
    * 设置用户排序
    * @param userDTOList用户数据传输对象列表<br>
    * 用户数据传输对象必须包含用户ID和用户排序号
    */
    public void setOrder(List<UserDTO> userDTOList);

    /**
    * 修改用户密码
    * @param userId用户ID
    * @param password用户密码
    */
    public void updatePassword(String userId, String password);

    /**
    * 修改用户密码
    * @param userName用户账号
    * @param oldPassword用户原密码
    * @param newPassword用户新密码
    */
    public void updatePassword(String userName, String oldPassword,
        String newPassword);

    /**
    * 删除用户
    * @param userIds用户ID数组
    */
    public void deleteByPKs(String... userIds);

    /**
    * 删除用户
    * @param userName用户账号
    */
    public void deleteByUserName(String userName);

    /**
    * 删除用户实例
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void deleteUserInstanceByScope(String userId, int scopeType,
        String scopeId);

    /**
    * 删除用户实例
    * @param userInstanceIds用户实例ID数组
    */
    public void deleteUserInstancesByUserInstanceIds(String... userInstanceIds);

    /**
    * 查询所有用户
    * @return 所有用户<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryAll();

    /**
    * 查询用户
    * @param userDto用户数据传输对象
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByUser(UserDTO userDto);

    /**
    * 查询用户
    * @param userId用户ID
    * @return 用户数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public UserDTO queryByPK(String userId);

    /**
    * 查询用户
    * @param userName用户账号
    * @return 用户数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public UserDTO queryByUserName(String userName);

    /**
    * 查询用户
    * @param userRealname用户实名
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByUserRealName(String userRealname);

    /**
    * 查询指定用户活动范围下的所有用户
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryByScope(int scopeType, String scopeId);

    /**
    * 查询指定用户所拥有的直接角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryDirectRoles(String userId);

    /**
    * 查询指定用户所拥有的直接角色
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryDirectRoles(String userId, int scopeType,
        String scopeId);

    /**
    * 查询指定用户所拥有的角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String userId);

    /**
    * 查询指定用户所拥有的角色
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String userId, int scopeType, String scopeId);

    /**
    * 查询用户所属用户组
    * @param userId用户ID
    * @return 用户组数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<GroupDTO> queryGroups(String userId);

    /**
    * 查询用户所属用户组
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 用户组数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<GroupDTO> queryGroups(String userId, int scopeType,
        String scopeId);

    /**
    * 查询用户拥有的权限
    * @param userId用户ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryPrivileges(String userId);

    /**
    * 查询用户拥有的权限
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryPrivileges(String userId, int scopeType,
        String scopeId);

    /**
    * 添加用户拥有的角色
    * @param userId用户ID
    * @param roleId角色ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void assignRole(String userId, String roleId, int scopeType,
        String scopeId);

    /**
    * 批量添加用户拥有的角色
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void assignRoles(String[] userIds, String[] roleIds, int scopeType,
        String scopeId);

    /**
    * 设置用户拥有的角色
    * @param userId用户ID
    * @param roleIds角色ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setRoles(String userId, String[] roleIds, int scopeType,
        String scopeId);

    /**
    * 批量设置用户拥有的角色
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setRoles(String[] userIds, String[] roleIds, int scopeType,
        String scopeId);

    /**
    * 批量回收用户拥有的所有角色关系
    * @param userIds用户IDs
    */
    public void revokeAllRoles(String... userIds);

    /**
    * 批量回收用户拥有的所有角色关系
    * @param userIds用户IDs
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void revokeAllRoles(String[] userIds, int scopeType, String scopeId);

    /**
    * 回收用户拥有的指定角色关系
    * @param userId用户ID
    * @param roleId角色ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void revokeRole(String userId, String roleId, int scopeType,
        String scopeId);

    /**
    * 批量回收用户拥有的指定角色关系
    * @param userIds用户ID数组
    * @param roleIds角色ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void revokeRoles(String[] userIds, String[] roleIds, int scopeType,
        String scopeId);

    /**
    * 添加用户到用户组
    * @param userId用户ID
    * @param groupId用户组ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void addToGroup(String userId, String groupId, int scopeType,
        String scopeId);

    /**
    * 批量添加用户到用户组
    * @param userIds用户ID数组
    * @param groupIds用户组ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void addToGroups(String[] userIds, String[] groupIds, int scopeType,
        String scopeId);

    /**
    * 设置用户所属用户组
    * @param userId用户ID
    * @param groupId用户组ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setGroup(String userId, String groupId, int scopeType,
        String scopeId);

    /**
    * 批量设置用户所属用户组
    * @param userIds用户ID数组
    * @param groupIds用户组ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setGroups(String[] userIds, String[] groupIds, int scopeType,
        String scopeId);

    /**
    * 将用户从其所属所有用户组中删除
    * @param userIds用户IDs
    */
    public void removeFromAllGroups(String... userIds);

    /**
    * 将用户从指定用户组中删除
    * @param userId用户ID
    * @param groupId用户组ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void removeFromGroup(String userId, String groupId, int scopeType,
        String scopeId);

    /**
    * 将用户从指定用户组列表中删除
    * @param userIds用户ID数组
    * @param groupIds用户组ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void removeFromGroups(String[] userIds, String[] groupIds,
        int scopeType, String scopeId);

    /**
    * 设置用户在指定活动范围内的有效状态
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @param enabled是否有效（true:有效，false:无效）
    */
    public void setEnabledByScope(String userId, int scopeType, String scopeId,
        boolean enabled);

    /**
    * 判断用户是否拥有直接授予其的指定角色
    * @param userId用户ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasDirectRole(String userId, String roleId);

    /**
    * 判断用户是否拥有直接授予其的指定角色
    * @param userId用户ID
    * @param roleId角色ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean hasDirectRole(String userId, String roleId, int scopeType,
        String scopeId);

    /**
    * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
    * @param userId用户ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasRole(String userId, String roleId);

    /**
    * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
    * @param userId用户ID
    * @param roleId角色ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean hasRole(String userId, String roleId, int scopeType,
        String scopeId);

    /**
    * 判断用户是否属于指定用户组
    * @param userId用户ID
    * @param groupId用户组ID
    * @return true:有，false:无
    */
    public boolean belongsToGroup(String userId, String groupId);

    /**
    * 判断用户是否属于指定用户组
    * @param userId用户ID
    * @param groupId用户组ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean belongsToGroup(String userId, String groupId, int scopeType,
        String scopeId);

    /**
    * 判断用户是否拥有指定权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @return true:有，false:无
    */
    public boolean hasPrivilege(String userId, String privilegeId);

    /**
    * 判断用户是否拥有指定权限
    * @param userId用户ID
    * @param privilegeId权限ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean hasPrivilege(String userId, String privilegeId,
        int scopeType, String scopeId);

    /**
    * 判断用户在指定活动范围内是否有效
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有效，false:无效
    */
    public boolean isEnabledByScope(String userId, int scopeType, String scopeId);

    /**
    * 判断用户是否存在
    * @param userId用户ID
    * @return true:存在，false:不存在
    */
    public boolean existsByPK(String userId);

    /**
    * 判断用户是否存在
    * @param userName用户帐号
    * @return true:存在，false:不存在
    */
    public boolean existsByUserName(String userName);

    /**
    * 判断用户是否存在
    * @param userRealname用户实名
    * @return true:存在，false:不存在
    */
    public boolean existsByUserRealname(String userRealname);
}
