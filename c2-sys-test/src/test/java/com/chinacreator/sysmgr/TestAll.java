package com.chinacreator.sysmgr;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.openqa.selenium.WebDriver;

import com.chinacreator.sysmgr.login.testcase.login;
import com.chinacreator.sysmgr.login.testcase.logout;
import com.chinacreator.sysmgr.orgmgr.testcase.OpenOrgMgr;
import com.chinacreator.sysmgr.orgmgr.testcase.addorg.exception.AddOrgXlsException;
import com.chinacreator.sysmgr.orgmgr.testcase.addorg.normal.AddOrgXls;
import com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal.DelOrg;
import com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal.DelOrg_Multiple;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgNameAgain;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgNameIsNull;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgNumberAgain;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgNumberIsNull;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgShowNameAgain;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_OrgShowNameIsNull;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception.EditOrg_XZQHIsNull;
import com.chinacreator.sysmgr.orgmgr.testcase.editorg.normal.EditOrg;
import com.chinacreator.sysmgr.resourcemgr.testcase.OpenResMgr;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.exception.AddMenu_CodeIsNull;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.exception.AddMenu_NameIsNull;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal.AddMenu_NewPage;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal.AddMenu_div;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal.AddMenu_div_DotDisplay;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal.AddMenu_iframe;
import com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal.DelMenu;
import com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal.DelMenu_multiple;
import com.chinacreator.sysmgr.resourcemgr.testcase.editresource.exception.EditMenu_NameIsNull;
import com.chinacreator.sysmgr.resourcemgr.testcase.editresource.normal.EditMenu;
import com.chinacreator.sysmgr.resourcemgr.testcase.impresource.normal.impresource;
import com.chinacreator.sysmgr.rolemgr.testcase.OpenRoleMgr;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception.AddRole_RoleNameIsAgain;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception.AddRole_RoleNameIsNull;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal.AddRole;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal.AddRole_IsNotUse;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal.AddRole_Res;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.delrole.normal.DelRole;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.delrole.normal.DelRole_Multiple;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.editrole.exception.EditRole_RoleNameIsAgain;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.editrole.exception.EditRole_RoleNameIsNull;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.editrole.normal.EditRole;
import com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.exception.AddRoleType_TypeNameIsAgain;
import com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.exception.AddRoleType_TypeNameIsNull;
import com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.normal.AddRoleType;
import com.chinacreator.sysmgr.usermgr.testcase.OpenUserMgr;
import com.chinacreator.sysmgr.usermgr.testcase.adduser.Exception.AddUserXlsException;
import com.chinacreator.sysmgr.usermgr.testcase.adduser.normal.AddUserXls;
import com.chinacreator.sysmgr.usermgr.testcase.deluser.normal.DelUser;
import com.chinacreator.sysmgr.usermgr.testcase.deluser.normal.DelUser_Multiple;
import com.chinacreator.sysmgr.usermgr.testcase.edituser.exception.EditUser_NochooseUser;
import com.chinacreator.sysmgr.usermgr.testcase.edituser.exception.EditUser_UserRealnameIsNull;
import com.chinacreator.sysmgr.usermgr.testcase.edituser.normal.EditUser;
import com.chinacreator.sysmgr.usermgr.testcase.orderuser.normal.OrderUser;
import com.chinacreator.sysmgr.usermgr.testcase.resetpassword.normal.ResetPwd;
import com.chinacreator.sysmgr.usermgr.testcase.search.normal.UserSearch;
import com.chinacreator.sysmgr.usermgr.testcase.usersettingorg.normal.UserSettingOrg;
import com.chinacreator.sysmgr.usermgr.testcase.usersettingorg.normal.UserSettingOrg_NotOldOrg;


public class TestAll {

	public static WebDriver driver;
	public static String baseUrl;
	public static String nodeUrl;
	private boolean acceptNextAlert = true;
	public static StringBuffer verificationErrors = new StringBuffer();

	
	public static Test suite(){
		//grid-hub地址
		nodeUrl = "http://172.16.25.157:4444/wd/hub";
		//nodeUrl = "http://172.16.72.5:32768/wd/hub";
		
		//测试应用访问地址
		baseUrl = "http://172.16.25.21:8080";
		
		TestSuite suite = new TestSuite();
		suite.addTestSuite(login.class);
		
		//=======================机构管理=================================
		//打开机构管理页
		suite.addTestSuite(OpenOrgMgr.class);
		//新增机构-正常流
		suite.addTestSuite(AddOrgXls.class);
		//新增机构-异常流
		suite.addTestSuite(AddOrgXlsException.class);
		//编辑机构-正常流
		suite.addTestSuite(EditOrg.class);
		//编辑机构-重置
		//suite.addTestSuite(EditOrg_Resetting.class);
		//编辑机构-机构名称为空
		suite.addTestSuite(EditOrg_OrgNameIsNull.class);
		//编辑机构-机构名称重复
		suite.addTestSuite(EditOrg_OrgNameAgain.class);
		//编辑机构-机构编号为空
		suite.addTestSuite(EditOrg_OrgNumberIsNull.class);
		//编辑机构-机构编号重复
		suite.addTestSuite(EditOrg_OrgNumberAgain.class);
		//编辑机构-机构显示名称为空
		suite.addTestSuite(EditOrg_OrgShowNameIsNull.class);
		//编辑机构-同一机构下机构显示名称重复
		suite.addTestSuite(EditOrg_OrgShowNameAgain.class);
		//编辑机构-行政区号为空
		suite.addTestSuite(EditOrg_XZQHIsNull.class);
		
		
		//=======================用户管理=================================
		//打开用户管理页
		suite.addTestSuite(OpenUserMgr.class);
		//新增用户-正常流
		suite.addTestSuite(AddUserXls.class);
		//新增用户-异常流
		suite.addTestSuite(AddUserXlsException.class);
		//编辑用户-正常流
		suite.addTestSuite(EditUser.class);
		//编辑用户-用户姓名为空
		suite.addTestSuite(EditUser_UserRealnameIsNull.class);
		//编辑用户-未选择用户
		suite.addTestSuite(EditUser_NochooseUser.class);
		//用户排序
		suite.addTestSuite(OrderUser.class);
		//用户查询
		suite.addTestSuite(UserSearch.class);
		//用户重置密码
		suite.addTestSuite(ResetPwd.class);
		//用户设置主机构-用户保留在原机构下
		suite.addTestSuite(UserSettingOrg.class);
		//用户设置主机构-用户不保留在原机构下
		suite.addTestSuite(UserSettingOrg_NotOldOrg.class);
			
		
		
		//=======================资源管理=================================
		//打开资源管理页
		suite.addTestSuite(OpenResMgr.class);
		//新增菜单-新页面方式
		suite.addTestSuite(AddMenu_NewPage.class);
		//新增菜单-div方式
		suite.addTestSuite(AddMenu_div.class);
		//新增菜单-不显示
		suite.addTestSuite(AddMenu_div_DotDisplay.class);
		//新增菜单-iframe方式
		suite.addTestSuite(AddMenu_iframe.class);
		//新增菜单-资源编码为空
		suite.addTestSuite(AddMenu_CodeIsNull.class);
		//新增菜单-资源名称为空
		suite.addTestSuite(AddMenu_NameIsNull.class);
		//编辑菜单-正常流
		suite.addTestSuite(EditMenu.class);
		//编辑菜单-资源名称为空
		suite.addTestSuite(EditMenu_NameIsNull.class);
		//资源导入
		suite.addTestSuite(impresource.class);
		

		
		//=======================角色管理=================================
		//打开角色管理页
		suite.addTestSuite(OpenRoleMgr.class);
		//新增角色类型-正常流
		suite.addTestSuite(AddRoleType.class);
		//新增角色类型-异常流
		suite.addTestSuite(AddRoleType_TypeNameIsAgain.class);
		suite.addTestSuite(AddRoleType_TypeNameIsNull.class);
		//新增角色-正常流
		suite.addTestSuite(AddRole.class);
		suite.addTestSuite(AddRole_IsNotUse.class);
		suite.addTestSuite(AddRole_Res.class);
		//新增角色-异常流
		suite.addTestSuite(AddRole_RoleNameIsNull.class);
		suite.addTestSuite(AddRole_RoleNameIsAgain.class);
		//编辑角色-正常流
		suite.addTestSuite(EditRole.class);
		//编辑角色-异常流
		suite.addTestSuite(EditRole_RoleNameIsNull.class);
		suite.addTestSuite(EditRole_RoleNameIsAgain.class);
		
//		
//		
//		
//		//=======================权限管理=================================
//		//无权限用户权限验证
//		suite.addTestSuite(logout.class);
//		suite.addTestSuite(login_zhangsan.class);
//		suite.addTestSuite(NoPrivilegeCheck.class);
//		suite.addTestSuite(logout_zhangsan.class);
//				
//		//角色授予用户权限验证
//		suite.addTestSuite(login.class);
//		suite.addTestSuite(OpenRoleMgr.class);
//		suite.addTestSuite(RoleSetPrivilege.class);
//		suite.addTestSuite(RoleToUser.class);
//		suite.addTestSuite(logout.class);
//		suite.addTestSuite(login_zhangsan.class);
//		suite.addTestSuite(RoleToUserCheck.class);
//		suite.addTestSuite(logout_zhangsan.class);
//		
//		//授予用户的角色权限回收
//		suite.addTestSuite(login.class);
//		suite.addTestSuite(OpenRoleMgr.class);
//		suite.addTestSuite(RoleSetPrivilege_recovery.class);
//		suite.addTestSuite(logout.class);
//		suite.addTestSuite(login_zhangsan.class);
//		suite.addTestSuite(NoPrivilegeCheck.class);
//		suite.addTestSuite(logout_zhangsan.class);
//		
//		//授予普通用户角色权限
//		suite.addTestSuite(login.class);
//		suite.addTestSuite(OpenRoleMgr.class);
//		suite.addTestSuite(RoleSetPrivilege_everyone.class);
//		suite.addTestSuite(login_zhangsan.class);
//		suite.addTestSuite(OpenMyInfo.class);
//		suite.addTestSuite(OpenMyPassword.class);
//		suite.addTestSuite(logout_zhangsan.class);
//		suite.addTestSuite(login_linlei.class);
//		suite.addTestSuite(OpenMyPassword.class);
//		suite.addTestSuite(logout_linlei.class);
//		
//		//角色授予给机构
//		suite.addTestSuite(login.class);
//		suite.addTestSuite(OpenRoleMgr.class);
//		suite.addTestSuite(RoleToOrg.class);
//		suite.addTestSuite(logout.class);
//		suite.addTestSuite(login_lilei.class);
//		suite.addTestSuite(OpenDicMgr.class);
//		suite.addTestSuite(logout_lilei.class);
//		suite.addTestSuite(login_linlei.class);
//		suite.addTestSuite(OpenDicMgr.class);
//		suite.addTestSuite(logout_linlei.class);
//		
//		
//		
//		
//		//=======================字典管理=================================
//		//打开字典管理页
//		suite.addTestSuite(OpenDicMgr.class);
//		//新增字典类型-正常流
//		suite.addTestSuite(AddDicType.class);
//		//新增字典类型-字典类型名称为空
//		suite.addTestSuite(AddDicType_TypeNameIsNull.class);
//		//新增字典类型-字典类型名称重复
//		suite.addTestSuite(AddDicType_TypeNameIsAgain.class);
//		//编辑字典类型-正常流
//		suite.addTestSuite(EditDicType.class);
//		//编辑字典类型-字典类型名称为空
//		suite.addTestSuite(EditDicType_TypeNameIsNull.class);
//		//编辑字典类型-字典类型名称重复
//		suite.addTestSuite(EditDicType_TypeNameIsAgain.class);
//		//新增字典数据-正常流
//		suite.addTestSuite(AddDicData.class);
//		//新增字典数据-数据名称为空
//		suite.addTestSuite(AddDicData_DicDataNameIsNull.class);
//		//新增字典数据-数据名称重复
//		suite.addTestSuite(AddDicData_DicDataNameIsAgain.class);
//		//编辑字典数据-正常流
//		suite.addTestSuite(EditDicData.class);
//		//编辑字典数据-数据名称为空
//		suite.addTestSuite(EditDicData_DicDataNameIsNull.class);
//		//字典数据排序
//		suite.addTestSuite(DicDataOrder.class);
//		//字典数据查询
//		suite.addTestSuite(DicDataSearch.class);
//		//删除字典数据-取消删除
//		suite.addTestSuite(DelDicData_Cancel.class);
//		//删除字典数据-确认删除一条记录
//		suite.addTestSuite(DelDicData.class);
//		//删除字典数据-全部删除
//		suite.addTestSuite(DelDicData_All.class);
//		//删除字典数据-未选择数据删除
//		suite.addTestSuite(DelDicData_NotChooseData.class);
//		//删除字典类型-取消删除
//		suite.addTestSuite(DelDicType_Cancel.class);
//		//删除字典类型-删除一条记录
//		suite.addTestSuite(DelDicType.class);
//		
//		
//		
//		
//		
//		
//		//=======================日志管理=================================
//		//打开日志管理页
//		suite.addTestSuite(OpenLogMgr.class);
//		//日志查询
//		suite.addTestSuite(LogQuery.class);
//		//日志详情
//		suite.addTestSuite(LogDetail.class);
//		//日志备份
//		suite.addTestSuite(LogBackUp.class);
//		//打开历史日志列表Tab页
//		suite.addTestSuite(OpenHisLogTab.class);
//		//查看历史日志详情
//		suite.addTestSuite(HisLogDel.class);
//		//历史日志查询
//		suite.addTestSuite(HisLogDetail.class);
//		//删除历史日志
//		suite.addTestSuite(HisLogDel.class);
//		//打开日志配置页
//		suite.addTestSuite(OpenLogConfigTab.class);
//		//修改日志配置项
//		suite.addTestSuite(LogConfigEdit.class);
//		//打开日志列表Tab页
//		suite.addTestSuite(OpenLogTab.class);
//		//日志备份
//		suite.addTestSuite(LogBackUp.class);
//		//刷新日志列表
//		suite.addTestSuite(LogRefresh.class);
//		//检查配置是否生效
//		suite.addTestSuite(checkLog.class);
//		
//		
		//=======================删除测试数据=================================
		//打开资源管理页
		suite.addTestSuite(OpenResMgr.class);
		//删除资源-删除一个菜单
		suite.addTestSuite(DelMenu.class);
		//删除资源-同时删除多个资源
		suite.addTestSuite(DelMenu_multiple.class);
		//打开角色管理页
		suite.addTestSuite(OpenRoleMgr.class);
		//删除一个角色
		suite.addTestSuite(DelRole.class);
		//删除多个角色
		suite.addTestSuite(DelRole_Multiple.class);
		//打开用户管理页
		suite.addTestSuite(OpenUserMgr.class);
		//删除一个用户
		suite.addTestSuite(DelUser.class);
		//删除所有用户
		suite.addTestSuite(DelUser_Multiple.class);
		//打开机构管理页
		suite.addTestSuite(OpenOrgMgr.class);
		//删除一个机构
		suite.addTestSuite(DelOrg.class);
		//删除多个机构
		suite.addTestSuite(DelOrg_Multiple.class);
		
		
		
		
		suite.addTestSuite(logout.class);
		return suite;
	
	  }
	public static void main(String args[]) {
		TestRunner.run(suite());
	}
	  
	 
	/*  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }*/
	}


