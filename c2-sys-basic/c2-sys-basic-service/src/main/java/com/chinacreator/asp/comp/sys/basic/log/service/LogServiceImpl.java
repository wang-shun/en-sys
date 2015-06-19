package com.chinacreator.asp.comp.sys.basic.log.service;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.LogMessages;
import com.chinacreator.asp.comp.sys.basic.log.dao.LogDao;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogConfigDTO;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.asp.comp.sys.basic.log.eo.LogEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 日志服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Autowired
	private LogConfigService logConfigService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(LogDTO logDTO) {

		// 验证业务传输对象
		validateCreateLog(logDTO);
		// 是否记录日志
		if (logConfigService.isLogEnabled(logDTO.getOperModule())) {
			// 设置主键
			logDTO.setLogId(PKGenerator.generate());

			// 创建EO，将DTO赋值给EO
			LogEO logEo = new LogEO();
			BeanCopierUtil.copy(logDTO, logEo);

			// 创建日志
			logDao.create(logEo);
		}
	}

	public void createToLogQueue(LogDTO logDTO) {
		// 验证业务传输对象
		validateCreateLog(logDTO);
		// 插入日志队列
		LogQueue.offer(logDTO);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... logIds) {
		if (null != logIds) {
			if (logIds.length > 0) {
				for (String logId : logIds) {
					if (isBlank(logId)) {
						throw new NullPointerException(
								LogMessages
										.getString("LOG.LOG_ID_ARRAY_CANT_CONTAINS_NULL"));
					}
				}
				// 进行批量删除日志模块表中的记录
				logDao.deleteByPKs(logIds, LogDao.LOG_TABLE);
			} else {
				throw new NullPointerException(
						LogMessages
								.getString("LOG.LOG_ID_ARRAY_CANT_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_ID_ARRAY_IS_NULL"));
		}
	}

	public Page<LogDTO> queryAll(Pageable pageable, Sortable sortable) {

		// 查询所有日志，返回分页
		Page<LogEO> logEoPage = logDao.queryAll(LogDao.LOG_TABLE, pageable,
				sortable);
		// 分页对象的EO向DTO的转换
		Page<LogDTO> logDtoPage = BeanCopierUtil.copyPage(logEoPage,
				LogEO.class, LogDTO.class);
		return logDtoPage;
	}

	public Page<LogDTO> queryByLog(LogDTO logDTO, Map<String, Object> paramMap,
			Pageable pageable, Sortable sortable) {
		if (null != logDTO) {
			LogEO logEo = new LogEO();
			BeanCopierUtil.copy(logDTO, logEo);
			Page<LogEO> logEoPage = logDao.queryByLog(logEo, paramMap,
					LogDao.LOG_TABLE, pageable, sortable);

			Page<LogDTO> logDtoPage = BeanCopierUtil.copyPage(logEoPage,
					LogEO.class, LogDTO.class);
			return logDtoPage;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOD_DTO_IS_NULL"));
		}

	}

	public LogDTO queryByPK(String logId) {
		if (!isBlank(logId)) {
			LogEO logEo = logDao.queryByPK(logId, LogDao.LOG_TABLE);

			LogDTO logDto = null;
			if (null != logEo) {
				logDto = new LogDTO();
				BeanCopierUtil.copy(logEo, logDto);
			}
			return logDto;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOD_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public void backupLog(int fromNDaysAgo) {
		if (fromNDaysAgo >= 0) {
			logDao.backupLog(fromNDaysAgo);
		} else {
			throw new IllegalArgumentException(
					LogMessages.getString("LOG.DAY_CANT_LESS_THAN_ZERO"));
		}
	}

	private void validateCreateLog(LogDTO logDTO) {
		if (null != logDTO) {
			boolean isOperModule = false;
			// 如果日志模块ID传入，判断是否存在
			if (!isBlank(logDTO.getOperModule())) {
				isOperModule = logConfigService.existsByOperModule(logDTO
						.getOperModule());
			}
			// 如果日志模块ID不存在
			if (!isOperModule) {
				// 判断日志类型不为空
				if (isBlank(logDTO.getLogType())) {
					throw new NullPointerException(
							LogMessages.getString("LOG.LOG_TYPE_IS_NULL"));
				}
				// 判断日志操作ID不为空
				if (isBlank(logDTO.getLogOper())) {
					throw new NullPointerException(
							LogMessages.getString("LOG.LOG_OPER_IS_NULL"));
				}
				// 判断日志模块是否存在
				if (logConfigService.existsByLogTypeAndLogOper(
						logDTO.getLogType(), logDTO.getLogOper())) {
					LogConfigDTO logConfigDTO = logConfigService
							.queryByLogTypeAndLogOper(logDTO.getLogType(),
									logDTO.getLogOper());
					if (null != logConfigDTO
							&& !isBlank(logConfigDTO.getOperModule())) {
						// 设置日志模块ID
						logDTO.setOperModule(logConfigDTO.getOperModule());
					} else {
						throw new NullPointerException(
								LogMessages
										.getString("LOG.OPER_MODULE_IS_NULL"));
					}
				}
				// 如果日志模块不存在则新增
				else {
					LogConfigDTO logConfigDTO = new LogConfigDTO();
					logConfigDTO.setLogType(logDTO.getLogType());
					logConfigDTO.setLogOper(logDTO.getLogOper());
					logConfigDTO.setLogOperdesc(logDTO.getLogOperdesc());
					logConfigDTO.setLogEnabled(true);
					logConfigService.create(logConfigDTO);
					// 设置日志模块ID
					logDTO.setOperModule(logConfigDTO.getOperModule());
				}
			}

			// 设置日志操作时间
			if (null == logDTO.getLogOperTime()) {
				logDTO.setLogOperTime(new Timestamp(System.currentTimeMillis()));
			}
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_DTO_IS_NULL"));
		}
	}

	/**
	 * 判断字符串是否为空或空格或null
	 * 
	 * @param str
	 *            被判断的字符串
	 * @return 字符串是否为null或者""或者全是空格 是：true 否：false
	 */
	private boolean isBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}

}