package com.chinacreator.asp.comp.sys.advanced.org.eo;

import java.io.Serializable;

/**
 * 机构岗位关系数据访问对象类
 * 
 * @author 杨祎程
 * 
 */

public class OrgJobEO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 机构ID */
	private String orgId;
	/** 岗位ID */
	private String jobId;
	/** 机构岗位序号 */
	private Integer jobSn;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = null == orgId ? null : orgId.trim();
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = null == jobId ? null : jobId.trim();
	}

	public Integer getJobSn() {
		return jobSn;
	}

	public void setJobSn(Integer jobSn) {
		this.jobSn = null == jobSn ? null : jobSn;
	}


}
