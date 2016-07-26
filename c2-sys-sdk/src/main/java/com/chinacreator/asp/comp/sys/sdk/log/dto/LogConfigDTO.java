package com.chinacreator.asp.comp.sys.sdk.log.dto;

import java.io.Serializable;


/**
 * 日志配置数据传输对象类
 * @author 彭盛
 */
public class LogConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 日志模块ID
    */
    private String operModule;

    /**
    * 日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义）
    */
    private String logType;

    /**
    * 日志操作ID
    */
    private String logOper;

    /**
    * 日志操作描述
    */
    private String logOperdesc;

    /**
    * 记录日志状态，false:不记录日志，true:记录日志
    */
    private Boolean logEnabled;

    public String getOperModule() {
        return operModule = (null == operModule) ? null : operModule.trim();
    }

    public void setOperModule(String operModule) {
        this.operModule = operModule;
    }

    public String getLogType() {
        return logType = (null == logType) ? null : logType.trim();
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogOper() {
        return logOper = (null == logOper) ? null : logOper.trim();
    }

    public void setLogOper(String logOper) {
        this.logOper = logOper;
    }

    public String getLogOperdesc() {
        return logOperdesc = (null == logOperdesc) ? null : logOperdesc.trim();
    }

    public void setLogOperdesc(String logOperdesc) {
        this.logOperdesc = logOperdesc;
    }

    public Boolean getLogEnabled() {
        return logEnabled = (null == logEnabled) ? null : logEnabled;
    }

    public void setLogEnabled(Boolean logEnabled) {
        this.logEnabled = logEnabled;
    }
}
