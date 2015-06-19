package com.chinacreator.c2.sysmgr;

import javax.servlet.http.HttpServletRequest;

import com.chinacreator.asp.comp.sys.common.RemoteHostUtil;
import com.chinacreator.c2.context.OperationContextHolder;
import com.chinacreator.c2.context.WebOperationContext;

public class SysMgrRemoteHost implements RemoteHost {

	@Override
	public String getIP() {
		WebOperationContext context = (WebOperationContext) OperationContextHolder
				.getContext();
		HttpServletRequest request = context.getRequest();
		return RemoteHostUtil.getIpAddr(request);
	}
}
