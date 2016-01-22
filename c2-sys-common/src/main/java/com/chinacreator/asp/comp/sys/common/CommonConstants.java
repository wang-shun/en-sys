package com.chinacreator.asp.comp.sys.common;

/**
 * 通用常量类
 * 
 * @author 彭盛
 * 
 */
public class CommonConstants {

	/**
	 * 系统管理事务管理器名称
	 */
	public static final String sfs_SYSMGT_TRANSACTIONMANAGER_NAME = "transactionManager";

	/**
	 * properties文件key:设置是否随机生成公开盐(true:生成,false:不生成)
	 */
	public static final String sfs_SYSMGT_ENCRYPTION_GENERATEPUBLICSALT = "encryption.generatePublicSalt";

	/**
	 * properties文件key:加密算法(NONE:不加密,MD2,MD5,SHA-1,SHA-256,SHA-384,SHA-512)
	 */
	public static final String sfs_SYSMGT_ENCRYPTION_HASHALGORITHMNAME = "encryption.hashAlgorithmName";

	/**
	 * properties文件key:设置进行哈希计算迭代的次数(默认:500000)
	 */
	public static final String sfs_SYSMGT_ENCRYPTION_HASHITERATIONS = "encryption.hashIterations";

	/**
	 * properties文件key:超级管理员用户ID
	 */
	public static final String sfs_SYSMGT_ADMINUSERID = "sysMgt.adminUserId";

	/**
	 * properties文件key:超级管理员用户帐号
	 */
	public static final String sfs_SYSMGT_ADMINUSERNAME = "sysMgt.adminUserName";

	/**
	 * properties文件key:匿名角色类型ID
	 */
	public static final String sfs_SYSMGT_ANONYMOUSROLETYPEID = "sysMgt.anonymousRoleTypeId";

	/**
	 * properties文件key:超级管理员角色ID
	 */
	public static final String sfs_SYSMGT_ADMINISTRATORROLEID = "sysMgt.administratorRoleId";

	/**
	 * properties文件key:超级管理员角色名称
	 */
	public static final String sfs_SYSMGT_ADMINISTRATORROLENAME = "sysMgt.administratorRoleName";

	/**
	 * properties文件key:每个用户都缺省拥有的角色ID
	 */
	public static final String sfs_SYSMGT_ROLEOFEVERYONEROLEID = "sysMgt.roleofeveryoneRoleId";

	/**
	 * properties文件key:每个用户都缺省拥有的角色名称
	 */
	public static final String sfs_SYSMGT_ROLEOFEVERYONEROLENAME = "sysMgt.roleofeveryoneRoleName";

	/**
	 * properties文件key:机构管理员角色ID
	 */
	public static final String sfs_SYSMGT_ORGMANAGERROLEID = "sysMgt.orgmanagerRoleId";

	/**
	 * properties文件key:机构管理员角色名称
	 */
	public static final String sfs_SYSMGT_ORGMANAGERROLENAME = "sysMgt.orgmanagerRoleName";

	/**
	 * properties文件key:管理员岗位ID
	 */
	public static final String sfs_SYSMGT_ADMINISTRATORJOBID = "sysMgt.administratorJobId";

	/**
	 * properties文件key:普通用户岗位ID
	 */
	public static final String sfs_SYSMGT_ROLEOFEVERYONEJOBID = "sysMgt.roleofeveryoneJobId";

	/**
	 * properties文件key:默认重置密码
	 */
	public static final String sfs_SYSMGT_DEFAULTPWD = "sysMgt.defaultPwd";

	/**
	 * properties文件key:登录页面路径
	 */
	public static final String sfs_SYSMGT_LOGINURL = "sysMgt.loginUrl";

	/**
	 * properties文件key:删除所属主机构下用户方式
	 */
	public static final String sfs_SYSMGT_DELMAINORGUSERMODE = "sysMgt.delMainOrgUserMode";

	/**
	 * properties文件key:机构名称是否唯一
	 */
	public static final String sfs_SYSMGT_ISUNIQUEORGNAME = "sysMgt.isUniqueOrgName";

	/**
	 * properties文件key:机构显示名称是否同级唯一
	 */
	public static final String sfs_SYSMGT_ISUNIQUEORGSHOWNAME = "sysMgt.isUniqueOrgShowName";

	/**
	 * properties文件key:用户登录成功，是否判断用户密码为默认密码时跳转修改密码页面
	 */
	public static final String sfs_SYSMGT_ISUPDATEDEFAULTPWD = "sysMgt.isUpdateDefaultPwd";

	/**
	 * properties文件key:修改默认密码页面地址
	 */
	public static final String sfs_SYSMGT_UPDATEDEFAULTPWDURL = "sysMgt.updateDefaultPwdUrl";

}
