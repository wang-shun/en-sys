package com.chinacreator.asp.comp.sys.sdk.role.dto;

import java.io.Serializable;


/**
 * 角色数据传输对象类
 * @author 彭盛
 */
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 角色ID
    */
    private String roleId;

    /**
    * 角色名
    */
    private String roleName;

    /**
    * 是否使用 true:使用,false:不使用
    */
    private Boolean roleUsage;

    /**
    * 角色类型
    */
    private String roleType;

    /**
    * 角色描述
    */
    private String roleDesc;

    /**
    * 创建人id
    */
    private String ownerId;

    /**
    * 备用字段1
    */
    private String remark1;

    /**
    * 备用字段2
    */
    private String remark2;

    /**
    * 备用字段3
    */
    private String remark3;

    /**
    * 备用字段4
    */
    private String remark4;

    /**
    * 备用字段5
    */
    private String remark5;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = (null == roleId) ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = (null == roleName) ? null : roleName.trim();
    }

    public Boolean getRoleUsage() {
        return roleUsage;
    }

    public void setRoleUsage(Boolean roleUsage) {
        this.roleUsage = (null == roleUsage) ? null : roleUsage;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = (null == roleType) ? null : roleType.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = (null == roleDesc) ? null : roleDesc.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = (null == ownerId) ? null : ownerId.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = (null == remark1) ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = (null == remark2) ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = (null == remark3) ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = (null == remark4) ? null : remark4.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = (null == remark5) ? null : remark5.trim();
    }
}
