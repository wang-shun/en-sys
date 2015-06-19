package com.chinacreator.asp.comp.sys.core.user.eo;

import java.io.Serializable;

/**
 * 用户实例数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class UserInstanceEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户实例ID */
    private String id;
    /** 用户ID */
    private String userId;
    /** 范围ID */
    private String scopeId;
    /** 范围类型（0：全局，1：机构，2：岗位） */
    private String scopeType;
    /** 是否有效（false：否，true：是） */
    private Boolean isEnabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = null == id ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = null == userId ? null : userId.trim();
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = null == scopeId ? null : scopeId.trim();
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = null == scopeType ? null : scopeType.trim();
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = null == isEnabled ? null : isEnabled;
    }
}
