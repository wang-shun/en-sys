package com.chinacreator.asp.comp.sys.basic.userpreferences.eo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户偏好设置信息数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class UserPreferencesEO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private String userId;
	/** 信息名称 */
	private String infoName;
	/** 信息内容 */
	private String infoContent;
	/** 信息描述 */
	private String infoDesc;
	/** 最后修改时间 */
	private Timestamp infoLastupdate;

	public String getUserId() {
		return userId = null == userId ? null : userId.trim();
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfoName() {
		return infoName = null == infoName ? null : infoName.trim();
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public String getInfoContent() {
		return infoContent = null == infoContent ? null : infoContent.trim();
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoDesc() {
		return infoDesc = null == infoDesc ? null : infoDesc.trim();
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public Timestamp getInfoLastupdate() {
		return infoLastupdate = null == infoLastupdate ? null : infoLastupdate;
	}

	public void setInfoLastupdate(Timestamp infoLastupdate) {
		this.infoLastupdate = infoLastupdate;
	}
}
