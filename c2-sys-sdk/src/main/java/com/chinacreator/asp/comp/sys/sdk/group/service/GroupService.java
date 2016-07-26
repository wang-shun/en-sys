package com.chinacreator.asp.comp.sys.sdk.group.service;

import com.chinacreator.asp.comp.sys.sdk.group.dto.GroupDTO;
import com.chinacreator.asp.comp.sys.sdk.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import java.util.List;


/**
 * 用户组服务接口
 * @author 彭盛
 */
public interface GroupService {
    /**
    * 新增用户组
    * @param groupDTO用户组数据传输对象
    */
    public void create(GroupDTO groupDTO);

    /**
    * 修改用户组
    * @param groupDTO用户组数据传输对象
    */
    public void update(GroupDTO groupDTO);

    /**
    * 删除用户组
    * @param groupIds用户组ID数组
    */
    public void deleteByPKs(String... groupIds);

    /**
    * 查询所有用户组
    * @return 用户组数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<GroupDTO> queryAll();

    /**
    * 查询用户组
    * @param groupDTO用户组数据传输对象
    * @return 用户组数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<GroupDTO> queryByGroup(GroupDTO groupDTO);

    /**
    * 查询用户组
    * @param groupId用户组ID
    * @return 用户组数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public GroupDTO queryByPK(String groupId);

    /**
    * 查询用户组下的用户
    * @param groupId用户组ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String groupId);

    /**
    * 查询用户组下的用户
    * @param groupId用户组ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String groupId, int scopeType,
        String scopeId);

    /**
    * 查询用户组所拥有的角色
    * @param groupId用户组ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String groupId);

    /**
    * 查询用户组所拥有的权限
    * @param groupId用户组ID
    * @return 权限数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<PrivilegeDTO> queryPrivileges(String groupId);

    /**
    * 添加用户给用户组
    * @param groupId用户组ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void addUser(String groupId, String userId, int scopeType,
        String scopeId);

    /**
    * 批量添加用户给用户组
    * @param groupIds用户组ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void addUsers(String[] groupIds, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 设置用户给用户组
    * @param groupId用户组ID
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setUser(String groupId, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 批量设置用户给用户组
    * @param groupIds用户组ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void setUsers(String[] groupIds, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 批量回收用户组下所有用户
    * @param groupIds用户组ID数组
    */
    public void removeAllUsers(String... groupIds);

    /**
    * 回收用户组下指定用户
    * @param groupId用户组ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void removeUser(String groupId, String userId, int scopeType,
        String scopeId);

    /**
    * 批量回收用户组下指定用户
    * @param groupIds用户组ID数组
    * @param userIds用户ID数组
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    */
    public void removeUsers(String[] groupIds, String[] userIds, int scopeType,
        String scopeId);

    /**
    * 添加用户组拥有的角色
    * @param groupId用户组ID
    * @param roleId角色ID
    */
    public void assignRole(String groupId, String roleId);

    /**
    * 批量添加用户组拥有的角色
    * @param groupIds用户组ID数组
    * @param roleIds角色ID数组
    */
    public void assignRoles(String[] groupIds, String[] roleIds);

    /**
    * 设置用户组拥有的角色
    * @param groupId用户组ID
    * @param roleIds角色ID数组
    */
    public void setRoles(String groupId, String[] roleIds);

    /**
    * 批量设置用户组拥有的角色
    * @param groupIds用户组ID数组
    * @param roleIds角色ID数组
    */
    public void setRolesToGroups(String[] groupIds, String[] roleIds);

    /**
    * 批量回收用户组拥有的所有角色关系
    * @param groupIds用户组数组
    */
    public void revokeAllRoles(String... groupIds);

    /**
    * 回收用户组拥有的角色关系
    * @param groupId用户组ID
    * @param roleId角色ID
    */
    public void revokeRole(String groupId, String roleId);

    /**
    * 批量回收用户组拥有的角色关系
    * @param groupIds用户组ID数组
    * @param roleIds角色ID数组
    */
    public void revokeRoles(String[] groupIds, String[] roleIds);

    /**
    * 判断用户组下是否有指定用户
    * @param groupId用户组ID
    * @param userId用户ID
    * @return true:有，false:无
    */
    public boolean containsUser(String groupId, String userId);

    /**
    * 判断用户组下是否有指定用户
    * @param groupId用户组ID
    * @param userId用户ID
    * @param scopeType用户活动范围类型（0：全局，1：机构，2：岗位）
    * @param scopeId用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
    * 该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
    * @return true:有，false:无
    */
    public boolean containsUser(String groupId, String userId, int scopeType,
        String scopeId);

    /**
    * 判断用户组是否拥有指定角色
    * @param groupId用户组ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasRole(String groupId, String roleId);
}
