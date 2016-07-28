package com.chinacreator.c2.sys.sdk.service;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Role;
/**
 * 角色服务接口
 * @author 彭盛
 */
public interface RoleSevice {
    /**
     * 查询所有的角色
     * @return
     */
    public List<Role> query();
}
