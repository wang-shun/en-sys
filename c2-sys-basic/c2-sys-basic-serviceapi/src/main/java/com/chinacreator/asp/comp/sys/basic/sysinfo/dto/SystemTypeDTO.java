package com.chinacreator.asp.comp.sys.basic.sysinfo.dto;

import java.io.Serializable;

/**
 * 系统信息类型数据传输对象类
 * 
 * @author 彭盛
 * 
 */
public class SystemTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统信息类型ID */
    private String id;
    /** 系统信息类型名称 */
    private String typeName;
    /** 系统信息类型描述 */
    private String typeDesc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = null == id ? null : id.trim();
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
}