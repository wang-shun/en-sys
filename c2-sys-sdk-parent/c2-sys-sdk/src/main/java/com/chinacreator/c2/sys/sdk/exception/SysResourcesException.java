package com.chinacreator.c2.sys.sdk.exception;

/**
 * 系统管理资源变更时发生错误
 *  
 * @author Vurt
 */
public class SysResourcesException extends Exception {

	private static final long serialVersionUID = -6797082072559602557L;

	public SysResourcesException(String message){
		super(message);
	}
}
