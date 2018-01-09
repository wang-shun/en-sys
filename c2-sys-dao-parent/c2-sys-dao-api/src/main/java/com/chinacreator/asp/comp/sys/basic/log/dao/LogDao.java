package com.chinacreator.asp.comp.sys.basic.log.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.basic.log.eo.LogEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 日志数据访问接口
 * 
 * @author 杨祎程
 * 
 */
@Repository
public interface LogDao {

	/**
	 * 日志表
	 */
	public static final String LOG_TABLE = "td_sm_log";

	/**
	 * 历史日志表
	 */
	public static final String LOG_HIS_TABLE = "td_sm_log_his";

	/**
	 * 新增日志
	 * 
	 * @param logEO
	 *            日志数据访问对象
	 * @return 数据库执行操作影响到的行数
	 */
	public int create(LogEO logEO);

	/**
	 * 通过日志主键删除日志表（或历史日志表）的记录
	 * 
	 * @param logIds
	 *            日志ID数组
	 * @param tableName
	 *            日志表（历史日志表）名称
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteByPKs(@Param("logIds") String[] logIds,
			@Param("tableName") String tableName);

	/**
	 * 删除日志表的记录
	 * 
	 * @param tableName
	 *            历史日志表名称
	 * @return 数据库执行操作影响到的行数
	 */
	public int deleteAll(@Param("tableName") String tableName);

	/**
	 * 分页查询所有日志表（或历史日志表）的日志记录
	 * 
	 * @param tableName
	 *            日志表（历史日志表）名称
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的日志数据库传输对象
	 */
	public Page<LogEO> queryAll(@Param("tableName") String tableName,
			Pageable pageable, Sortable sortable);

	/**
	 * 通过日志主键查询日志表（或历史日志表）的日志对象
	 * 
	 * @param logId
	 *            日志ID
	 * @return 日志数据库传输对象
	 */
	public LogEO queryByPK(@Param("logId") String logId,
			@Param("tableName") String tableName);

	/**
	 * 分页查询日志
	 * 
	 * @param logEo
	 *            日志数据传输对象
	 * @param paramMap
	 *            其他查询条件<br>
	 *            <table border="1">
	 *            <tr>
	 *            <td>key值</td>
	 *            <td>对象类型</td>
	 *            <td>说明</td>
	 *            </tr>
	 *            <tr>
	 *            <td>startDate</td>
	 *            <td>java.util.Date</td>
	 *            <td>操作开始时间</td>
	 *            </tr>
	 *            <tr>
	 *            <td>endDate</td>
	 *            <td>java.util.Date</td>
	 *            <td>操作结束时间</td>
	 *            </tr>
	 *            </table>
	 * @param tableName
	 *            查询的日志表名
	 * @param pageable
	 *            分页条件
	 * @param sortable
	 *            排序条件
	 * @return 查询后的分页日志结果集
	 */
	public Page<LogEO> queryByLog(@Param("logEo") LogEO logEo,
			@Param("paramMap") Map<String, Object> paramMap,
			@Param("tableName") String tableName, Pageable pageable,
			Sortable sortable);

	/**
	 * 调用存储过程完成日志备份
	 * 
	 * @param day
	 *            备份天数（非负整数）
	 */
	public void backupLog(int toNDaysAgo);
}
