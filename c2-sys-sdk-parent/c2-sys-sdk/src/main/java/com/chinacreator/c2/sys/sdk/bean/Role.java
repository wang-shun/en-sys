package com.chinacreator.c2.sys.sdk.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 角色数据传输对象类
 * @author 彭盛
 */
@ApiModel(value = "角色数据传输对象")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 角色ID
    */
    @ApiModelProperty(value = "角色ID")
    private String id;

    /**
    * 角色名
    */
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
    * 是否使用 true:使用,false:不使用
    */
    @ApiModelProperty(value = "是否使用 true:使用,false:不使用")
    private Boolean usage;

    /**
    * 角色类型
    */
    @ApiModelProperty(value = "角色类型")
    private String type;

    /**
    * 角色描述
    */
    @ApiModelProperty(value = "角色描述")
    private String desc;

    /**
    * 创建人id
    */
    @ApiModelProperty(value = "创建人id")
    private String creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUsage() {
        return usage;
    }

    public void setUsage(Boolean usage) {
        this.usage = usage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
