package com.chinacreator.asp.comp.sys.advanced.user.eo;

import java.io.Serializable;

/**
 *  用户实例与岗位关系数据访问对象类
 * 
 * @author 杨祎程
 * 
 */

public class UserInstanceJobEO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户实例ID */
	private String userinstanceId;
	/** 岗位ID */
	private Boolean isMain;
	
	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = null == isMain ? null : isMain;
	}

	/** 用户排序号 */
	private Integer sn;

	public String getUserinstanceId() {
		return userinstanceId;
	}

	public void setUserinstanceId(String userinstanceId) {
		this.userinstanceId = null == userinstanceId ? null : userinstanceId.trim();
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = null == sn ? null : sn;
	}

}
