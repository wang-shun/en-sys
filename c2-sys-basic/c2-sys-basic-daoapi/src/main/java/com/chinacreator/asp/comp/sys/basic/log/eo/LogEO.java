package com.chinacreator.asp.comp.sys.basic.log.eo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 日志数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class LogEO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 日志ID */
	private String logId;
	/** 用户帐号 */
	private String logOperUser;
	/** 用户实名 */
	private String logOperUserRealName;
	/** 日志模块ID */
	private String operModule;
	/** 日志类型（e：实体操作，ws：服务，dao：持久层操作，custom：自定义） */
	private String logType;
	/** 日志操作ID */
	private String logOper;
	/** 日志操作描述 */
	private String logOperdesc;
	/** 操作来源（一般为操作员机器ip） */
	private String logVisitorial;
	/** 操作时间 */
	private Timestamp logOperTime;
	/** 操作内容 */
	private String logContent;
	/** 操作类型（ 1:新增 2:删除 3:修改 4:查询 5:其他） */
	private Integer operType;
	/** 日志状态（1：成功，0：失败） */
	private Integer logStatus;
	/** 备注1 */
	private String remark1;
	/** 备注2 */
	private String remark2;
	/** 备注3 */
	private String remark3;
	/** 备注4 */
	private String remark4;
	/** 备注5 */
	private String remark5;

	public String getLogId() {
		return logId = null == logId ? null : logId.trim();
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogOperUser() {
		return logOperUser = null == logOperUser ? null : logOperUser.trim();
	}

	public void setLogOperUser(String logOperUser) {
		this.logOperUser = logOperUser;
	}

	public String getLogOperUserRealName() {
		return logOperUserRealName = null == logOperUserRealName ? null
				: logOperUserRealName.trim();
	}

	public void setLogOperUserRealName(String logOperUserRealName) {
		this.logOperUserRealName = logOperUserRealName;
	}

	public String getOperModule() {
		return operModule = null == operModule ? null : operModule.trim();
	}

	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}

	public String getLogType() {
		return logType = null == logType ? null : logType.trim();
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogOper() {
		return logOper = null == logOper ? null : logOper.trim();
	}

	public void setLogOper(String logOper) {
		this.logOper = logOper;
	}

	public String getLogOperdesc() {
		return logOperdesc = null == logOperdesc ? null : logOperdesc.trim();
	}

	public void setLogOperdesc(String logOperdesc) {
		this.logOperdesc = logOperdesc;
	}

	public String getLogVisitorial() {
		return logVisitorial = null == logVisitorial ? null : logVisitorial
				.trim();
	}

	public void setLogVisitorial(String logVisitorial) {
		this.logVisitorial = logVisitorial;
	}

	public Timestamp getLogOperTime() {
		return logOperTime = null == logOperTime ? null : logOperTime;
	}

	public void setLogOperTime(Timestamp logOperTime) {
		this.logOperTime = logOperTime;
	}

	public String getLogContent() {
		return logContent = null == logContent ? null : logContent.trim();
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Integer getOperType() {
		return operType = null == operType ? null : operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Integer getLogStatus() {
		return logStatus = null == logStatus ? null : logStatus;
	}

	public void setLogStatus(Integer logStatus) {
		this.logStatus = logStatus;
	}

	public String getRemark1() {
		return remark1 = null == remark1 ? null : remark1.trim();
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2 = null == remark2 ? null : remark2.trim();
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3 = null == remark3 ? null : remark3.trim();
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRemark4() {
		return remark4 = null == remark4 ? null : remark4.trim();
	}

	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getRemark5() {
		return remark5 = null == remark5 ? null : remark5.trim();
	}

	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
}