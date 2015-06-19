package com.chinacreator.asp.comp.sys.core.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.user.eo.UserEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 用户数据访问接口
 * 
 * @author 彭盛
 * 
 */
@Repository
public interface UserDao {

	/**
	 * 新增用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @return 数据库执行操作影响到的行数
	 * 
	 */
	public int create(UserEO userEO);

	/**
	 * 修改用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int update(UserEO userEO);

	/**
	 * 设置用户排序
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 */
	public void setOrder(UserEO userEO);

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param userPassword
	 *            用户密码
	 */
	public void updatePasswordByUserId(@Param("userId") String userId,
			@Param("userPassword") String userPassword);

	/**
	 * 批量修改用户密码
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param userPassword
	 *            用户密码
	 */
	public void updatePasswordByUserIds(@Param("userIds") String[] userIds,
			@Param("userPassword") String userPassword);

	/**
	 * 修改用户密码
	 * 
	 * @param userName
	 *            用户ID
	 * @param userPassword
	 *            用户密码
	 */
	public void updatePasswordByUserName(@Param("userName") String userName,
			@Param("userPassword") String userPassword);

	/**
	 * 批量删除用户
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByPKs(String... userIds);

	/**
	 * 查询所有用户
	 * 
	 * @return 所有用户<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryAll();

	/**
	 * 查询所有用户（带分页）
	 * 
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 分页后的用户
	 */
	public Page<UserEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByUser(@Param("userEO") UserEO userEO);

	/**
	 * 查询用户
	 * 
	 * @param userEO
	 *            用户数据访问对象
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public Page<UserEO> queryByUser(@Param("userEO") UserEO userEO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户数据访问对象<br>
	 *         没查询到的情况下返回null
	 */
	public UserEO queryByPK(String userId);

	/**
	 * 查询用户
	 * 
	 * @param userName
	 *            用户账号
	 * @return 用户数据访问对象<br>
	 *         没查询到的情况下返回null
	 */
	public UserEO queryByUserName(String userName);

	/**
	 * 查询用户
	 * 
	 * @param userRealname
	 *            用户实名
	 * @return 用户数据访问对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByUserRealName(String userRealname);

	/**
	 * 查询指定用户活动范围下的所有用户
	 * 
	 * @param scopeType
	 *            用户活动范围类型（0：全局，1：机构，2：岗位）
	 * @param scopeId
	 *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
	 *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
	 * @return 用户数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<UserEO> queryByScope(@Param("scopeType") String scopeType,
			@Param("scopeId") String scopeId);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userId
	 *            用户ID
	 * @return 大于0:存在，0:不存在
	 */
	public int existsByPK(String userId);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userName
	 *            用户帐号
	 * @return 大于0:存在，0:不存在
	 */
	public int existsByUserName(String userName);

	/**
	 * 判断用户是否存在
	 * 
	 * @param userRealname
	 *            用户实名
	 * @return 大于0:存在，0:不存在
	 */
	public int existsByUserRealname(String userRealname);
}
