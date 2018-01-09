package com.chinacreator.asp.comp.sys.basic.user.eo;

import java.io.Serializable;

/**
 * 用户机构扩展信息数据库访问对象
 * @author 杨祎程
 */
public class UserInstanceOrgEO implements Serializable{

    private static final long serialVersionUID = 1L;
    /** 用户实例ID */
    private String userInstanceId;
    /** 是否主机构 */
    private Boolean isMain;
    /** 用户在机构下的排序号 */
    private Integer sn;

    public String getUserInstanceId() {
        return userInstanceId;
    }

    public void setUserInstanceId(String userInstanceId) {
        this.userInstanceId = null == userInstanceId ? null : userInstanceId.trim();
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = null == isMain ? null : isMain;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = null == sn ? null : sn;
    } 
}
