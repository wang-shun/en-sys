package com.chinacreator.asp.comp.sys.core.privilege.dto;

import java.io.Serializable;

/**
 * 权限内部关联数据传输对象类
 * 
 * @author 彭盛
 * 
 */
public class PrivilegeInsiderelateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 权限ID */
	private String id;

	/** 关联ID(父ID) */
	private String relateId;

	/** 排序号 */
	private Integer sn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId == null ? null : relateId.trim();
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
}
