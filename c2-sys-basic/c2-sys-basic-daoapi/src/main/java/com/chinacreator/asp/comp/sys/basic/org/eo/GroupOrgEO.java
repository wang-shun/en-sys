package com.chinacreator.asp.comp.sys.basic.org.eo;

import java.io.Serializable;
/**
 * 用户组与机构关联数据库访问类
 * @author 杨祎程
 *
 */
public class GroupOrgEO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupId;
	private String orgId;

	public String getOrgId() {
		return orgId ;
	}

	public void setOrgId(String orgId) {
		this.orgId = null == orgId ? null : orgId.trim();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = null == groupId ? null : groupId.trim();
	}


}
