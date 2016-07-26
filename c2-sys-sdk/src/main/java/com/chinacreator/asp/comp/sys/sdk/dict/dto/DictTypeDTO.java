package com.chinacreator.asp.comp.sys.sdk.dict.dto;

import java.io.Serializable;

import java.util.List;


/**
 * 字典类型数据传输对象类
 * @author 彭盛
 */
public class DictTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 字典类型ID
    */
    private String dicttypeId;

    /**
    * 字典类型名称
    */
    private String dicttypeName;

    /**
    * 字典类型描述
    */
    private String dicttypeDesc;

    public String getDicttypeId() {
        return dicttypeId;
    }

    public void setDicttypeId(String dicttypeId) {
        this.dicttypeId = (null == dicttypeId) ? null : dicttypeId.trim();
    }

    public String getDicttypeName() {
        return dicttypeName;
    }

    public void setDicttypeName(String dicttypeName) {
        this.dicttypeName = (null == dicttypeName) ? null : dicttypeName.trim();
    }

    public String getDicttypeDesc() {
        return dicttypeDesc;
    }

    public void setDicttypeDesc(String dicttypeDesc) {
        this.dicttypeDesc = (null == dicttypeDesc) ? null : dicttypeDesc.trim();
    }
}
