package com.chinacreator.asp.comp.sys.basic.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.log.eo.LogConfigEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 日志配置数据访问接口
 * 
 * @author 杨祎程
 * 
 */

@Repository
public interface LogConfigDao {

	/**
	 * 新增记录日志配置
	 * 
	 * @param logConfigEO
	 *            日志配置对象
	 * @return 影响记录条数
	 */
	public int create(LogConfigEO logConfigEO);

	/**
	 * 查询所有日志配置数据
	 * 
	 * @return 所有日志配置信息
	 */
	public List<LogConfigEO> queryAll();

	/**
	 * 查询所有日志配置数据
	 * 
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 所有日志配置信息
	 */
	public Page<LogConfigEO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 查询日志配置数据
	 * 
	 * @param logConfigEO
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @param sortable
	 *            排序参数
	 * @return 日志配置信息列表
	 */
	public Page<LogConfigEO> query(
			@Param("logConfigEO") LogConfigEO logConfigEO, Pageable pageable,
			Sortable sortable);

	/**
	 * 查询日志配置
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return 日志配置信息数据库访问对象
	 */
	public LogConfigEO queryByPK(@Param("operModule") String operModule);

	/**
	 * 查询日志配置
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return 日志配置数据对象<br>
	 *         没查询到的情况下返回null
	 */
	public LogConfigEO queryByLogTypeAndLogOper(
			@Param("logType") String logType, @Param("logOper") String logOper);

	/**
	 * 判断日志模块是否存在
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return 大于0:存在，否则不存在
	 */
	public int existsByOperModule(@Param("operModule") String operModule);

	/**
	 * 判断日志模块是否存在
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return 大于0:存在，否则不存在
	 */
	public int existsByLogTypeAndLogOper(@Param("logType") String logType,
			@Param("logOper") String logOper);

	/**
	 * 判断日志模块是否可记录日志
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return 大于0:可记录，否则不可记录
	 */
	public int isLogEnabledByOperModule(@Param("operModule") String operModule);

	/**
	 * 判断日志模块是否可记录日志
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return 大于0:可记录，否则不可记录
	 */
	public int isLogEnabledByLogTypeAndLogOper(
			@Param("logType") String logType, @Param("logOper") String logOper);

	/**
	 * 修改日志记录状态
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @param logEnabled
	 *            记录日志状态（true:可记录，false:不可记录）
	 * @return 影响记录条数
	 */
	public int updateLogEnabled(@Param("operModule") String operModule,
			@Param("logEnabled") boolean logEnabled);

}
