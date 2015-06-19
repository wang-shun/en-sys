package com.chinacreator.asp.comp.sys.core.security.shiro.interfaces;

import java.util.Collection;

import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;

public interface IdentitifyInfomationFetcher {

    /**
     * 通过登录名，获取用户登录信息
     * 
     * @param loginName
     *            登录名
     * @return 用户登录信息
     * @throws Exception
     */
    SimpleUser findBy(String loginName) throws Exception;

    /**
     * 通过用户ID，获取用户所有的角色列表
     * 
     * @param userId
     *            用户ID
     * @return 角色列表
     */
    Collection<String> getUserRoles(String userId);

    /**
     * 通过用户ID，获取用户所有的权限字符串列表
     * 
     * @param userId
     *            用户ID
     * @return 权限字符串列表
     */
    Collection<String> getPermExprs(String userId);
}
