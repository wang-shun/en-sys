package com.chinacreator.sysmgr.usermgr.bean;

import com.chinacreator.sysmgr.utils.Common;;

public class UserBean {
	private String orgName;
	private String userName ;
	private String userRealname ;
	private String userPassword ;
	private String userPinyin ;
	private String userBirthday ;
	private String userSex ;
	private String userIdcard ;
	private String userMobiletel1 ;
	private String userOicq ;
	private String userEmail ;
	private String userAddress ;
	private String userPostalcode ;
	private String userHometel ;
	private String userWorktel ;
	private String userFax ;
	private String userWorkaddress ;
	private String worklength ;
	private String Politics ;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRealname() {
		return userRealname;
	}
	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	
	public String getUserPinyin() {
		return userPinyin;
	}
	public void setUserPinyin(String userPinyin) {
		this.userPinyin = userPinyin;
	}
	
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserIdcard() {
		return userIdcard;
	}
	public void setUserIdcard(String userIdcard) {
		//this.userIdcard = userIdcard;
		if(Common.isNumric(userIdcard)){
			this.userIdcard = Common.subDoubleZero(userIdcard);
		}else{
			this.userIdcard = userIdcard;
		}
		
	}
	public String getUserMobiletel1() {
		return userMobiletel1;
	}
	public void setUserMobiletel1(String userMobiletel1) {
		//this.userMobiletel1 = userMobiletel1;
		if(Common.isNumric(userMobiletel1)){
			this.userMobiletel1 = Common.subDoubleZero(userMobiletel1);
		}else{
			this.userMobiletel1 = userMobiletel1;
		}
	}
	public String getUserOicq() {
		return userOicq;
	}
	public void setUserOicq(String userOicq) {
		//this.userOicq = userOicq;
		if(Common.isNumric(userOicq)){
			this.userOicq = Common.subDoubleZero(userOicq);
		}else{
			this.userOicq = userOicq;
		}
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserPostalcode() {
		return userPostalcode;
	}
	public void setUserPostalcode(String userPostalcode) {
		//this.userPostalcode = userPostalcode;
		if(Common.isNumric(userPostalcode)){
			this.userPostalcode = Common.subDoubleZero(userPostalcode);
		}else{
			this.userPostalcode = userPostalcode;
		}
	}
	public String getUserHometel() {
		return userHometel;
	}
	public void setUserHometel(String userHometel) {
		//this.userHometel = userHometel;
		if(Common.isNumric(userHometel)){
			this.userHometel = Common.subDoubleZero(userHometel);
		}else{
			this.userHometel = userHometel;
		}
	}
	public String getUserWorktel() {
		return userWorktel;
	}
	public void setUserWorktel(String userWorktel) {
		this.userWorktel = userWorktel;
	}
	public String getUserFax() {
		return userFax;
	}
	public void setUserFax(String userFax) {
		//this.userFax = userFax;
		if(Common.isNumric(userFax)){
			this.userFax = Common.subDoubleZero(userFax);
		}else{
			this.userFax = userFax;
		}
	}
	public String getUserWorkaddress() {
		return userWorkaddress;
	}
	public void setUserWorkaddress(String userWorkaddress) {
		this.userWorkaddress = userWorkaddress;
	}
	public String getWorklength() {
		return worklength;
	}
	public void setWorklength(String worklength) {
		//this.worklength = worklength;
		if(Common.isNumric(worklength)){
			this.worklength = Common.subDoubleZero(worklength);
		}else{
			this.worklength = worklength;
		}
	}
	public String getPolitics() {
		return Politics;
	}
	public void setPolitics(String politics) {
		Politics = politics;
	}
}
