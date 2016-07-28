package com.chinacreator.c2.sys.sdk.service;

import com.chinacreator.c2.sys.sdk.bean.Role;

import java.util.List;


/**
 * 角色服务接口
 * @author 彭盛
 */
public interface RoleSevice {
    /**
     * 查询所有的角色
     * @return 角色列表
     */
    public List<Role> query();
}
