package com.chinacreator.c2.sys.selecttree.handler;

import java.lang.reflect.Method;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.google.common.reflect.AbstractInvocationHandler;

public class UserInvocationHandler extends AbstractInvocationHandler {

	private UserDTO userDTO;

	public UserInvocationHandler(Object bean) {
		this.userDTO = (UserDTO) bean;
	}

	@Override
	protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
		if ("getId".equals(method.getName())) {
			return "user:" + userDTO.getUserId()+"###org:"+userDTO.getExtFields().get("orgId");
		} else if ("getPid".equals(method.getName())) {
			return userDTO.getExtFields().get("orgId");
		} else if ("getName".equals(method.getName())) {
			return userDTO.getUserRealname();
		} else if ("getIsParent".equals(method.getName())) {
			return false;
		} else if ("getIndex".equals(method.getName())) {
			return userDTO.getExtFields().get("sn");
		} else if ("getTypeIndex".equals(method.getName())) {
			return 2;
		} else if ("getType".equals(method.getName())) {
			return "user";
		} else if ("getDepth".equals(method.getName())) {
			return -1;
		} else if ("getDTO".equals(method.getName())) {
			return userDTO;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "id:" + "user:" + userDTO.getUserId()+"###org:"+userDTO.getExtFields().get("orgId") + ",pid:" + userDTO.getExtFields().get("orgId") + ",name:" + userDTO.getUserRealname()
				+ ",index:" + userDTO.getExtFields().get("sn");
	}
}
