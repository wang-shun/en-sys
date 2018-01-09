package com.chinacreator.c2.sysmgr;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;

/**
 * 主体接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrSubject implements Subject {

	/**
	 * 用户数据传输对象
	 */
	private UserDTO userDTO;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getId() {
		if (null != getUserDTO()) {
			return getUserDTO().getUserId();
		}
		return null;
	}

	public String getName() {
		if (null != getUserDTO()) {
			return getUserDTO().getUserName();
		}
		return null;
	}
}
