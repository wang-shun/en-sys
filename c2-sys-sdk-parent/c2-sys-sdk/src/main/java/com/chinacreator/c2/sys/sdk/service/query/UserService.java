package com.chinacreator.c2.sys.sdk.service.query;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sysmgr.User;


/**
 * 用户信息查询服务，要对用户信息进行操作请使用{@link UserManageService}
 *
 * @see UserManageService
 * @author 彭盛
 */
public interface UserService {
	/**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 指定用户，没有指定id的用户则返回null
     */
    public User get(String id);
    /**
     * 按用户ID查询用户
     *
     * @param ids 用户ID
     * @return 用戶列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryMulti(String... ids);
	/**
     * 通过用户名获取用户详情
     *
     * @param username 用户查询条件
     * @return 指定用户，没有指定id的用户则返回null
     */
    public User getByUsername(String username);
    /**
     * 按用户ID查询用户
     *
     * @param ids 用户ID
     * @return 用戶列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryMultiByUsername(String... username);
    /**
     * 查询指定机构下用户
     *
     * @param orgId 机构ID
     * @return 用戶列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryByOrg(String orgId, boolean cascade);

    /**
     * 查询指定机构及角色下用户
     *
     * @param roleId 角色ID
     * @return 用戶列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<User> queryByRole(String roleId);

    /**
     * 查询指定用户所属机构
     *
     * @param userId 用户ID
     * @return 机构数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Organization> getOrgs(String userId);

    /**
     * 查询指定用户所属主机构
     *
     * @param userId 用户ID
     * @return 机构数据传输对象<br>
     *         没查询到的情况下返回null
     */
    public Organization getMainOrg(String userId);

    /**
     * 判断用户是否属于指定机构
     *
     * @param userId 用户ID
     * @param orgId 机构ID
     * @return true:是，false:否
     */
    public boolean inOrg(String userId, String orgId);

    /**
     * 判断指定机构是否用户的主机构
     *
     * @param userId 用户ID
     * @param orgId 机构ID
     * @return true:是，false:否
     */
    public boolean isMainOrg(String userId, String orgId);

    /**
     * 查询指定用户所拥有的角色
     *
     * @param userId 用户ID
     * @return 角色数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Role> getRoles(String userId);

    /**
     * 判断用户是否拥有指定角色
     * 包括直接授予用户的角色和授予用户所属用户组的角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return true:有，false:无
     */
    public boolean hasRole(String userId, String roleId);
}
