package com.chinacreator.asp.comp.sys.core.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 用户实例数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface UserInstanceDao {

    /**
     * 新增用户实例
     * 
     * @param userInstanceEO
     *            用户实例数据访问对象
     * @return 数据库执行操作影响到的行数
     * 
     */
    public int create(UserInstanceEO userInstanceEO);

    /**
     * 修改用户实例
     * 
     * @param userInstanceEO
     *            用户实例数据访问对象
     * @return 数据库执行操作影响到的行数
     */
    public int update(UserInstanceEO userInstanceEO);

    /**
     * 批量删除用户实例
     * 
     * @param ids
     *            用户实例ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByPKs(String... ids);

    /**
     * 批量删除用户实例
     * 
     * @param userIds
     *            用户ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByUserIds(String... userIds);

    /**
     * 删除用户实例
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByUserIdAndScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询所有用户实例
     * 
     * @return 所有用户实例<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserInstanceEO> queryAll();

    /**
     * 查询用户实例
     * 
     * @param userInstanceEO
     *            用户实例数据访问对象
     * @return 用户实例数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserInstanceEO> queryByUserInstance(
            UserInstanceEO userInstanceEO);

    /**
     * 查询用户实例
     * 
     * @param id
     *            用户实例id
     * @return 用户实例数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public UserInstanceEO queryByPK(String id);

    /**
     * 查询用户实例
     * 
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserInstanceEO> queryByScope(
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询用户实例
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户实例数据访问对象
     */
    public UserInstanceEO queryByUserAndScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询用户
     * 
     * @param id
     *            用户实例id
     * @return 用户数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserEO> queryUsersByPK(String id);

    /**
     * 查询指定用户活动范围下的所有用户
     * 
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<UserEO> queryUsersByScope(@Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询指定用户所拥有的直接角色
     * 
     * @param userId
     *            用户ID
     * @return 角色数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<RoleEO> queryDirectRolesByUserId(@Param("userId")String userId);
    
    /**
     * 查询指定用户所拥有的直接角色
     * 
     * @param userId
     *            用户ID
     * @param pageable
     *            分页参数
     * @param sortable
     *            排序参数
     * @return 分页后的角色数据访问对象列表
     */
    public Page<RoleEO> queryDirectRolesByUserId(@Param("userId")String userId, Pageable pageable, Sortable sortable);

    /**
     * 查询指定用户所拥有的直接角色
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 角色数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<RoleEO> queryDirectRolesByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);
    
    /**
     * 查询指定用户所拥有的直接角色
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @param pageable
     *            分页参数
     * @param sortable
     *            排序参数
     * @return 角色数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public Page<RoleEO> queryDirectRolesByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId,
            Pageable pageable,
            Sortable sortable);

    /**
     * 查询指定用户所拥有的角色
     * 
     * @param userId
     *            用户ID
     * @return 角色数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<RoleEO> queryRolesByUserId(@Param("userId")String userId);
    
    /**
     * 查询指定用户所拥有的角色（带分页）
     * 
     * @param userId
     *            用户ID
     * @param pageable 
     *            分页条件
     * @param sortable
     *            排序条件
     * @return 分页后的角色数据访问对象列表
     *         
     */
    public Page<RoleEO> queryRolesByUserId(@Param("userId")String userId, Pageable pageable,
            Sortable sortable);

    /**
     * 查询指定用户所拥有的角色
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 角色数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<RoleEO> queryRolesByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);
    
    /**
     * 查询指定用户所拥有的角色
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @param pageable
     *            分页参数
     * @param sortable
     *            排序参数
     * @return 角色数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public Page<RoleEO> queryRolesByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId,
            Pageable pageable,
            Sortable sortable);

    /**
     * 查询用户所属用户组
     * 
     * @param userId
     *            用户ID
     * @return 用户组数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<GroupEO> queryGroupsByUserId(String userId);

    /**
     * 查询用户所属用户组
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户组数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<GroupEO> queryGroupsByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 查询用户拥有的权限
     * 
     * @param userId
     *            用户ID
     * @return 权限数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<PrivilegeEO> queryPrivilegesByUserId(@Param("userId")String userId);
    
    /**
     * 查询用户拥有的权限（带分页）
     * 
     * @param userId
     *            用户ID
     * @param pageable
     *            分页条件
     * @param sortable
     *            排序条件
     * @return 分页后的权限数据访问对象列表
     *         
     */
    public Page<PrivilegeEO> queryPrivilegesByUserId(@Param("userId")String userId, Pageable pageable, Sortable sortable);

    /**
     * 查询用户拥有的权限
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 权限数据访问对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<PrivilegeEO> queryPrivilegesByScope(
            @Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);
    
    /**
     * 查询用户拥有的权限（带分页）
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @param pageable 
     *            分页条件
     * @param sortable
     *            排序条件           
     * @return 分页后的权限数据访问对象列表
     */
    public Page<PrivilegeEO> queryPrivilegesByScope(
            @Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId,
            Pageable pageable,
            Sortable sortable);

    /**
     * 设置用户在指定活动范围内的有效状态
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @param enabled
     *            是否有效（true:有效，false:无效）
     */
    public void setEnabledByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId, @Param("enabled") String enabled);

    /**
     * 判断用户是否拥有直接授予的指定角色
     * 
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @return 大于0:有，0:无
     */
    public int hasRole(@Param("userId") String userId,
            @Param("roleId") String roleId);

    /**
     * 判断用户是否拥有直接授予的指定角色
     * 
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:有，0:无
     */
    public int hasRoleByScope(@Param("userId") String userId,
            @Param("roleId") String roleId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 判断用户是否拥有指定角色
     * 
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @return 大于0:有，0:无
     */
    public int hasAllRole(@Param("userId") String userId,
            @Param("roleId") String roleId);

    /**
     * 判断用户是否拥有指定角色
     * 
     * @param userId
     *            用户ID
     * @param roleId
     *            角色ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:有，0:无
     */
    public int hasAllRoleByScope(@Param("userId") String userId,
            @Param("roleId") String roleId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 判断用户是否拥有指定权限
     * 
     * @param userId
     *            用户ID
     * @param privilegeId
     *            权限ID
     * @return 大于0:有，0:无
     */
    public int hasPrivilege(@Param("userId") String userId,
            @Param("privilegeId") String privilegeId);

    /**
     * 判断用户是否拥有指定权限
     * 
     * @param userId
     *            用户ID
     * @param privilegeId
     *            权限ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:有，0:无
     */
    public int hasPrivilegeByScope(@Param("userId") String userId,
            @Param("privilegeId") String privilegeId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 判断用户在指定活动范围内是否有效
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:有效，0:无效
     */
    public Integer isEnabledByScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);

    /**
     * 判断用户实例是否在指定用户组
     * 
     * @param userInstanceId
     *            用户实例ID
     * @param groupId
     *            用户组ID
     * @return 大于0:是，0:否
     */
    public int belongsToGroup(@Param("userInstanceId") String userInstanceId,
            @Param("groupId") String groupId);

    /**
     * 判断用户实例是否存在
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 大于0:存在，0:不存在
     */
    public int existsByUserIdAndScope(@Param("userId") String userId,
            @Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId);
    
    /**
     * 通过用户范围类型和用户范围ID数组查询所有满足条件的用户实例信息（此处不支持全局）
     * 
     * @param scopeType
     *            用户活动范围类型（1：机构，2：岗位）
     * @param scopeIds
     *            用户活动范围ID数组（当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户实例信息列表
     */
    public List<UserInstanceEO> queryByScopeTypeScopeIds(
            @Param("scopeType") String scopeType,
            @Param("scopeIds") String[] scopeIds);
    
    /**
     * 通过用户范围类型和用户范围ID查询满足用户ID的用户实例信息（此处不支持全局）
     * 
     * @param scopeType
     *            用户活动范围类型（1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为1时， 该用户活动范围ID为机构ID；当用户活动范围类型为2时，
     *            该用户活动范围ID为岗位ID；以此类推）
     * @param userIds
     *            用户ID数组
     * @return 用户实例信息列表
     */
    public List<UserInstanceEO> queryByScopeTypeScopeIdUserIds(@Param("scopeType") String scopeType,
            @Param("scopeId") String scopeId, @Param("userIds")String[] userIds);
    
    /**
     * 通过用户范围类型查询满足用户ID的用户实例信息（此处不支持全局）
     * 
     * @param scopeType
     *            用户活动范围类型（1：机构，2：岗位）
     * @param userIds
     *            用户ID数组
     * @return 用户实例信息列表
     */
    public List<UserInstanceEO> queryByScopeTypeUserIds(@Param("scopeType") String scopeType, @Param("userIds")String[] userIds);
    
    /**
     * 查询出用户实例所对应的用户的所有用户实例集合
     * @param userInsIds 用户实例ID数组
     * @return 用户实例对应的所有用户的用户实例集合
     */
    public List<String> queryAllUserInsIdsByUserInsIds(@Param("userInsIds")List<String> userInsIds);
}
