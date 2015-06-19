package com.chinacreator.asp.comp.sys.basic.userpreferences.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.userpreferences.eo.UserPreferencesEO;

/**
 * 用户偏好设置数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface UserPreferencesDao {

	/**
	 * 新增用户偏好设置信息
	 * 
	 * @param userPreferencesEO
	 *            用户偏好设置信息数据访问对象类
	 * @return 影响的行数
	 */
	public int create(UserPreferencesEO userPreferencesEO);

	/**
	 * 修改用户偏好设置信息
	 * 
	 * @param userPreferencesEO
	 *            用户偏好设置信息数据访问对象类
	 * @return 影响的行数
	 */
	public int update(UserPreferencesEO userPreferencesEO);

	/**
	 * 删除用户偏好设置信息
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param infoNames
	 *            信息名称数组
	 * @return 影响的行数
	 */
	public int deleteByPKs(@Param("userIds") String[] userIds,
			@Param("infoNames") String[] infoNames);

	/**
	 * 删除用户偏好设置信息
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @return 影响的行数
	 */
	public int deleteByUserIDs(@Param("userIds") String... userIds);

	/**
	 * 删除用户偏好设置信息
	 * 
	 * @param infoNames
	 *            信息名称数组
	 * @return 影响的行数
	 */
	public int deleteByInfoNames(@Param("infoNames") String... infoNames);

	/**
	 * 查询所有用户偏好设置信息
	 * 
	 * @return 用户偏好设置信息数据访问对象列表
	 */
	public List<UserPreferencesEO> queryAll();

	/**
	 * 查询用户偏好设置信息
	 * 
	 * @param userSettingDTO
	 * @return 用户偏好设置信息数据访问对象列表
	 */
	public List<UserPreferencesEO> queryByUserPreferences(
			@Param("userPreferencesEO") UserPreferencesEO userPreferencesEO);

	/**
	 * 查询用户偏好设置信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param infoName
	 *            信息名称
	 * @return 用户偏好设置信息数据访问对象类
	 */
	public UserPreferencesEO queryByPK(@Param("userId") String userId,
			@Param("infoName") String infoName);

	/**
	 * 判断用户偏好设置信息是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @param infoName
	 *            信息名称
	 * @return 大于0：存在，小于等于0：不存在
	 */
	public int existsByInfoName(@Param("userId") String userId,
			@Param("infoName") String infoName);
}
