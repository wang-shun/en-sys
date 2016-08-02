package com.chinacreator.c2.sys.sdk.service;

import com.chinacreator.c2.sys.sdk.bean.Role;

import java.util.List;


/**
 * 角色服务接口
 * @author 彭盛
 */
public interface RoleService {
    /**
     * 查询角色列表
     * @param role 查询条件对象，null表示查所有的
     * @return 角色列表
     */
    public List<Role> query(Role role);
}
