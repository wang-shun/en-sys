package com.chinacreator.asp.comp.sys.sdk.sysinfo.dto;

import java.io.Serializable;


/**
 * 系统信息数据传输对象类
 * @author 彭盛
 */
public class SystemInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 系统信息ID
    */
    private String id;

    /**
    * 系统信息名称
    */
    private String infoName;

    /**
    * 系统信息内容
    */
    private String infoContent;

    /**
    * 系统信息描述
    */
    private String infoDesc;

    /**
    * 系统信息类型
    */
    private String infoType;

    /**
    * 是否可以删除字段，false:不可删除, true:可删除
    */
    private Boolean canremove;

    /**
    * 备用字段
    */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (null == id) ? null : id.trim();
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = (null == infoName) ? null : infoName.trim();
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = (null == infoContent) ? null : infoContent.trim();
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = (null == infoDesc) ? null : infoDesc.trim();
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = (null == infoType) ? null : infoType.trim();
    }

    public Boolean getCanremove() {
        return canremove;
    }

    public void setCanremove(Boolean canremove) {
        this.canremove = (null == canremove) ? null : canremove;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = (null == remark) ? null : remark.trim();
    }
}
