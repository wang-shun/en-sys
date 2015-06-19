package com.chinacreator.asp.comp.sys.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 远程主机工具类
 * 
 * @author 彭盛
 * 
 */
public class RemoteHostUtil {

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 *            request对象
	 * @return IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (null != ip && ip.trim().equals("")) {
			ip = ip.split(",")[0];
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
