package com.chinacreator.c2.sys.sdk.service.query;

import com.chinacreator.c2.sys.sdk.bean.Role;


/**
 * 角色查询接口
 * @author 彭盛
 */
public interface RoleService {
    /**
     * 查询角色列表
     * @param role 查询条件对象，null表示查所有的
     * @return 角色列表
     */
    public Role get(String id);
    
    /**
     * 查询角色列表
     * @param role 查询条件对象，null表示查所有的
     * @return 角色列表
     */
    public Role getByName(String roleName);
}
