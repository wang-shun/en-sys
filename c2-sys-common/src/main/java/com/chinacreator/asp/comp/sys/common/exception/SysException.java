package com.chinacreator.asp.comp.sys.common.exception;

import com.chinacreator.c2.exception.C2BusinessException;

/**
 * 系统管理自定义异常
 * 
 * @author 彭盛
 */
public class SysException extends RuntimeException implements C2BusinessException {

	private static final long serialVersionUID = 1L;

	public SysException() {
		super();
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
	}

	public SysException(String message) {
		super(message);
	}

	public SysException(Throwable cause) {
		super(cause);
	}
}
