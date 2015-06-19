package com.chinacreator.asp.comp.sys.core.privilege.eo;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class PrivilegeEO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 权限ID */
	private String privilegeId;
	/** 权限编码 */
	private String privilegeCode;
	/** 权限名称 */
	private String privilegeName;
	/** 父ID */
	private String parentId;
	/** 类型（1：操作，2：表单，3：服务，4：菜单） */
	private String type;
	/** 权限字符串 */
	private String permExpr;
	/** 创建者 */
	private String creator;
	/** 创建时间 */
	private Date creatorTime;
	/** 排序号 */
	private Integer sn;
	/** 权限来源（0：自定义，1：IDE） */
	private String source;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = null == privilegeId ? null : privilegeId.trim();
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = null == privilegeCode ? null : privilegeCode
				.trim();
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = null == privilegeName ? null : privilegeName
				.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = null == parentId ? null : parentId.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = null == type ? null : type.trim();
	}

	public String getPermExpr() {
		return permExpr;
	}

	public void setPermExpr(String permExpr) {
		this.permExpr = null == permExpr ? null : permExpr.trim();
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = null == creator ? null : creator.trim();
	}

	public Date getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(Date creatorTime) {
		this.creatorTime = null == creatorTime ? null : creatorTime;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = null == source ? null : source;
	}
}
