package com.chinacreator.asp.comp.sys.basic.log.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.basic.log.dto.LogConfigDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 日志配置服务接口
 * 
 * @author 彭盛
 * 
 */
public interface LogConfigService {

	/**
	 * 新增日志配置
	 * 
	 * @param logConfigDTO
	 *            日志配置数据传输对象
	 */
	public void create(LogConfigDTO logConfigDTO);

	/**
	 * 查询所有日志配置
	 * 
	 * @return 日志配置数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<LogConfigDTO> queryAll();

	/**
	 * 分页查询所有日志配置
	 * 
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的日志配置数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<LogConfigDTO> queryAll(Pageable pageable, Sortable sortable);

	/**
	 * 分页查询日志模块
	 * 
	 * @param logConfigDTO
	 *            日志配置数据传输对象
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的日志配置数据传输对象列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<LogConfigDTO> queryByLogConfig(LogConfigDTO logConfigDTO,
			Pageable pageable, Sortable sortable);

	/**
	 * 查询日志配置
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return 日志配置传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public LogConfigDTO queryByPK(String operModule);

	/**
	 * 查询日志配置
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return 日志配置传输对象<br>
	 *         没查询到的情况下返回null
	 */
	public LogConfigDTO queryByLogTypeAndLogOper(String logType, String logOper);

	/**
	 * 判断日志模块是否存在
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByOperModule(String operModule);

	/**
	 * 判断日志模块是否存在
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return true:存在，false:不存在
	 */
	public boolean existsByLogTypeAndLogOper(String logType, String logOper);

	/**
	 * 判断日志模块是否可记录日志
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @return true:可记录，false:不可记录
	 */
	public boolean isLogEnabled(String operModule);

	/**
	 * 判断日志模块是否可记录日志
	 * 
	 * @param logType
	 *            日志类型
	 * @param logOper
	 *            日志操作ID
	 * @return true:可记录，false:不可记录
	 */
	public boolean isLogEnabled(String logType, String logOper);

	/**
	 * 修改日志记录状态
	 * 
	 * @param operModule
	 *            日志模块ID
	 * @param logEnabled
	 *            记录日志状态（true:可记录，false:不可记录）
	 */
	public void updateLogEnabled(String operModule, boolean logEnabled);
}
