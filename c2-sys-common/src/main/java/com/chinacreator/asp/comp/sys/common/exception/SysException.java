package com.chinacreator.asp.comp.sys.common.exception;

import com.chinacreator.c2.exception.C2RuntimeException;

/**
 * 系统管理自定义异常
 * 
 * @author 彭盛
 */
public class SysException extends C2RuntimeException {

	private static final long serialVersionUID = 1L;

	public SysException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysException(String message) {
		super(message);
	}
}
