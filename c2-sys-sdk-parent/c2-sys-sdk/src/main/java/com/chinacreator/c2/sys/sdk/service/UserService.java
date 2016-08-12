package com.chinacreator.c2.sys.sdk.service;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.validation.constraints.NotNull;


/**
 * 用户服务接口
 * 只能查出授权给应用的机构的用户数据，如果在新增、修改、删除此外的用户，将会抛出没有没有权限的异常{@link com.chinacreator.c2.sys.sdk.exception.SysResourceUnauthorizedException}
 *
 * @author 彭盛
 */
public interface UserService {
    /**
     * 在指定机构下新增用户
     *
     * @param user用户数据传输对象
     * @param orgId机构ID
     * @param sn用户在机构下的排序号
     */
	@ApiOperation(value="创建机构，有XXX限制")
    public String create(@NotNull(message="user.NotNull.message") User user,@NotNull(message="org.id.NotNull.message") String orgId) throws SysResourcesException;

    /**
     * 添加用户的所属机构，如需设置主机构请使用{@link #setMainOrg(String[], String, boolean)}
     *
     * @param userId 用户ID
     * @param orgId 机构ID
     */
    public void addOrg(@NotNull(message="user.id.NotNull.message") String userId, @NotNull(message="org.id.NotNull.message") String orgId)
        throws SysResourcesException;

    /**
     * 删除用户及用户所有相关的关联关系
     *
     * @param userId 用户ID
     */
    public void delete(@NotNull String... userId) throws SysResourcesException;

    /**
     * 删除用户和机构的关联关系，主机构不能删
     *
     * @param userId
     * @param orgId
     */
    public void deleteUserOrgRelationship(@NotNull(message="user.id.NotNull.message") String userId, @NotNull(message="org.id.NotNull.message") String orgId)
        throws SysResourcesException;

    /**
     * 修改用户
     *
     * @param user 用户数据传输对象
     */
    public void update(@NotNull(message="user.NotNull.message") User user) throws SysResourcesException;

    /**
     * 用户设置主机构
     *
     * @param userIds 用户ID数组
     * @param orgId 主机构ID
     * @param isRetain 用户是否保留在原机构下(true:是，false:否)
     */
    public void setMainOrg(@NotNull(message="user.id.NotNull.message") String[] userIds,@NotNull(message="org.id.NotNull.message") String orgId, boolean isRetain)
        throws SysResourcesException;

    /**
     * 查询指定机构下用户
     *
     * @param user 用户数据传输对象
     * @param orgId 机构ID
     * @return 用户数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryByOrg(User user, String orgId, boolean cascade);

    /**
     * 查询指定机构及角色下用户
     *
     * @param user 用户数据传输对象
     * @param roleId 角色ID
     * @return 用户数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryByRole(User user, String roleId);

    /**
     * 查询指定用户所属机构
     *
     * @param userId 用户ID
     * @return 机构数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Organization> getOrgs(@NotNull(message="user.id.NotNull.message") String userId);

    /**
     * 查询指定用户所属主机构
     *
     * @param userId 用户ID
     * @return 机构数据传输对象<br>
     *         没查询到的情况下返回null
     */
    public Organization getMainOrg(@NotNull(message="user.id.NotNull.message") String userId);

    /**
     * 判断用户是否属于指定机构
     *
     * @param userId 用户ID
     * @param orgId 机构ID
     * @return true:是，false:否
     */
    public boolean inOrg(@NotNull(message="user.id.NotNull.message") String userId,@NotNull(message="org.id.NotNull.message") String orgId);

    /**
     * 判断指定机构是否用户的主机构
     *
     * @param userId 用户ID
     * @param orgId 机构ID
     * @return true:是，false:否
     */
    public boolean isMainOrg(@NotNull(message="user.id.NotNull.message") String userId,@NotNull(message="org.id.NotNull.message") String orgId);

    /**
     * 查询用户
     *
     * @param user用户数据传输对象，null查所有
     * @return 用户数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> query(User user);

    /**
     * 查询用户， 查询到多个只返回第一个
     *
     * @param userId 用户ID
     * @return 用户数据传输对象<br>
     */
    public User get(@NotNull(message="user.NotNull.message") User user);

    /**
     * 按用户ID查询用户
     *
     * @param userIds 用户ID
     * @return
     */
    public List<User> query(@NotNull(message="user.id.NotNull.message") String... userIds);

    /**
     * 查询指定用户所拥有的角色
     *
     * @param userId 用户ID
     * @return 角色数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Role> getRoles(@NotNull(message="user.id.NotNull.message") String userId);

    /**
     * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return true:有，false:无
     */
    public boolean hasRole(@NotNull(message="user.id.NotNull.message") String userId, String roleId);
}
