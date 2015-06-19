package com.chinacreator.asp.comp.sys.core.user.eo;

import java.io.Serializable;

/**
 * 用户实例与角色关系数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class UserInstanceRoleEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户实例ID */
    private String userInstanceId;
    /** 角色ID */
    private String roleId;

    public String getUserInstanceId() {
        return userInstanceId;
    }

    public void setUserInstanceId(String userInstanceId) {
        this.userInstanceId = null == userInstanceId ? null : userInstanceId
                .trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = null == roleId ? null : roleId.trim();
    }
}
