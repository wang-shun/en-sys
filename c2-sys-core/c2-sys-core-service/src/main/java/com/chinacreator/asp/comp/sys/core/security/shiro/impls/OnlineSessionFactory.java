package com.chinacreator.asp.comp.sys.core.security.shiro.impls;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import com.chinacreator.asp.comp.sys.common.RemoteHostUtil;

public class OnlineSessionFactory implements SessionFactory {

	@Override
	public Session createSession(SessionContext initData) {
		SimpleSession session = new SimpleSession();
		if (initData != null && initData instanceof WebSessionContext) {
			WebSessionContext sessionContext = (WebSessionContext) initData;
			HttpServletRequest request = (HttpServletRequest) sessionContext
					.getServletRequest();
			if (request != null) {
				session.setHost(RemoteHostUtil.getIpAddr(request));
			}
		}
		return session;
	}
}
