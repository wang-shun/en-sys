package com.chinacreator.asp.comp.sys.basic.menu.eo;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单数据传输对象类（所有信息）
 * 
 * @author 杨祎程
 * 
 */
public class MenuAllInfoEO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 菜单ID */
	private String menuId;
	/** 菜单编码 */
	private String menuCode;
	/** 菜单名称 */
	private String menuName;
	/** 父模块ID */
	private String parentId;
	/** 是否使用（false：不显示，true：显示） */
	private Boolean isEnabled;
	/** 链接路径 */
	private String href;
	/** 图标 */
	private String icon;
	/** 模块创建者 */
	private String creator;
	/** 创建时间 */
	private Date creatorTime;
	/** 排序号 */
	private Integer sn;
	/** 菜单类型（1：操作，2：表单，3：服务，4：菜单） */
	private String type;
	/** 权限字符串 */
	private String permExpr;
	/** 打开方式（0：div中，1：iframe中，2：新页面中） */
	private String displayMode;
	/** 权限来源（0：自定义，1：IDE） */
	private String source;
	/** 菜单扩展字段 */
	private String menuExt;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = null == menuId ? null : menuId.trim();
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = null == menuCode ? null : menuCode.trim();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = null == menuName ? null : menuName.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = null == parentId ? null : parentId.trim();
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = null == isEnabled ? null : isEnabled;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = null == href ? null : href.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = null == icon ? null : icon.trim();
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = null == creator ? null : creator.trim();
	}

	public Date getCreatorTime() {
		return null == creatorTime ? null : (Date) creatorTime.clone();
	}

	public void setCreatorTime(Date creatorTime) {
		this.creatorTime = null == creatorTime ? null : (Date) creatorTime
				.clone();
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = null == sn ? null : sn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = null == type ? null : type;
	}

	public String getPermExpr() {
		return permExpr;
	}

	public void setPermExpr(String permExpr) {
		this.permExpr = null == permExpr ? null : permExpr.trim();
	}

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = null == displayMode ? null : displayMode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = null == source ? null : source;
	}
	
	public String getMenuExt() {
		return menuExt;
	}

	public void setMenuExt(String menuExt) {
		this.menuExt = null == menuExt ? null : menuExt;
	}
}