package com.chinacreator.c2.sys.sdk.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
import com.chinacreator.c2.web.exception.RequiredPropertyNotFoundException;
import com.chinacreator.c2.web.exception.ResourceNotFoundException;
import com.chinacreator.c2.web.exception.UniqueConstraintException;

/**
 * 机构服务接口
 * 
 * @author 彭盛
 */
public interface OrgnizationService {
	/**
	 * 新增机构 约束： 1.机构名称不能为空 2.父机构ID不能为空 3.机构编号不能为空
	 * 4.如果在c2-config.properties里面配置了sysMgt.isUniqueOrgName=true,机构名称必须全局唯一
	 * 5.机构编号必须全局唯一
	 * 6.如果在c2-config.properties里面配置了sysMgt.isUniqueOrgShowName=true
	 * ,在同一级机构下，机构显示名称必须唯一
	 * 
	 * @param org
	 *            机构数据传输对象
	 * @return 新增机构对象
	 * @exception UniqueConstraintException
	 *                违反唯一约束
	 * @exception PropertyRequiredException
	 *                必填属性为空
	 */
	public Organization create(Organization org) throws UniqueConstraintException, RequiredPropertyNotFoundException;

	/**
	 * 删除机构
	 * 
	 * @param orgIds
	 *            机构ID
	 * @throws SysResourcesException
	 *             删除机构时发生时错误，如果机构id不存在，不会抛出异常
	 */
	public void delete(String orgId) throws SysResourcesException;

	/**
	 * 更新机构信息，仅更新参数中的非空属性 参数中的非空属性的约束请参照创建{@link #create(Organization)}接口
	 * 
	 * @param orgId
	 *            要更新的机构ID
	 * @param org
	 *            机构数据传输对象
	 * @return 修改后的机构对象
	 * 
	 * @exception ResourceNotFoundException 要更新的资源不存在
	 * @exception UniqueConstraintException 有属性违反唯一约束
	 */
	public Organization update(String orgId, Organization org)
			throws ResourceNotFoundException,UniqueConstraintException;

	/**
	 * 替换机构信息，使用参数中的机构对象（包含空属性）整体替换库中现有的记录,如果机构id不存在则创建一条新纪录 参数中的机构对象的属性约束请参照创建
	 * {@link #create(Organization)}接口
	 * 
	 * @param orgId
	 *            机构ID
	 * @param org
	 *            机构数据传输对象
	 * @return 替换后的机构对象
	 */
	public Organization replace(String orgId, Organization org)
			throws UniqueConstraintException, RequiredPropertyNotFoundException;

	/**
	 * 查询机构
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构数据传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public Organization get(String orgId) throws ResourceNotFoundException;

	/**
	 * 查询子机构，不包含自身
	 * 
	 * @param orgId
	 *            机构ID
	 * @param cascade
	 *            是否递归查询所有子机构
	 * @return 子机构数据传输对象列表
	 */
	public List<Organization> getChildren(String orgId, boolean cascade);

	/**
	 * 查询当前机构及其所有父机构，不包含自身
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 当前机构及其父机构数据传输对象列表，第一个是顶级机构<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<Organization> getParents(String orgId);

	/**
	 * 判断机构是否拥有指定角色
	 * 
	 * @param orgId
	 *            机构ID
	 * @param roleId
	 *            角色ID
	 * @return true:有，false:无
	 */
	public boolean hasRole(String orgId, @NotNull String roleId);

	/**
	 * 查询机构
	 * 
	 * @param org
	 *            机构数据传输对象,null查所有机构
	 * @return 机构数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<Organization> query(Organization org);

	/**
	 * 判断机构下是否有指定用户
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return true:有，false:无
	 */
	public boolean containsUser(String orgId, String userId);
}
