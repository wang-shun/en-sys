package com.chinacreator.asp.comp.sys.core.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.group.eo.GroupRoleEO;

/**
 * 用户组与角色关系数据访问接口
 * 
 * @author 蒋海杰
 * 
 */
@Repository
public interface GroupRoleDao {

    /**
     * 新增用户组与角色关系
     * 
     * @param groupRoleEO
     *            用户组与角色关系数据传输对象
     * @return 数据库执行操作影响到的行数
     */
    public int create(GroupRoleEO groupRoleEO);

    /**
     * 批量新增用户组与角色关系
     * 
     * @param listGroupRoleEO
     *            用户组与角色关系数据传输对象列表
     * @return 数据库执行操作影响到的行数
     */
    public int createBatch(List<GroupRoleEO> listGroupRoleEO);

    /**
     * 批量删除用户组与角色关系
     * 
     * @param groupIds
     *            用户组ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByGroupIds(String... groupIds);

    /**
     * 批量删除用户组与角色关系
     * 
     * @param roleIds
     *            角色ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByRoleIds(String... roleIds);
    
    /**
     * 删除用户组与角色关系
     * 
     * @param groupId
     *            用户组ID
     * @param roleId
     *            角色ID
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByGroupIdAndRoleId(@Param("groupId") String groupId,
            @Param("roleId") String roleId);

}
