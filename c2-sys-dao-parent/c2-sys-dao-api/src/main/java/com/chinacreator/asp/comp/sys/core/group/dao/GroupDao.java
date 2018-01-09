package com.chinacreator.asp.comp.sys.core.group.dao;

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
 * 用户组数据访问接口
 * 
 * @author 林伏山
 * 
 */
@Repository
public interface GroupDao {
    /**
     * 新增用户组
     * 
     * @param GroupEO
     *            用户组数据传输对象
     * @return 数据库执行操作影响到的行数
     */
    public int create(GroupEO GroupEO);

    /**
     * 修改用户组
     * 
     * @param GroupEO
     *            用户组数据传输对象
     * @return 数据库执行操作影响到的行数
     */
    public int update(GroupEO GroupEO);

    /**
     * 批量删除用户组
     * 
     * @param groupIds
     *            用户组ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByPKs(String... groupIds);

    /**
     * 查询所有用户组
     * 
     * @return 用户组数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<GroupEO> queryAll();

    /**
     * 查询用户组
     * 
     * @param GroupEO
     *            用户组数据传输对象
     * @return 用户组数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<GroupEO> queryByGroup(GroupEO GroupEO);

    /**
     * 查询用户组
     * 
     * @param groupId
     *            用户组ID
     * @return 用户组数据传输对象<br>
     *         没查询到的情况下返回null
     */
    public GroupEO queryByPK(String groupId);

    /**
     * 查询用户组下的用户
     * 
     * @param groupId
     *            用户组ID
     * @return 用户数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserEO> queryUsers(String groupId);

    /**
     * 查询用户组下的用户
     * 
     * @param groupId
     *            用户组ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserEO> queryUsersByScope(@Param("groupId") String groupId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);
    
    /**
     * 查询用户组下的用户(带分页)
     * 
     * @param groupId
     *            用户组ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @param pageable 
     *            分页条件
     * @param sortable
     *            排序条件
     * @return 分也后的用户数据传输对象列表
     */
    public Page<UserEO> queryUsersByScope(@Param("groupId") String groupId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId,
            Pageable pageable,
            Sortable sortable);

    /**
     * 查询用户组所拥有的角色
     * 
     * @param groupId
     *            用户组ID
     * @return 角色数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<RoleEO> queryRoles(@Param("groupId")String groupId);
    
    /**
     * 查询用户组所拥有的角色
     * 
     * @param groupId
     *            用户组ID
     * @param pageable
     *            分页条件
     * @param sortable
     *            排序条件
     * @return 分页后的角色数据传输对象列表
     */
    public Page<RoleEO> queryRoles(@Param("groupId")String groupId, Pageable pageable, Sortable sortable);

    /**
     * 查询用户组所拥有的权限
     * 
     * @param groupId
     *            用户组ID
     * @return 权限数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<PrivilegeEO> queryPrivileges(String groupId);

    /**
     * 判断用户组下是否有指定用户
     * 
     * @param groupId
     *            用户组ID
     * @param userId
     *            用户ID
     * @return 大于0:有，等于0:无
     */
    public int containsUser(@Param("groupId") String groupId,
            @Param("userId") String userId);

    /**
     * 判断用户组下是否有指定用户
     * 
     * @param groupId
     *            用户组ID
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:有，等于0:无
     */
    public int containsUserByScope(@Param("groupId") String groupId,
            @Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询用户组
     * 
     * @param groupIds
     *            用户组ID数组
     * @return 用户组数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<GroupEO> queryByGroupIds(String... groupIds);
}
