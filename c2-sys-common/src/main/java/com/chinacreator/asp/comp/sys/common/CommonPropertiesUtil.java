package com.chinacreator.asp.comp.sys.common;

import com.chinacreator.c2.config.ConfigManager;

/**
 * 通用properties文件工具类
 * 
 * @author 彭盛
 * 
 */
public class CommonPropertiesUtil {

	/**
	 * 获取超级管理员用户ID
	 * 
	 * @return 超级管理员用户ID
	 */
	public static String getAdminUserId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ADMINUSERID);
		return (null != id && !id.trim().equals("")) ? id : "-1";
	}

	/**
	 * 获取超级管理员用户帐号
	 * 
	 * @return 超级管理员用户帐号
	 */
	public static String getAdminUserName() {
		String name = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ADMINUSERNAME);
		return (null != name && !name.trim().equals("")) ? name : "admin";
	}

	/**
	 * 获取匿名角色类型ID
	 * 
	 * @return 匿名角色类型ID
	 */
	public static String getAnonymousRoleTypeId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ANONYMOUSROLETYPEID);
		return (null != id && !id.trim().equals("")) ? id : "-1";
	}

	/**
	 * 获取超级管理员角色ID
	 * 
	 * @return 超级管理员角色ID
	 */
	public static String getAdministratorRoleId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ADMINISTRATORROLEID);
		return (null != id && !id.trim().equals("")) ? id : "1";
	}

	/**
	 * 获取超级管理员角色名称
	 * 
	 * @return 超级管理员角色名称
	 */
	public static String getAdministratorRoleName() {
		String name = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ADMINISTRATORROLENAME);
		return (null != name && !name.trim().equals("")) ? name
				: "administrator";
	}

	/**
	 * 获取每个用户都缺省拥有的角色ID
	 * 
	 * @return 每个用户都缺省拥有的角色ID
	 */
	public static String getRoleofeveryoneRoleId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ROLEOFEVERYONEROLEID);
		return (null != id && !id.trim().equals("")) ? id : "2";
	}

	/**
	 * 获取每个用户都缺省拥有的角色名称
	 * 
	 * @return 每个用户都缺省拥有的角色名称
	 */
	public static String getRoleofeveryoneRoleName() {
		String name = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ROLEOFEVERYONEROLENAME);
		return (null != name && !name.trim().equals("")) ? name
				: "roleofeveryone";
	}

	/**
	 * 获取机构管理员角色ID
	 * 
	 * @return 机构管理员角色ID
	 */
	public static String getOrgManagerRoleId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ORGMANAGERROLEID);
		return (null != id && !id.trim().equals("")) ? id : "3";
	}

	/**
	 * 获取机构管理员角色名称
	 * 
	 * @return 机构管理员角色名称
	 */
	public static String getOrgManagerRoleName() {
		String name = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ORGMANAGERROLENAME);
		return (null != name && !name.trim().equals("")) ? name : "orgmanager";
	}

	/**
	 * 获取管理员岗位ID
	 * 
	 * @return 管理员岗位ID
	 */
	public static String getAdministratorJobId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ADMINISTRATORJOBID);
		return (null != id && !id.trim().equals("")) ? id : "1";
	}

	/**
	 * 获取普通用户岗位ID
	 * 
	 * @return 普通用户岗位ID
	 */
	public static String getRoleofeveryoneJobId() {
		String id = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ROLEOFEVERYONEJOBID);
		return (null != id && !id.trim().equals("")) ? id : "2";
	}

	/**
	 * 获取默认密码
	 * 
	 * @return 默认密码
	 */
	public static String getDefaultPwd() {
		String defaultPwd = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_DEFAULTPWD);
		return (null != defaultPwd && !defaultPwd.trim().equals("")) ? defaultPwd
				: "123456";
	}

	/**
	 * 获取登录页面路径
	 * 
	 * @return 登录页面路径
	 */
	public static String getLoginUrl() {
		String loginUrl = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_LOGINURL);
		return (null != loginUrl && !loginUrl.trim().equals("")) ? loginUrl
				: "/login.html";
	}

	/**
	 * 获取删除所属主机构下用户方式
	 * 
	 * @return 删除所属主机构下用户方式<br>
	 *         all：&nbsp当前机构为用户所属主机构时，同时删除其他所属机构下用户；<br>
	 *         &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp当前机构为用户非主机构时，仅删除指定机构下用户。<br>
	 *         only：仅删除指定机构下用户。<br>
	 */
	public static String getDelMainOrgUserMode() {
		String delMainOrgUserMode = ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_DELMAINORGUSERMODE);
		return (null != delMainOrgUserMode && !delMainOrgUserMode.trim()
				.equals("")) ? delMainOrgUserMode : "all";
	}

	/**
	 * 机构名称是否唯一
	 * 
	 * @return true：机构名称必须唯一，新增修改机构前进行机构名称唯一性校验<br>
	 *         false：机构名称可以重复，新增修改机构前不进行机构名称唯一性校验
	 */
	public static boolean isUniqueOrgName() {
		return !"false".equals(ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ISUNIQUEORGNAME));
	}

	/**
	 * 机构显示名称是否同级唯一
	 * 
	 * @return true：机构显示名称必须同级唯一，新增修改机构前进行机构显示名称同级唯一性校验<br>
	 *         false：机构显示名称可以重复，新增修改机构前不进行机构显示名称唯一性校验
	 */
	public static boolean isUniqueOrgShowName() {
		return !"false".equals(ConfigManager.getInstance().getConfig(
				CommonConstants.sfs_SYSMGT_ISUNIQUEORGSHOWNAME));
	}
}
