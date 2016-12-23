package com.chinacreator.asp.comp.sys.advanced.group.eo;

import java.io.Serializable;

/**
 * 用户组与机构岗位关系数据访问对象类
 * 
 * @author 杨祎程
 * 
 */
public class GroupOrgJobEO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户组ID */
	private String groupId;

	/** 岗位ID */
	private String jobId;

	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = null == groupId ? null : groupId.trim();
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = null == jobId ? null : jobId.trim();
	}

}
