package com.chinacreator.asp.comp.sys.sdk.security.service;

import com.chinacreator.asp.comp.sys.sdk.privilege.dto.PrivilegeDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import com.chinacreator.c2.sysmgr.AuthenticationToken;

import java.util.List;
import java.util.Map;


/**
 * 访问控制服务接口
 * @author 彭盛
 */
public interface AccessControlService {
    /**
    * 登录
    * @param userName用户帐号
    * @param password登录密码
    * @return true:成功，false:失败
    */
    public boolean login(String userName, String password);

    /**
    * 登录
    * @param token身份认证信息
    * @return true:成功，false:失败
    */
    public boolean login(AuthenticationToken token);

    /**
    * 登出
    * @return 返回跳转url，为null时刷新页面
    */
    public String logout();

    /**
    * 判断用户是否已通过身份认证
    * @return true:是，false:否
    */
    public boolean isAuthenticated();

    /**
    * 获取当前登录用户ID
    * @return 当前登录用户ID
    */
    public String getUserID();

    /**
    * 获取当前登录用户帐号
    * @return 当前登录用户帐号
    */
    public String getUserName();

    /**
    * 获取当前登录用户实名
    * @return 当前登录用户实名
    */
    public String getUserRealName();

    /**
    * 获取当前登录用户数据传输对象
    * @return 当前用户登录数据传输对象
    */
    public UserDTO getUser();

    /**
    * 获取当前登录用户拥有的角色
    * @return 当前登录用户拥有的角色数据传输对象列表
    */
    public List<RoleDTO> getRoles();

    /**
    * 获取当前登录用户拥有的权限
    * @return 当前登录用户拥有的权限数据传输对象列表
    */
    public List<PrivilegeDTO> getPrivileges();

    /**
    * 检测当前登录用户是否拥有指定权限
    * @param permExpr权限字符串
    * @return true:有，false:无
    */
    public boolean isPermitted(String permExpr);

    /**
    * 检测当前登录用户是否拥有指定权限
    * @param permExpr权限字符串数组
    * @return 按参数传入顺序，逐一返回验证结果数组（true:有，false:无）
    */
    public boolean[] isPermitted(String... permExpr);

    /**
    * 批量检测当前登录用户是否拥有指定权限
    * @param permExpr权限字符串数组
    * @return 按参数传入权限字符串为key，返回验证结果value（true:有，false:无）
    */
    public Map<String, Boolean> isPermittedByBatch(String... permExpr);

    /**
    * 批量检测当前登录用户是否拥有指定权限<br>
    * （多个权限全部验证通过才能返回true，任意有一个不通过返回false）
    * @param permExpr权限字符串数组
    * @return true:有，false:无
    */
    public boolean isAllPermitted(String... permExpr);

    /**
    * 检测当前登录用户是否拥有指定角色
    * @param roleName角色名
    * @return true:有，false:无
    */
    public boolean hasRole(String roleName);

    /**
    * 检测当前登录用户是否拥有指定角色
    * @param roleName角色名
    * @return 按参数传入顺序，逐一返回验证结果数组（true:有，false:无）
    */
    public boolean[] hasRoles(String... roleName);

    /**
    * 检测当前登录用户是否拥有指定角色 <br>
    * （多个角色全部验证通过才能返回true，任意有一个不通过返回false）
    * @param roleName角色名
    * @return true:有，false:无
    */
    public boolean hasAllRoles(String... roleName);

    /**
    * 检测当前登录用户是否超级管理员
    * @return true:是，false:否
    */
    public boolean isAdmin();

    /**
    * 清除当前登录用户的权限缓存
    */
    public void cleanPermittedCache();
}
