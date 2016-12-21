package com.chinacreator.c2.sys.sdk.exception;

/**
 * 系统管理SDK调用异常
 *  
 * @author Vurt
 */
public class SysSDKException extends RuntimeException {

	private static final long serialVersionUID = -6797082072559602557L;

	public SysSDKException(String message){
		super(message);
	}
}
