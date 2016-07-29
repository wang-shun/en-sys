package com.chinacreator.c2.sys.selecttree.handler;

import java.lang.reflect.Method;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.google.common.reflect.AbstractInvocationHandler;

public class OrgInvocationHandler extends AbstractInvocationHandler {

	private OrgDTO orgDTO;

	public OrgInvocationHandler(Object bean) {
		this.orgDTO = (OrgDTO) bean;
	}

	@Override
	protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
		if ("getId".equals(method.getName())) {
			return orgDTO.getOrgId();
		} else if ("getPid".equals(method.getName())) {
			String pid = orgDTO.getParentId();
			if (null == pid || pid.trim().equals("") || pid.trim().equals("0")) {
				return null;
			} else {
				return pid;
			}
		} else if ("getName".equals(method.getName())) {
			return orgDTO.getOrgShowName();
		} else if ("getIsParent".equals(method.getName())) {
			return false;
		} else if ("getIndex".equals(method.getName())) {
			return null!=orgDTO.getOrgSn()?orgDTO.getOrgSn():999;
		} else if ("getTypeIndex".equals(method.getName())) {
			return 1;
		} else if ("getType".equals(method.getName())) {
			return "org";
		} else if ("getDepth".equals(method.getName())) {
			return Integer.parseInt(orgDTO.getLayer());
		}else if("getDTO".equals(method.getName())){
			return orgDTO;
		}
		return null;
	}

	@Override
	public String toString() {
		return "id:" + orgDTO.getOrgId() + ",pid:" + orgDTO.getParentId() + ",name:" + orgDTO.getOrgShowName()
				+ ",index:" + orgDTO.getOrgSn()+",depth:"+orgDTO.getLayer();
	}
}
