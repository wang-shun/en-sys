package com.chinacreator.asp.comp.sys.core.group.eo;

import java.io.Serializable;

/**
 * 用户组与角色关系数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class GroupRoleEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户组ID */
    private String groupId;
    /** 角色ID */
    private String roleId;
    /** 授予角色的用户ID */
    private String resopOriginUserid;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = null == groupId ? null : groupId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = null == roleId ? null : roleId.trim();
    }

    public String getResopOriginUserid() {
        return resopOriginUserid;
    }

    public void setResopOriginUserid(String resopOriginUserid) {
        this.resopOriginUserid = null == resopOriginUserid ? null
                : resopOriginUserid.trim();
    }
}
