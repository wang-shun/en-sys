package com.chinacreator.c2.sys.sdk.service;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;


/**
 * 用户服务接口
 * @author 彭盛
 */
public interface UserService {

    /**
    * 在指定机构下新增用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @param sn用户在机构下的排序号
    */
    public String create(User userDto, String orgId);


    /**
     * 用户设置主机构
     * 
     * @param userIds
     *            用户ID数组
     * @param orgId
     *            主机构ID
     * @param isRetain
     *            用户是否保留在原机构下(true:是，false:否)
     */
    public void setMainOrg(String[] userIds, String orgId, boolean isRetain);

    /**
    * 删除用户及用户所有相关的关联关系
    * @param userId 用户ID
    */
    public void delete(String... userId);
    
    /**
     * 删除用户和机构的关联关系，主机构不能删
     * @param userId
     * @param orgId
     */
    public void deleteUserOrgRelationship(String userId,String orgId);
    
    /**
    * 查询指定机构下用户
    * @param userDto用户数据传输对象
    * @param orgId机构ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<User> queryByOrg(User userDto, String orgId,boolean cascade);

    /**
    * 查询指定机构及角色下用户
    * @param userDto用户数据传输对象
    * @param roleId角色ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<User> queryByRole(User userDto,String roleId);


    /**
    * 查询指定用户所属机构
    * @param userId用户ID
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    //TODO queryXXX --> getXXX
    public List<Orgnization> getOrgs(String userId);

    /**
    * 查询指定用户所属主机构
    * @param userId用户ID
    * @return 机构数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public Orgnization getMainOrg(String userId);

    /**
    * 添加用户的所属机构，如需设置主机构请使用{@link #setMainOrg(String[], String, boolean)}
    *  
    * @param userId用户ID
    * @param orgId机构ID
    */
    public void addToOrg(String userId, String orgId);

    /**
    * 判断用户是否属于指定机构
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean inOrg(String userId, String orgId);

    /**
    * 判断指定机构是否用户的主机构
    * @param userId用户ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean isMainOrg(String userId, String orgId);

    /**
    * 修改用户
    * @param userDto用户数据传输对象
    */
    public void update(User userDto);

    /**
    * 查询用户
    * @param userDto用户数据传输对象，null查所有
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<User> query(User userDto);

    /**
    * 查询用户
    * @param userId用户ID
    * @return 用户数据传输对象<br>
    * 查询到多个抛异常
    */
    public User get(User userDto);
    
    public List<User> query(String... userIds);

    /**
    * 查询指定用户所拥有的角色
    * @param userId用户ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<Role> getRoles(String userId);


    /**
    * 判断用户是否拥有指定角色（包括直接授予用户的角色和授予用户所属用户组的角色）
    * @param userId用户ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasRole(String userId, String roleId);
}
