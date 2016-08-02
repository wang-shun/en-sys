package com.chinacreator.c2.sys.sdk;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcResult;

import com.chinacreator.asp.comp.sys.common.exception.SysException;

import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;

@Activate(group = Constants.PROVIDER, order = 1)
public class ExceptionFilter implements Filter {
    private final Logger logger;
    
    public ExceptionFilter() {
        this(LoggerFactory.getLogger(ExceptionFilter.class));
    }
    
    public ExceptionFilter(Logger logger) {
        this.logger = logger;
    }
    
    public Result invoke(Invoker<?> invoker, Invocation invocation)
        throws RpcException {
        try {
            Result result = invoker.invoke(invocation);
            Throwable exception = result.getException();
            
            if (exception != null && exception instanceof SysException) {
                result = new RpcResult(new SysResourcesException(exception.getMessage()));
                logger.error("系统管理内部异常,consumer： " + RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: " + exception.getClass()
                    .getName() + ": " + exception.getMessage(), exception);
            }
            return result;
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
}
