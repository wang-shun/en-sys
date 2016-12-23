package com.chinacreator.asp.comp.sys.advanced.user.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 用户服务接口
 * 
 * @author 彭盛
 * 
 */
public interface UserService extends
		com.chinacreator.asp.comp.sys.basic.user.service.UserService {

	/**
	 * 给机构下用户添加岗位
	 * 
	 * @param userId
	 *            用户ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void addJobs(String userId, String... jobIds);

	/**
	 * 批量给机构下用户添加岗位
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void addJobs(String[] userIds, String[] jobIds);

	/**
	 * 给机构下用户设置岗位
	 * 
	 * @param userId
	 *            用户ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setJobs(String userId, String... jobIds);

	/**
	 * 批量给机构下用户设置岗位
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void setJobs(String[] userIds, String[] jobIds);

	/**
	 * 批量回收用户的所有岗位
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void removeAllJobs(String... userIds);

	/**
	 * 批量回收机构下用户的所有岗位
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void removeAllJobs(String orgId, String... userIds);

	/**
	 * 回收机构下用户的岗位
	 * 
	 * @param userId
	 *            用户ID
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void removeJobs(String userId, String... jobIds);

	/**
	 * 回收机构下用户的岗位
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void removeJobs(String[] userIds, String[] jobIds);

	/**
	 * 判断用户是否属于指定岗位
	 * 
	 * @param userId
	 *            用户ID
	 * @param jobId
	 *            岗位ID
	 * @return true:是，false:否
	 */
	public boolean belongsJob(String userId, String jobId);

	/**
	 * 判断机构下用户是否属于指定岗位
	 * 
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @param jobId
	 *            岗位ID
	 * @return true:是，false:否
	 */
	public boolean belongsJob(String orgId, String userId, String jobId);

	/**
	 * 查询指定机构岗位下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByOrgJob(UserDTO userDto, String orgId,
			String jobId);

	/**
	 * 分页查询指定机构岗位下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param orgId
	 *            机构ID
	 * @param jobId
	 *            岗位ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryByOrgJob(UserDTO userDto, String orgId,
			String jobId, Pageable pageable, Sortable sortable);

	/**
	 * 查询指定岗位下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param jobId
	 *            岗位ID
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserDTO> queryByJob(UserDTO userDto, String jobId);

	/**
	 * 分页查询指定岗位下用户
	 * 
	 * @param userDto
	 *            用户数据传输对象
	 * @param jobId
	 *            岗位ID
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<UserDTO> queryByJob(UserDTO userDto, String jobId,
			Pageable pageable, Sortable sortable);

}
