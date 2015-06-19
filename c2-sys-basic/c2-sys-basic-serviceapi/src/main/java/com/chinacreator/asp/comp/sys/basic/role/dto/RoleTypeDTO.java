package com.chinacreator.asp.comp.sys.basic.role.dto;

import java.io.Serializable;

/**
 * 角色类型数据传输对象类
 * 
 * @author 彭盛
 * 
 */
public class RoleTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色类型ID */
    private String typeId;
    /** 角色类型名称 */
    private String typeName;
    /** 角色类型描述 */
    private String typeDesc;
    /** 创建人ID */
    private String creatorUserId;
    /** 创建人机构ID */
    private String creatorOrgId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = null == typeId ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = null == typeName ? null : typeName.trim();
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = null == typeDesc ? null : typeDesc.trim();
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = null == creatorUserId ? null : creatorUserId
                .trim();
    }

    public String getCreatorOrgId() {
        return creatorOrgId;
    }

    public void setCreatorOrgId(String creatorOrgId) {
        this.creatorOrgId = null == creatorOrgId ? null : creatorOrgId.trim();
    }
}