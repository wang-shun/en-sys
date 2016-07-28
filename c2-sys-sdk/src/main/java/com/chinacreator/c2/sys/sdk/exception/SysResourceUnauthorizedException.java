package com.chinacreator.c2.sys.sdk.exception;

/**
 * 没有权限操作指定的资源异常
 * 
 * @author Administrator
 */
public class SysResourceUnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 553565032806546658L;

    public SysResourceUnauthorizedException(String message){
        super(message);
    }
}
