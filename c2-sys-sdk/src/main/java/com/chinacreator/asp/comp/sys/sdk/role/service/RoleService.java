package com.chinacreator.asp.comp.sys.sdk.role.service;

import com.chinacreator.asp.comp.sys.sdk.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.sdk.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 角色服务接口
 * @author 彭盛
 */
public interface RoleService {
    /**
    * 添加角色给岗位
    * @param roleId角色ID
    * @param jobIds岗位ID数组
    */
    public void assignToJob(String roleId, String... jobIds);

    /**
    * 批量添加角色给岗位
    * @param roleIds角色ID数组
    * @param jobIds岗位ID数组
    */
    public void assignToJob(String[] roleIds, String[] jobIds);

    /**
    * 设置角色给岗位
    * @param roleId角色ID
    * @param jobIds岗位ID数组
    */
    public void setToJob(String roleId, String... jobIds);

    /**
    * 批量设置角色给岗位
    * @param roleIds角色ID数组
    * @param jobIds岗位ID数组
    */
    public void setToJob(String[] roleIds, String[] jobIds);

    /**
    * 批量回收指定角色与其授予的所有岗位关系
    * @param roleIds角色ID数组
    */
    public void revokeFromAllJobs(String... roleIds);

    /**
    * 回收指定角色与其授予的岗位关系
    * @param roleId角色ID
    * @param jobIds岗位ID数组
    */
    public void revokeFromJobs(String roleId, String... jobIds);

    /**
    * 批量回收指定角色与其授予的岗位关系
    * @param roleIds角色ID数组
    * @param jobIds岗位ID数组
    */
    public void revokeFromJobs(String[] roleIds, String[] jobIds);

    /**
    * 判断角色是否已分配给岗位
    * @param roleId角色ID
    * @param jobId岗位ID
    * @return true:有，false:无
    */
    public boolean isAssignedToJob(String roleId, String jobId);

    /**
    * 创建匿名角色
    * @param roleDTO角色业务对象
    */
    public void createAnonymousRole(RoleDTO roleDTO);

    /**
    * 分页查询所有角色
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 分页查询角色
    * @param roleDTO角色数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryByRole(RoleDTO roleDTO, Pageable pageable,
        Sortable sortable);

    /**
    * 查询角色(忽略匿名角色类型)
    * @param roleDTO角色数据传输对象
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryByRoleIgnoreAnonymous(RoleDTO roleDTO);

    /**
    * 分页查询角色(忽略匿名角色类型)
    * @param roleDTO角色数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryByRoleIgnoreAnonymous(RoleDTO roleDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 分页查询拥有特定角色的所有用户
    * @param roleId角色ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryUsers(String roleId, Pageable pageable,
        Sortable sortable);

    /**
    * 查询拥有特定角色的指定机构下用户
    * @param roleId角色ID
    * @param orgId机构ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String roleId, String orgId);

    /**
    * 分页查询拥有特定角色的指定机构下用户
    * @param roleId角色ID
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryUsers(String roleId, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 分页查询角色所拥有的权限
    * @param roleId角色ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public Page<PrivilegeDTO> queryPrivileges(String roleId, Pageable pageable,
        Sortable sortable);

    /**
    * 授予角色给机构下用户
    * @param roleId角色ID
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void assignToUser(String roleId, String userId, String orgId);

    /**
    * 批量授予角色给机构下用户
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param orgId机构ID
    */
    public void assignToUsers(String[] roleIds, String[] userIds, String orgId);

    /**
    * 设置角色给机构下用户
    * @param roleId角色ID
    * @param userIds用户ID数组
    * @param orgId机构ID
    */
    public void setToUser(String roleId, String[] userIds, String orgId);

    /**
    * 批量设置角色给机构下用户
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param orgId机构ID
    */
    public void setToUsers(String[] roleIds, String[] userIds, String orgId);

    /**
    * 添加角色给机构
    * @param roleId角色ID
    * @param orgId机构ID
    */
    public void assignToOrg(String roleId, String orgId);

    /**
    * 批量添加角色给机构
    * @param roleIds角色ID数组
    * @param orgIds机构ID数组
    */
    public void assignToOrgs(String[] roleIds, String[] orgIds);

    /**
    * 设置角色给机构
    * @param roleId角色ID
    * @param orgIds机构ID数组
    */
    public void setToOrgs(String roleId, String[] orgIds);

    /**
    * 批量设置角色给机构
    * @param roleIds角色ID数组
    * @param orgIds机构ID数组
    */
    public void setRolesToOrgs(String[] roleIds, String[] orgIds);

    /**
    * 批量回收指定角色与其授予的所有机构关系
    * @param roleIds角色ID数组
    */
    public void revokeFromAllOrgs(String... roleIds);

    /**
    * 回收指定角色与其授予的指定机构关系
    * @param roleId角色ID
    * @param orgId机构ID
    */
    public void revokeFromOrg(String roleId, String orgId);

    /**
    * 批量回收指定角色与其授予的指定机构关系
    * @param roleIds角色ID数组
    * @param orgIds机构ID数组
    */
    public void revokeFromOrgs(String[] roleIds, String[] orgIds);

    /**
    * 回收指定角色与其授予的指定机构下用户关系
    * @param roleId角色ID
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void revokeFromUser(String roleId, String userId, String orgId);

    /**
    * 批量回收指定角色与其授予的指定机构下用户关系
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param orgId机构ID
    */
    public void revokeFromUsers(String[] roleIds, String[] userIds, String orgId);

    /**
    * 判断角色是否已分配给机构下用户
    * @param roleId角色ID
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean isAssignedToUser(String roleId, String userId, String orgId);

    /**
    * 判断角色是否已分配给机构
    * @param roleId角色ID
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean isAssignedToOrg(String roleId, String orgId);

    /**
    * 判断指定角色是否用户的可管理角色
    * @param roleId角色ID
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isMgtPermitted(String roleId, String userId);

    /**
    * 获取超级管理员角色ID
    * @return 超级管理员角色ID
    */
    public String getAdministratorRoleId();

    /**
    * 获取超级管理员角色名称
    * @return 超级管理员角色名称
    */
    public String getAdministratorRoleName();

    /**
    * 获取每个用户都缺省拥有的角色ID
    * @return 每个用户都缺省拥有的角色ID
    */
    public String getRoleofeveryoneRoleId();

    /**
    * 获取每个用户都缺省拥有的角色名称
    * @return 每个用户都缺省拥有的角色名称
    */
    public String getRoleofeveryoneRoleName();

    /**
    * 获取机构管理员角色ID
    * @return 机构管理员角色ID
    */
    public String getOrgManagerRoleId();

    /**
    * 获取机构管理员角色名称
    * @return 机构管理员角色名称
    */
    public String getOrgManagerRoleName();

    /**
    * 新增角色
    * @param roleDTO角色数据传输对象
    */
    public void create(RoleDTO roleDTO);

    /**
    * 修改角色
    * @param roleDTO角色数据传输对象
    */
    public void update(RoleDTO roleDTO);

    /**
    * 删除角色
    * @param roleIds角色ID数组
    */
    public void deleteByPKs(String... roleIds);

    /**
    * 删除角色
    * @param roleName角色名
    */
    public void deleteByRoleName(String roleName);

    /**
    * 查询所有角色
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryAll();

    /**
    * 查询角色
    * @param roleDTO角色数据传输对象
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryByRole(RoleDTO roleDTO);

    /**
    * 查询角色
    * @param roleId角色ID
    * @return 角色数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public RoleDTO queryByPK(String roleId);

    /**
    * 查询角色
    * @param roleName角色名
    * @return 角色数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public RoleDTO queryByRoleName(String roleName);

    /**
    * 查询拥有特定角色的所有用户
    * @param roleId角色ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String roleId);

    /**
    * 查询拥有特定角色的所有用户
    * @param roleId角色ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String roleId, int scopeType, String scopeId);

    /**
    * 查询拥有特定角色的所有用户组
    * @param roleId角色ID
    * @return 用户组数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<GroupDTO> queryGroups(String roleId);

    /**
    * 查询角色所拥有的权限
    * @param roleId角色ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryPrivileges(String roleId);

    /**
    * 授予角色给用户
    * @param roleId角色ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void assignToUser(String roleId, String userId, int scopeType,
        String scopeId);

    /**
    * 批量授予角色给用户
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void assignToUsers(String[] roleIds, String[] userIds,
        int scopeType, String scopeId);

    /**
    * 设置角色给用户
    * @param roleId角色ID
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setToUser(String roleId, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 批量设置角色给用户
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setToUsers(String[] roleIds, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 批量回收指定角色与其授予的所有用户关系
    * @param roleIds角色ID数组
    */
    public void revokeFromAllUsers(String... roleIds);

    /**
    * 回收指定角色与其授予的指定用户关系
    * @param roleId角色ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void revokeFromUser(String roleId, String userId, int scopeType,
        String scopeId);

    /**
    * 批量回收指定角色与其授予的指定用户关系
    * @param roleIds角色ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void revokeFromUsers(String[] roleIds, String[] userIds,
        int scopeType, String scopeId);

    /**
    * 添加角色给用户组
    * @param roleId角色ID
    * @param groupId用户组ID
    */
    public void assingToGroup(String roleId, String groupId);

    /**
    * 批量添加角色给用户组
    * @param roleIds角色ID数组
    * @param groupIds用户组ID数组
    */
    public void assingToGroups(String[] roleIds, String[] groupIds);

    /**
    * 设置角色给用户组
    * @param roleId角色ID
    * @param groupIds用户组ID数组
    */
    public void setToGroups(String roleId, String[] groupIds);

    /**
    * 批量设置角色给用户组
    * @param roleIds角色ID数组
    * @param groupIds用户组ID数组
    */
    public void setRolesToGroups(String[] roleIds, String[] groupIds);

    /**
    * 批量回收指定角色与其授予的所有用户组关系
    * @param roleIds角色ID数组
    */
    public void revokeFromAllGroups(String... roleIds);

    /**
    * 回收指定角色与其授予的指定用户组关系
    * @param roleId角色ID
    * @param groupId用户组ID
    */
    public void revokeFromGroup(String roleId, String groupId);

    /**
    * 批量回收指定角色与其授予的指定用户组关系
    * @param roleIds角色ID数组
    * @param groupIds用户组ID数组
    */
    public void revokeFromGroups(String[] roleIds, String[] groupIds);

    /**
    * 添加角色的权限
    * @param roleId角色ID
    * @param privilegeId权限ID
    */
    public void assignPrivilege(String roleId, String privilegeId);

    /**
    * 批量添加角色的权限
    * @param roleIds角色ID数组
    * @param privilegeIds权限ID数组
    */
    public void assignPrivileges(String[] roleIds, String[] privilegeIds);

    /**
    * 设置角色的权限
    * @param roleId角色ID
    * @param privilegeIds权限ID数组
    */
    public void setPrivileges(String roleId, String[] privilegeIds);

    /**
    * 批量设置角色的权限
    * @param roleIds角色ID数组
    * @param privilegeIds权限ID数组
    */
    public void setPrivilegesToRoles(String[] roleIds, String[] privilegeIds);

    /**
    * 回收角色的所有权限
    * @param roleIds角色ID数组
    */
    public void revokeAllPrivileges(String... roleIds);

    /**
    * 回收角色的权限
    * @param roleId角色ID
    * @param privilegeId权限ID
    */
    public void revokePrivilege(String roleId, String privilegeId);

    /**
    * 批量回收角色的权限
    * @param roleIds角色ID数组
    * @param privilegeIds权限ID数组
    */
    public void revokePrivileges(String[] roleIds, String[] privilegeIds);

    /**
    * 判断角色有效状态
    * @param roleId角色ID
    * @return true:有效，false:无效
    */
    public boolean isEnabledByPK(String roleId);

    /**
    * 判断角色有效状态
    * @param roleName角色名
    * @return true:有效，false:无效
    */
    public boolean isEnabledByRoleName(String roleName);

    /**
    * 判断角色名称是否存在
    * @param roleName角色名称
    * @return true:存在，false:不存在
    */
    public boolean existsByRoleName(String roleName);

    /**
    * 判断角色名称是否存在(忽略指定角色ID名称，编辑时用)
    * @param roleName角色名称
    * @param roleId角色ID
    * @return true:存在，false:不存在
    */
    public boolean existsByRoleNameIgnoreRoleID(String roleName, String roleId);

    /**
    * 判断角色是否已分配给用户
    * @param roleId角色ID
    * @param userId用户ID
    * @return true:有，false:无
    */
    public boolean isAssingedToUser(String roleId, String userId);

    /**
    * 判断角色是否已分配给用户
    * @param roleId角色ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean isAssingedToUser(String roleId, String userId,
        int scopeType, String scopeId);

    /**
    * 判断角色是否分配给用户组
    * @param roleId角色ID
    * @param groupId用户组ID
    * @return true:有，false:无
    */
    public boolean isAssignedToGroup(String roleId, String groupId);

    /**
    * 判断角色是否拥有指定权限
    * @param roleId角色ID
    * @param privilegeId权限ID
    * @return true:有，false:无
    */
    public boolean hasPrivilege(String roleId, String privilegeId);
}
