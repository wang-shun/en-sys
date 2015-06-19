package com.chinacreator.asp.comp.sys.core.security.dto;

import java.io.Serializable;
import java.util.Date;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 在线用户数据传输对象类
 * 
 * @author 彭盛
 * 
 */
public class OnlineUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会话ID */
	private String sessionId;
	/** 会话IP */
	private String host;
	/** 会话开始时间 */
	private Date startTime;
	/** 会话最后访问时间 */
	private Date lastAccessTime;
	/** 用户是否已通过身份认证 */
	private Boolean authenticated;
	/** 用户数据传输对象类 */
	private UserDTO userDTO;

	public String getSessionId() {
		return sessionId = null == sessionId ? null : sessionId.trim();
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHost() {
		return host = null == host ? null : host.trim();
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getStartTime() {
		return startTime = null == startTime ? null : startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastAccessTime() {
		return lastAccessTime = null == lastAccessTime ? null : lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Boolean getAuthenticated() {
		return authenticated = null == authenticated ? null : authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public UserDTO getUserDTO() {
		return userDTO = null == userDTO ? null : userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
