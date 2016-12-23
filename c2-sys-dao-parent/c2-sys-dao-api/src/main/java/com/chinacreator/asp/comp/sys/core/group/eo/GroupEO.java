package com.chinacreator.asp.comp.sys.core.group.eo;

import java.io.Serializable;

/**
 * 用户组数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class GroupEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户组ID */
    private String groupId;
    /** 用户组名 */
    private String groupName;
    /** 用户组描述 */
    private String groupDesc;
    /** 父ID */
    private String parentId;
    /** 用户组创建人ID */
    private String ownerId;
    /** 备用字段1 */
    private String remark1;
    /** 备用字段2 */
    private String remark2;
    /** 备用字段3 */
    private String remark3;
    /** 备用字段4 */
    private String remark4;
    /** 备用字段5 */
    private String remark5;
    /** 类型（0：自定义用户组，1：机构，2：岗位） */
    private String type;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = null == groupId ? null : groupId.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = null == groupName ? null : groupName.trim();
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = null == groupDesc ? null : groupDesc.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = null == parentId ? null : parentId.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = null == ownerId ? null : ownerId.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = null == remark1 ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = null == remark2 ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = null == remark3 ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = null == remark4 ? null : remark4.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = null == remark5 ? null : remark5.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = null == type ? null : type.trim();
    }
}
