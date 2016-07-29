package com.chinacreator.c2.sys.sdk.exception;

/**
 * 参数不符合要求异常
 * 
 * @author Administrator
 */
public class IllegalParameterException extends RuntimeException {
    private static final long serialVersionUID = -6391206066283838011L;

    public IllegalParameterException(String message){
        super(message);
    }
}
