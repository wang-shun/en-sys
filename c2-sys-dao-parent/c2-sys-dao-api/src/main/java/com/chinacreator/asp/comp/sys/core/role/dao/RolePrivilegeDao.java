package com.chinacreator.asp.comp.sys.core.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.role.eo.RolePrivilegeEO;

/**
 * 角色与权限关系数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface RolePrivilegeDao {

    /**
     * 添加角色与权限关系
     * 
     * @param rolePrivilegeEO
     *            角色与权限关系数据访问对象
     * @return 数据库执行操作影响到的行数
     */
    public int create(RolePrivilegeEO rolePrivilegeEO);

    /**
     * 批量添加角色与权限关系
     * 
     * @param rolePrivilegeEO
     *            角色与权限关系数据访问对象
     * @return 数据库执行操作影响到的行数
     */
    public int createBatch(List<RolePrivilegeEO> rolePrivilegeEOs);

    /**
     * 修改角色与权限关系信息
     * 
     * @param rolePrivilegeEO
     *            角色与权限关系数据访问对象
     * @return 数据库执行操作影响到的行数
     */
    public int update(RolePrivilegeEO rolePrivilegeEO);

    /**
     * 批量删除角色与权限关系
     * 
     * @param roleIds
     *            角色ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByRoles(String... roleIds);
    
    /**
     * 批量删除角色与菜单权限的关系
     * @param roleIds 角色ID数组
     * @return 影响的行数
     */
    public int deleteMenuByRoles(String... roleIds);

    /**
     * 删除角色与权限关系(批量)
     * 
     * @param privilegeIds
     *            权限ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByPrivileges(String... privilegeIds);

    /**
     * 删除角色与权限关系
     * 
     * @param privilegeIds
     *            权限ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByPrivilege(String privilegeId);

    /**
     * 删除角色与权限关系
     * 
     * @param privilegeId
     *            权限ID
     * @param roleId
     *            角色ID
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByPrivilegeIdAndRoleId(
            @Param("privilegeId") String privilegeId,
            @Param("roleId") String roleId);

}
