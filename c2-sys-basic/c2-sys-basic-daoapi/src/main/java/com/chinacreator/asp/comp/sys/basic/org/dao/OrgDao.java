package com.chinacreator.asp.comp.sys.basic.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

@Repository
public interface OrgDao {

	/**
	 * 通过机构Id查询机构所在的用户组ID
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 用户组ID
	 */
	public String queryGroupIdByOrgId(@Param("orgId") String orgId);

	/**
	 * 通过机构Id数组查询机构所在的用户组ID数组
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @return 用户组ID集合
	 */
	public List<String> queryGroupIdsByOrgIds(@Param("orgIds") String[] orgIds);

	/**
	 * 通过用户组ID数组查询对应的机构列表
	 * 
	 * @param groupIds
	 *            用户组机构ID数组
	 * @return 机构列表
	 */
	public List<OrgEO> queryByGroupIds(List<String> groupIds);

	/**
	 * 通过用户ID查询用户的主机构信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户所在主机构信息
	 */
	public OrgEO queryMainOrg(@Param("userId") String userId);

	/**
	 * 查询机构列表
	 * 
	 * @param orgEO
	 *            机构数据库访问对象
	 * @return 机构列表
	 */
	public List<OrgEO> query(@Param("orgEO") OrgEO orgEO);

	/**
	 * 查询机构列表（带分页）
	 * 
	 * @param orgEO
	 *            机构数据库访问对象
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的机构信息
	 */
	public Page<OrgEO> query(@Param("orgEO") OrgEO orgEO, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询用户是否指定机构的创建者
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 大于0为是，否则为否
	 */
	public int isOrgSCreator(@Param("userId") String userId,
			@Param("orgId") String orgId);

	/**
	 * 判断用户是否是机构管理员
	 * 
	 * @param userId
	 *            用户ID
	 * @param orgId
	 *            机构ID
	 * @return 大于0为是，否则为否
	 */
	public int isOrgMgr(@Param("userId") String userId,
			@Param("orgId") String orgId, @Param("roleId") String roleId);

	/**
	 * 判断机构名称是否存在（全局唯一）
	 * 
	 * @param orgName
	 *            机构名称
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgName(@Param("orgName") String orgName);

	/**
	 * 判断机构名称是否存在(忽略指定机构ID名称，编辑时用)
	 * 
	 * @param orgName
	 *            机构名称
	 * @param orgId
	 *            机构ID
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgNameIgnoreOrgID(@Param("orgName") String orgName,
			@Param("orgId") String orgId);

	/**
	 * 判断机构显示名称是否存在（同级唯一）
	 * 
	 * @param parentId
	 *            父机构ID
	 * @param orgShowName
	 *            机构显示名称
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgShowName(@Param("parentId") String parentId,
			@Param("orgShowName") String orgShowName);

	/**
	 * 判断机构显示名称是否存在（同级唯一）
	 * 
	 * @param orgId
	 *            机构ID
	 * @param orgShowName
	 *            机构显示名称
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgShowNameOnly(@Param("orgId") String orgId,
			@Param("orgShowName") String orgShowName);

	/**
	 * 判断机构显示名称是否存在(忽略指定机构ID名称，编辑时用)
	 * 
	 * @param orgId
	 *            机构ID
	 * @param orgShowName
	 *            机构显示名称
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgShowNameOnlyIgnoreOrgID(@Param("orgId") String orgId,
			@Param("orgShowName") String orgShowName);

	/**
	 * 判断机构编号是否存在（全局唯一 ）
	 * 
	 * @param orgNumber
	 *            机构编号
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgNumber(@Param("orgNumber") String orgNumber);

	/**
	 * 判断机构编号是否存在(忽略指定机构ID名称，编辑时用)
	 * 
	 * @param orgNumber
	 *            机构编号
	 * @param orgId
	 *            机构ID
	 * @return 大于0表示不唯一，否则唯一
	 */
	public int existsByOrgNumberIgnoreOrgID(
			@Param("orgNumber") String orgNumber, @Param("orgId") String orgId);

	/**
	 * 创建机构
	 * 
	 * @param orgEO
	 *            机构数据库访问对象
	 * @return 影响的记录行数
	 */
	public int create(OrgEO orgEO);

	/**
	 * 修改机构
	 * 
	 * @param orgEO
	 *            机构数据库访问对象
	 * @return 影响的记录行数
	 */
	public int update(OrgEO orgEO);

	/**
	 * 查询所有机构
	 * 
	 * @return 所有机构列表
	 */
	public List<OrgEO> queryAll();

	/**
	 * 查询所有机构（带分页）
	 * 
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页的机构列表
	 */
	public Page<OrgEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 通过机构ID查询机构信息
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构信息
	 */
	public OrgEO queryByPK(@Param("orgId") String orgId);

	/**
	 * 查询当前机构及其父机构（递归）
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构列表
	 */
	public List<OrgEO> queryFatherOrgs(@Param("orgId") String orgId);

	/**
	 * 查询当前机构及其父机构（递归、分页）
	 * 
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的机构列表
	 */
	public Page<OrgEO> queryFatherOrgs(@Param("orgId") String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询当前机构及其父机构（不递归）
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构列表
	 */
	public List<OrgEO> queryFatherOrgsUnRecursive(@Param("orgId") String orgId);

	/**
	 * 查询当前机构及其父机构（不递归、分页）
	 * 
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的机构列表
	 */
	public Page<OrgEO> queryFatherOrgsUnRecursive(@Param("orgId") String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询指定机构的子机构（递归）
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构列表
	 */
	public List<OrgEO> queryChildOrgs(@Param("orgId") String orgId);

	/**
	 * 查询指定机构的子机构（递归、带分页）
	 * 
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的机构列表
	 */
	public Page<OrgEO> queryChildOrgs(@Param("orgId") String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询指定机构的子机构（不递归）
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构列表
	 */
	public List<OrgEO> queryChildOrgsUnRecursive(@Param("orgId") String orgId);

	/**
	 * 查询指定机构的子机构（不递归、带分页）
	 * 
	 * @param orgId
	 *            机构ID
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 分页后的机构列表
	 */
	public Page<OrgEO> queryChildOrgsUnRecursive(@Param("orgId") String orgId,
			Pageable pageable, Sortable sortable);

	/**
	 * 修改机构排序号
	 * 
	 * @param orgEO
	 *            机构数据库访问对象
	 * @return 影响的行数
	 */
	public int setOrder(OrgEO orgEO);

	/**
	 * 通过机构ID删除机构信息
	 * 
	 * @param orgIds
	 *            机构ID数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(String... orgIds);

	/**
	 * 判断指定机构是否有子机构
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 大于0：有子机构，否则：没有子机构
	 */
	public int existsChildOrgs(@Param("orgId") String orgId);
}
