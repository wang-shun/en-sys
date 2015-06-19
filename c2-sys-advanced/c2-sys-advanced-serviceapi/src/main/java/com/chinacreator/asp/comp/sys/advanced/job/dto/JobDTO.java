package com.chinacreator.asp.comp.sys.advanced.job.dto;

import java.io.Serializable;

/**
 * 岗位数据传输对象类
 * 
 * @author 彭盛
 * 
 */
public class JobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    private String jobId;
    /** 岗位名称 */
    private String jobName;
    /** 岗位描述 */
    private String jobDesc;
    /** 岗位范围(0:通用岗位,1:机构特有) */
    private String jobScope;
    /** 岗位职责 */
    private String jobFunction;
    /** 岗位编制人数 */
    private String jobAmount;
    /** 岗位编号 */
    private String jobNumber;
    /** 任职条件 */
    private String jobCondition;
    /** 岗位级别 */
    private String jobRank;
    /** 岗位授予人ID */
    private String ownerId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = null == jobId ? null : jobId.trim();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = null == jobName ? null : jobName.trim();
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = null == jobDesc ? null : jobDesc.trim();
    }

    public String getJobScope() {
        return jobScope;
    }

    public void setJobScope(String jobScope) {
        this.jobScope = null == jobScope ? null : jobScope.trim();
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = null == jobFunction ? null : jobFunction.trim();
    }

    public String getJobAmount() {
        return jobAmount;
    }

    public void setJobAmount(String jobAmount) {
        this.jobAmount = null == jobAmount ? null : jobAmount.trim();
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = null == jobNumber ? null : jobNumber.trim();
    }

    public String getJobCondition() {
        return jobCondition;
    }

    public void setJobCondition(String jobCondition) {
        this.jobCondition = null == jobCondition ? null : jobCondition.trim();
    }

    public String getJobRank() {
        return jobRank;
    }

    public void setJobRank(String jobRank) {
        this.jobRank = null == jobRank ? null : jobRank.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = null == ownerId ? null : ownerId.trim();
    }
}