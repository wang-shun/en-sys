package com.chinacreator.c2.sys.sdk.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 机构数据传输对象类
 * 
 * @author 彭盛
 */
@ApiModel(value = "机构")
public class Organization implements Serializable, OrgUserModel {
    private static final long serialVersionUID = 1L;
    
    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构id",required=true)
    private String id;
    
    /**
     * 父机构ID,根机构的父机构id=0
     */
    @ApiModelProperty(value = "父机构ID,根机构的父机构id=0",required=true)
    private String pid;
    
    /**
     * 机构编号，对应系统管理表中的orgNumer
     */
    @ApiModelProperty(value = "机构编号",required=true)
    private String code;
    
    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称",notes="如果全局配置中sysMgt.isUniqueOrgName为true,那么机构名称必须全局唯一",required=true)
    private String name;
    
    /**
     * 机构排序ID
     */
    @ApiModelProperty(value = "机构排序ID")
    private Integer sn;
    
    /**
     * 机构描述
     */
    @ApiModelProperty(value = "机构描述")
    private String desc;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Timestamp creatAt;
    
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;
    
    /**
     * 机构行政级别，与系统管理的"行政级别"字典对应，存储的是字典的值
     */
    @ApiModelProperty(value = "机构行政级别，与系统管理的\"行政级别\"字典对应，存储的是字典的值")
    private String level;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getPid() {
        return pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    public Integer getSn() {
        return sn;
    }
    
    public void setSn(Integer sn) {
        this.sn = sn;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Timestamp getCreatAt() {
        return creatAt;
    }
    
    public void setCreatAt(Timestamp creatAt) {
        this.creatAt = creatAt;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
}
