package com.chinacreator.asp.comp.sys.basic.org.eo;

import java.io.Serializable;
import java.util.Date;

/**
 * 机构数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class OrgEO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 机构ID */
    private String orgId;
    /** 机构排序ID */
    private Integer orgSn;
    /** 机构名称 */
    private String orgName;
    /** 机构显示名称 */
    private String orgShowName;
    /** 父机构ID */
    private String parentId;
    /** 路径 */
    private String path;
    /** 层（阶次） */
    private String layer;
    /** 子机构 */
    private String children;
    /** 机构代号 */
    private String code;
    /** 简拼 */
    private String jp;
    /** 全拼 */
    private String qp;
    /** 创建时间 */
    private Date creatingTime;
    /** 创建人 */
    private String creator;
    /** 机构编号 */
    private String orgNumber;
    /** 机构描述 */
    private String orgDesc;
    /** 主管机构 */
    private String chargeOrgId;
    /** 机构行政级别，1：省级，2：市州级，3：县区级，4：科所级 */
    private String orgLevel;
    /** 行政区码 */
    private String orgXzqm;
    /** 是否直属局 0-不是，缺省值 1-是 */
    private Integer isDirectlyparty;
    /** 是否涉外局 0-是，缺省值 1-不是 */
    private Integer isForeignparty;
    /** 是否稽查局 0-不是，缺省值 1-是 */
    private Integer isJichaparty;
    /** 是否直接管户单位 0-不是，缺省值 1-是 */
    private Integer isDirectguanhu;
    /** 备用字段1 */
    private String remark1;
    /** 备用字段2 */
    private String remark2;
    /** 备用字段3 */
    private String remark3;
    /** 备用字段4 */
    private String remark4;
    /** 备用字段5 */
    private String remark5;
    /** 备用字段6 */
    private String remark6;
    /** 备用字段7 */
    private String remark7;
    /** 备用字段8 */
    private String remark8;
    /** 备用字段9 */
    private String remark9;
    /** 备用字段10 */
    private String remark10;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = null == orgId ? null : orgId.trim();
    }

    public Integer getOrgSn() {
        return orgSn;
    }

    public void setOrgSn(Integer orgSn) {
        this.orgSn = null == orgSn ? null : orgSn;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = null == orgName ? null : orgName.trim();
    }

    public String getOrgShowName() {
        return orgShowName;
    }

    public void setOrgShowName(String orgShowName) {
        this.orgShowName = null == orgShowName ? null : orgShowName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = null == parentId ? null : parentId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = null == path ? null : path.trim();
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = null == layer ? null : layer.trim();
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = null == children ? null : children.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = null == code ? null : code.trim();
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = null == jp ? null : jp.trim();
    }

    public String getQp() {
        return qp;
    }

    public void setQp(String qp) {
        this.qp = null == qp ? null : qp.trim();
    }

    public Date getCreatingTime() {
        return null == creatingTime ? null : (Date) creatingTime.clone();
    }

    public void setCreatingTime(Date creatingTime) {
        this.creatingTime = null == creatingTime ? null : (Date) creatingTime
                .clone();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = null == creator ? null : creator.trim();
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = null == orgNumber ? null : orgNumber.trim();
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = null == orgDesc ? null : orgDesc.trim();
    }

    public String getChargeOrgId() {
        return chargeOrgId;
    }

    public void setChargeOrgId(String chargeOrgId) {
        this.chargeOrgId = null == chargeOrgId ? null : chargeOrgId.trim();
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = null == orgLevel ? null : orgLevel.trim();
    }

    public String getOrgXzqm() {
        return orgXzqm;
    }

    public void setOrgXzqm(String orgXzqm) {
        this.orgXzqm = null == orgXzqm ? null : orgXzqm.trim();
    }

    public Integer getIsDirectlyparty() {
        return isDirectlyparty;
    }

    public void setIsDirectlyparty(Integer isDirectlyparty) {
        this.isDirectlyparty = null == isDirectlyparty ? null : isDirectlyparty;
    }

    public Integer getIsForeignparty() {
        return isForeignparty;
    }

    public void setIsForeignparty(Integer isForeignparty) {
        this.isForeignparty = null == isForeignparty ? null : isForeignparty;
    }

    public Integer getIsJichaparty() {
        return isJichaparty;
    }

    public void setIsJichaparty(Integer isJichaparty) {
        this.isJichaparty = null == isJichaparty ? null : isJichaparty;
    }

    public Integer getIsDirectguanhu() {
        return isDirectguanhu;
    }

    public void setIsDirectguanhu(Integer isDirectguanhu) {
        this.isDirectguanhu = null == isDirectguanhu ? null : isDirectguanhu;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = null == remark1 ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = null == remark2 ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = null == remark3 ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = null == remark4 ? null : remark4.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = null == remark5 ? null : remark5.trim();
    }

    public String getRemark6() {
        return remark6;
    }

    public void setRemark6(String remark6) {
        this.remark6 = null == remark6 ? null : remark6.trim();
    }

    public String getRemark7() {
        return remark7;
    }

    public void setRemark7(String remark7) {
        this.remark7 = null == remark7 ? null : remark7.trim();
    }

    public String getRemark8() {
        return remark8;
    }

    public void setRemark8(String remark8) {
        this.remark8 = null == remark8 ? null : remark8.trim();
    }

    public String getRemark9() {
        return remark9;
    }

    public void setRemark9(String remark9) {
        this.remark9 = null == remark9 ? null : remark9.trim();
    }

    public String getRemark10() {
        return remark10;
    }

    public void setRemark10(String remark10) {
        this.remark10 = null == remark10 ? null : remark10.trim();
    }
}