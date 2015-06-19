package com.chinacreator.asp.comp.sys.core.user.eo;

import java.io.Serializable;

/**
 * 用户实例与用户组关系数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class UserInstanceGroupEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户实例ID */
    private String userInstanceId;
    /** 用户组ID */
    private String groupId;
    /** 用户排序号 */
    private Integer sn;

    public String getUserInstanceId() {
        return userInstanceId;
    }

    public void setUserInstanceId(String userInstanceId) {
        this.userInstanceId = null == userInstanceId ? null : userInstanceId
                .trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = null == groupId ? null : groupId.trim();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = null == sn ? null : sn;
    }

}
