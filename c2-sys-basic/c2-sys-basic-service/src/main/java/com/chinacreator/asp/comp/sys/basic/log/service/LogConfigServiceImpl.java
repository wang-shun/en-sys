package com.chinacreator.asp.comp.sys.basic.log.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.LogMessages;
import com.chinacreator.asp.comp.sys.basic.log.dao.LogConfigDao;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogConfigDTO;
import com.chinacreator.asp.comp.sys.basic.log.eo.LogConfigEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 日志配置服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class LogConfigServiceImpl implements LogConfigService {

	@Autowired
	private LogConfigDao logConfigDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(LogConfigDTO logConfigDTO) {
		if (null != logConfigDTO) {
			// 判断日志类型不为空
			if (isBlank(logConfigDTO.getLogType())) {
				throw new NullPointerException(
						LogMessages.getString("LOG.LOG_TYPE_IS_NULL"));
			}
			// 判断日志操作ID不为空
			if (isBlank(logConfigDTO.getLogOper())) {
				throw new NullPointerException(
						LogMessages.getString("LOG.LOG_OPER_IS_NULL"));
			}

			logConfigDTO.setOperModule(PKGenerator.generate());

			LogConfigEO logConfigEO = new LogConfigEO();
			BeanCopierUtil.copy(logConfigDTO, logConfigEO);

			logConfigDao.create(logConfigEO);
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOGCONFIG.LOGCONFIGDTO_IS_NULL"));
		}
	}

	public List<LogConfigDTO> queryAll() {
		List<LogConfigDTO> logConfigDTOs = new ArrayList<LogConfigDTO>();
		List<LogConfigEO> logConfigEOs = logConfigDao.queryAll();
		BeanCopierUtil.copy(logConfigEOs, logConfigDTOs, LogConfigEO.class,
				LogConfigDTO.class);
		return logConfigDTOs;
	}

	public Page<LogConfigDTO> queryAll(Pageable pageable, Sortable sortable) {

		// 根据条件查询日志分类
		Page<LogConfigEO> logConfigEOPage = logConfigDao.queryAll(pageable,
				sortable);

		// 将分页后的EO日志分类对象转换成分也后的DTO日志分类对象
		Page<LogConfigDTO> logConfigDTOPage = BeanCopierUtil.copyPage(
				logConfigEOPage, LogConfigEO.class, LogConfigDTO.class);
		return logConfigDTOPage;
	}

	public Page<LogConfigDTO> queryByLogConfig(LogConfigDTO logConfigDTO,
			Pageable pageable, Sortable sortable) {
		if (null != logConfigDTO) {
			LogConfigEO logConfigEO = new LogConfigEO();
			BeanCopierUtil.copy(logConfigDTO, logConfigEO);
			Page<LogConfigEO> logConfigEOPage = logConfigDao.query(logConfigEO,
					pageable, sortable);
			Page<LogConfigDTO> logModuleDTOPage = BeanCopierUtil.copyPage(
					logConfigEOPage, LogConfigEO.class, LogConfigDTO.class);
			return logModuleDTOPage;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOGCONFIG.LOGCONFIGDTO_IS_NULL"));
		}
	}

	public LogConfigDTO queryByPK(String operModule) {
		if (!isBlank(operModule)) {
			LogConfigEO logConfigEO = logConfigDao.queryByPK(operModule);

			LogConfigDTO logConfigDTO = null;
			if (null != logConfigEO) {
				logConfigDTO = new LogConfigDTO();
				BeanCopierUtil.copy(logConfigEO, logConfigDTO);
			}
			return logConfigDTO;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.OPER_MODULE_IS_NULL"));
		}
	}

	public LogConfigDTO queryByLogTypeAndLogOper(String logType, String logOper) {
		// 判断日志类型不为空
		if (isBlank(logType)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_TYPE_IS_NULL"));
		}
		// 判断日志操作ID不为空
		if (isBlank(logOper)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_OPER_IS_NULL"));
		}

		LogConfigEO logConfigEO = logConfigDao.queryByLogTypeAndLogOper(
				logType, logOper);
		LogConfigDTO logConfigDTO = null;
		if (null != logConfigEO) {
			logConfigDTO = new LogConfigDTO();
			BeanCopierUtil.copy(logConfigEO, logConfigDTO);
		}
		return logConfigDTO;
	}

	public boolean existsByOperModule(String operModule) {
		if (!isBlank(operModule)) {
			return logConfigDao.existsByOperModule(operModule) > 0;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.OPER_MODULE_IS_NULL"));
		}
	}

	public boolean existsByLogTypeAndLogOper(String logType, String logOper) {
		// 判断日志类型不为空
		if (isBlank(logType)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_TYPE_IS_NULL"));
		}
		// 判断日志操作ID不为空
		if (isBlank(logOper)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_OPER_IS_NULL"));
		}
		return logConfigDao.existsByLogTypeAndLogOper(logType, logOper) > 0;
	}

	public boolean isLogEnabled(String operModule) {
		if (!isBlank(operModule)) {
			return logConfigDao.isLogEnabledByOperModule(operModule) > 0;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.OPER_MODULE_IS_NULL"));
		}
	}

	public boolean isLogEnabled(String logType, String logOper) {
		// 判断日志类型不为空
		if (isBlank(logType)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_TYPE_IS_NULL"));
		}
		// 判断日志操作ID不为空
		if (isBlank(logOper)) {
			throw new NullPointerException(
					LogMessages.getString("LOG.LOG_OPER_IS_NULL"));
		}
		return logConfigDao.isLogEnabledByLogTypeAndLogOper(logType, logOper) > 0;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void updateLogEnabled(String operModule, boolean logEnabled) {
		if (!isBlank(operModule)) {
			logConfigDao.updateLogEnabled(operModule, logEnabled);
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOG.OPER_MODULE_IS_NULL"));
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
