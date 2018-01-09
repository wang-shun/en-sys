package com.chinacreator.c2.sys.sdk.client.exception;

import javax.ws.rs.core.Response.Status;

import org.springframework.web.client.HttpStatusCodeException;

import com.chinacreator.c2.web.exception.RestException;

public class SysSDKInvokeException extends RestException {
	private static final long serialVersionUID = -8624103928029161646L;
	
	private int statusCode;
	
	public SysSDKInvokeException(String message, HttpStatusCodeException cause) {
		super(message, cause);
		this.statusCode = cause.getStatusCode().value();
	}

	@Override
	public Status getHttpStatus() {
		return Status.fromStatusCode(statusCode);
	}

}
