package com.chinacreator.c2.sys.sdk.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 用户数据传输对象类
 * @author 彭盛
 */
@ApiModel(value = "用户数据传输对象")
public class User implements Serializable, OrgUserModel {
    private static final long serialVersionUID = 1L;

    /**
    * 用户ID
    */
    @ApiModelProperty(value = "用户ID")
    private String id;

    /**
    * 用户账号
    */
    @ApiModelProperty(value = "用户账号")
    private String name;

    /**
    * 用户密码
    */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
    * 用户实名
    */
    @ApiModelProperty(value = "用户实名")
    private String realname;

    /**
    * 性别
    */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
    * 家庭电话
    */
    @ApiModelProperty(value = "家庭电话")
    private String hometel;

    /**
    * 公司电话
    */
    @ApiModelProperty(value = "公司电话")
    private String worktel;

    /**
    * 公司地址
    */
    @ApiModelProperty(value = "公司地址")
    private String workaddress;

    /**
    * 手机1
    */
    @ApiModelProperty(value = "手机1")
    private String mobiletel1;

    /**
    * 手机2
    */
    @ApiModelProperty(value = "手机2")
    private String mobiletel2;

    /**
    * 传真
    */
    @ApiModelProperty(value = "传真")
    private String fax;

    /**
    * qq
    */
    @ApiModelProperty(value = "qq")
    private String qq;

    /**
    * 生日
    */
    @ApiModelProperty(value = "生日")
    private Timestamp birthday;

    /**
    * 邮箱
    */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
    * 住址
    */
    @ApiModelProperty(value = "住址")
    private String address;

    /**
    * 邮编
    */
    @ApiModelProperty(value = "邮编")
    private String postalcode;

    /**
    * 身份证
    */
    @ApiModelProperty(value = "身份证")
    private String idcard;

    /**
    * 是否使用
    */
    @ApiModelProperty(value = "是否使用")
    private Integer isvalid;

    /**
    * 注册日期
    */
    @ApiModelProperty(value = "注册日期")
    private Timestamp regdate;

    /**
    * 登陆次数
    */
    @ApiModelProperty(value = "登陆次数")
    private Integer logincount;

    /**
    * 用户类型
    */
    @ApiModelProperty(value = "用户类型")
    private String type;

    /**
    * 过期时间
    */
    @ApiModelProperty(value = "过期时间")
    private Timestamp pastTime;

    /**
    * 开通时间
    */
    @ApiModelProperty(value = "开通时间")
    private String dredgeTime;

    /**
    * 用户最后登陆时间
    */
    @ApiModelProperty(value = "用户最后登陆时间")
    private Timestamp lastloginDate;

    /**
    * 工作年限
    */
    @ApiModelProperty(value = "工作年限")
    private String worklength;

    /**
    * 政治面貌
    */
    @ApiModelProperty(value = "政治面貌")
    private String politics;

    /**
    * 登录IP
    */
    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    /**
    * 用户排序号
    */
    @ApiModelProperty(value = "用户ID")
    private Integer sn;

    /**
    * 扩展字段
    */
    @ApiModelProperty(value = "扩展字段")
    private Map<String, Object> extFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHometel() {
        return hometel;
    }

    public void setHometel(String hometel) {
        this.hometel = hometel;
    }

    public String getWorktel() {
        return worktel;
    }

    public void setWorktel(String worktel) {
        this.worktel = worktel;
    }

    public String getWorkaddress() {
        return workaddress;
    }

    public void setWorkaddress(String workaddress) {
        this.workaddress = workaddress;
    }

    public String getMobiletel1() {
        return mobiletel1;
    }

    public void setMobiletel1(String mobiletel1) {
        this.mobiletel1 = mobiletel1;
    }

    public String getMobiletel2() {
        return mobiletel2;
    }

    public void setMobiletel2(String mobiletel2) {
        this.mobiletel2 = mobiletel2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Timestamp regdate) {
        this.regdate = regdate;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPastTime() {
        return pastTime;
    }

    public void setPastTime(Timestamp pastTime) {
        this.pastTime = pastTime;
    }

    public String getDredgeTime() {
        return dredgeTime;
    }

    public void setDredgeTime(String dredgeTime) {
        this.dredgeTime = dredgeTime;
    }

    public Date getLastloginDate() {
        return lastloginDate;
    }

    public void setLastloginDate(Timestamp lastloginDate) {
        this.lastloginDate = lastloginDate;
    }

    public String getWorklength() {
        return worklength;
    }

    public void setWorklength(String worklength) {
        this.worklength = worklength;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Map<String, Object> getExtFields() {
        return extFields;
    }

    public void setExtFields(Map<String, Object> extFields) {
        this.extFields = (null == extFields) ? null : extFields;
    }
}
