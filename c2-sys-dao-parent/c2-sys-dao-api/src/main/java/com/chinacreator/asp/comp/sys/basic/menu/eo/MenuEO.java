package com.chinacreator.asp.comp.sys.basic.menu.eo;

import java.io.Serializable;

/**
 * 菜单数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class MenuEO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 菜果ID */
	private String menuId;
	/** 是否使用（false：不显示，true：显示） */
	private Boolean isEnabled;
	/** 链接路径 */
	private String href;
	/** 图标 */
	private String icon;
	/** 打开方式（0：div中，1：iframe中，2：新页面中） */
	private String displayMode;
	/** 菜单扩展字段 */
	private String menuExt;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = null == menuId ? null : menuId.trim();
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

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = null == displayMode ? null : displayMode;
	}

	public String getMenuExt() {
		return menuExt;
	}

	public void setMenuExt(String menuExt) {
		this.menuExt = null == menuExt ? null : menuExt;
	}

}