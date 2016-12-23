package com.chinacreator.asp.sysmgmt.sysset.resmgt;

import java.io.Serializable;

import com.chinacreator.c2.web.ds.impl.DefaultTreeNode;

public class ResourceTreeNode extends DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;

	private boolean virtual;

	private String privilegeId;

	private String noteTitle;

	private String source;

	private Object obj;

	private Integer sn;
	

	public ResourceTreeNode(String id, String name) {
		super(id, name);
	}

	public ResourceTreeNode(String id, String name, String type, boolean virtual) {
		super(id, name);
		this.type = type;
		this.virtual = virtual;
	}

	public ResourceTreeNode(String pid, String id, String name, String type, boolean virtual) {
		super(id, name);
		setPid(pid);
		this.type = type;
		this.virtual = virtual;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
}
