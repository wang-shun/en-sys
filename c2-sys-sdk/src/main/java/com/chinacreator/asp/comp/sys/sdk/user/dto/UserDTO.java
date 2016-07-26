package com.chinacreator.asp.comp.sys.sdk.user.dto;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;


/**
 * 用户数据传输对象类
 * @author 彭盛
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 用户ID
    */
    private String userId;

    /**
    * 用户账号
    */
    private String userName;

    /**
    * 用户密码
    */
    private String userPassword;

    /**
    * 用户实名
    */
    private String userRealname;

    /**
    * 拼音
    */
    private String userPinyin;

    /**
    * 性别
    */
    private String userSex;

    /**
    * 家庭电话
    */
    private String userHometel;

    /**
    * 公司电话
    */
    private String userWorktel;

    /**
    * 公司地址
    */
    private String userWorkaddress;

    /**
    * 手机1
    */
    private String userMobiletel1;

    /**
    * 手机2
    */
    private String userMobiletel2;

    /**
    * 传真
    */
    private String userFax;

    /**
    * OICQ
    */
    private String userOicq;

    /**
    * 生日
    */
    private Date userBirthday;

    /**
    * 邮箱
    */
    private String userEmail;

    /**
    * 住址
    */
    private String userAddress;

    /**
    * 邮编
    */
    private String userPostalcode;

    /**
    * 身份证
    */
    private String userIdcard;

    /**
    * 是否使用
    */
    private Integer userIsvalid;

    /**
    * 注册日期
    */
    private Date userRegdate;

    /**
    * 登陆次数
    */
    private Integer userLogincount;

    /**
    * 用户类型
    */
    private String userType;

    /**
    * 过期时间
    */
    private Date pastTime;

    /**
    * 开通时间
    */
    private String dredgeTime;

    /**
    * 用户最后登陆时间
    */
    private Date lastloginDate;

    /**
    * 工作年限
    */
    private String worklength;

    /**
    * 政治面貌
    */
    private String politics;

    /**
    * 登录IP
    */
    private String loginIp;

    /**
    * 证书key的唯一标识
    */
    private String certSn;

    /**
    * 用户排序号
    */
    private Integer userSn;

    /**
    * 备用字段1
    */
    private String remark1;

    /**
    * 备用字段2
    */
    private String remark2;

    /**
    * 备用字段3
    */
    private String remark3;

    /**
    * 备用字段4
    */
    private String remark4;

    /**
    * 备用字段5
    */
    private String remark5;

    /**
    * 备用字段6
    */
    private String remark6;

    /**
    * 备用字段7
    */
    private String remark7;

    /**
    * 备用字段8
    */
    private String remark8;

    /**
    * 备用字段9
    */
    private String remark9;

    /**
    * 备用字段10
    */
    private String remark10;

    /**
    * 扩展字段
    */
    private Map<String, Object> extFields;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = (null == userId) ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = (null == userName) ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = (null == userPassword) ? null : userPassword.trim();
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = (null == userRealname) ? null : userRealname.trim();
    }

    public String getUserPinyin() {
        return userPinyin;
    }

    public void setUserPinyin(String userPinyin) {
        this.userPinyin = (null == userPinyin) ? null : userPinyin.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = (null == userSex) ? null : userSex.trim();
    }

    public String getUserHometel() {
        return userHometel;
    }

    public void setUserHometel(String userHometel) {
        this.userHometel = (null == userHometel) ? null : userHometel.trim();
    }

    public String getUserWorktel() {
        return userWorktel;
    }

    public void setUserWorktel(String userWorktel) {
        this.userWorktel = (null == userWorktel) ? null : userWorktel.trim();
    }

    public String getUserWorkaddress() {
        return userWorkaddress;
    }

    public void setUserWorkaddress(String userWorkaddress) {
        this.userWorkaddress = (null == userWorkaddress) ? null
                                                         : userWorkaddress.trim();
    }

    public String getUserMobiletel1() {
        return userMobiletel1;
    }

    public void setUserMobiletel1(String userMobiletel1) {
        this.userMobiletel1 = (null == userMobiletel1) ? null
                                                       : userMobiletel1.trim();
    }

    public String getUserMobiletel2() {
        return userMobiletel2;
    }

    public void setUserMobiletel2(String userMobiletel2) {
        this.userMobiletel2 = (null == userMobiletel2) ? null
                                                       : userMobiletel2.trim();
    }

    public String getUserFax() {
        return userFax;
    }

    public void setUserFax(String userFax) {
        this.userFax = (null == userFax) ? null : userFax.trim();
    }

    public String getUserOicq() {
        return userOicq;
    }

    public void setUserOicq(String userOicq) {
        this.userOicq = (null == userOicq) ? null : userOicq.trim();
    }

    public Date getUserBirthday() {
        return (null == userBirthday) ? null : (Date) userBirthday.clone();
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = (null == userBirthday) ? null
                                                   : (Date) userBirthday.clone();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = (null == userEmail) ? null : userEmail.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = (null == userAddress) ? null : userAddress.trim();
    }

    public String getUserPostalcode() {
        return userPostalcode;
    }

    public void setUserPostalcode(String userPostalcode) {
        this.userPostalcode = (null == userPostalcode) ? null
                                                       : userPostalcode.trim();
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = (null == userIdcard) ? null : userIdcard.trim();
    }

    public Integer getUserIsvalid() {
        return userIsvalid;
    }

    public void setUserIsvalid(Integer userIsvalid) {
        this.userIsvalid = (null == userIsvalid) ? null : userIsvalid;
    }

    public Date getUserRegdate() {
        return (null == userRegdate) ? null : (Date) userRegdate.clone();
    }

    public void setUserRegdate(Date userRegdate) {
        this.userRegdate = (null == userRegdate) ? null
                                                 : (Date) userRegdate.clone();
    }

    public Integer getUserLogincount() {
        return userLogincount;
    }

    public void setUserLogincount(Integer userLogincount) {
        this.userLogincount = (null == userLogincount) ? null : userLogincount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = (null == userType) ? null : userType.trim();
    }

    public Date getPastTime() {
        return (null == pastTime) ? null : (Date) pastTime.clone();
    }

    public void setPastTime(Date pastTime) {
        this.pastTime = (null == pastTime) ? null : (Date) pastTime.clone();
    }

    public String getDredgeTime() {
        return dredgeTime;
    }

    public void setDredgeTime(String dredgeTime) {
        this.dredgeTime = (null == dredgeTime) ? null : dredgeTime.trim();
    }

    public Date getLastloginDate() {
        return (null == lastloginDate) ? null : (Date) lastloginDate.clone();
    }

    public void setLastloginDate(Date lastloginDate) {
        this.lastloginDate = (null == lastloginDate) ? null
                                                     : (Date) lastloginDate.clone();
    }

    public String getWorklength() {
        return worklength;
    }

    public void setWorklength(String worklength) {
        this.worklength = (null == worklength) ? null : worklength.trim();
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = (null == politics) ? null : politics.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = (null == loginIp) ? null : loginIp.trim();
    }

    public String getCertSn() {
        return certSn;
    }

    public void setCertSn(String certSn) {
        this.certSn = (null == certSn) ? null : certSn.trim();
    }

    public Integer getUserSn() {
        return userSn;
    }

    public void setUserSn(Integer userSn) {
        this.userSn = (null == userSn) ? null : userSn;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = (null == remark1) ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = (null == remark2) ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = (null == remark3) ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = (null == remark4) ? null : remark4.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = (null == remark5) ? null : remark5.trim();
    }

    public String getRemark6() {
        return remark6;
    }

    public void setRemark6(String remark6) {
        this.remark6 = (null == remark6) ? null : remark6.trim();
    }

    public String getRemark7() {
        return remark7;
    }

    public void setRemark7(String remark7) {
        this.remark7 = (null == remark7) ? null : remark7.trim();
    }

    public String getRemark8() {
        return remark8;
    }

    public void setRemark8(String remark8) {
        this.remark8 = (null == remark8) ? null : remark8.trim();
    }

    public String getRemark9() {
        return remark9;
    }

    public void setRemark9(String remark9) {
        this.remark9 = (null == remark9) ? null : remark9.trim();
    }

    public String getRemark10() {
        return remark10;
    }

    public void setRemark10(String remark10) {
        this.remark10 = (null == remark10) ? null : remark10.trim();
    }

    public Map<String, Object> getExtFields() {
        return extFields;
    }

    public void setExtFields(Map<String, Object> extFields) {
        this.extFields = (null == extFields) ? null : extFields;
    }
}
