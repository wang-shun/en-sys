package com.chinacreator.asp.comp.sys.core.security.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.asp.comp.sys.core.security.shiro.util.ForwardView;

public class C2FormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(C2FormAuthenticationFilter.class);
	
	
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {

		  if (isLoginRequest(request, response)) {
	            if (isLoginSubmission(request, response)) {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login submission detected.  Attempting to execute login.");
	                }
	                return executeLogin(request, response);
	            } else {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login page view.");
	                }
	                //allow them to see the login page ;)
	                return true;
	            }
	        } else {
	            if (log.isTraceEnabled()) {
	                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
	                        "Authentication url [" + getLoginUrl() + "]");
	            }
	            
	            HttpServletResponse httpResponse=(HttpServletResponse)response;
	            
	            httpResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				httpResponse.setHeader("Pragma", "no-cache");
				httpResponse.setDateHeader("Expires", 0);
				
	            httpResponse.addHeader("nologin","c2login");
	            httpResponse.addHeader("loginurl",getLoginUrl());
	            saveRequestAndFowardToLogin(request,httpResponse);
	            return false;
	        }
	}
	
	
	/**
	 * 保存当前请求且转发至登陆页
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
    protected void saveRequestAndFowardToLogin(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        saveRequest(request);
        forwardToLogin(request, response);
    }
    
    
    /**
     * 转发至登陆页
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    protected void forwardToLogin(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        String loginUrl = getLoginUrl();
        ForwardView view = new ForwardView(loginUrl,false,true);
        view.renderMergedOutputModel(null, (HttpServletRequest) request, (HttpServletResponse) response);
    }
	
	
	
}
