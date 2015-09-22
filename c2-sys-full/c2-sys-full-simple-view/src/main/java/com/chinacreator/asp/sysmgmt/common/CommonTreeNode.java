package com.chinacreator.asp.sysmgmt.common;

import java.io.Serializable;

import com.chinacreator.c2.web.ds.TreeNode;

public class CommonTreeNode implements TreeNode, Serializable {
	private static final long serialVersionUID = 1L;

	/** 父节点ID */
	private String pid;
	/** 节点ID */
	private String id;
	/** 节点名称 */
	private String name;
	/** 是否父节点 */
	private boolean isParent;
	/** 节点自定义图标的 className */
	private String iconSkin;
	/** 设置节点的 checkbox / radio 是否禁用 */
	private boolean chkDisabled;
	/** 节点的 checkBox / radio 的 勾选状态 */
	private boolean checked;
	/** 设置节点是否隐藏 checkbox / radio  */
	private boolean nocheck;
	/** (自定义属性)隐含节点名称 */
	private String hiddenName;
	/** (自定义属性)悬停节点名称 */
	private String showName;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
}
