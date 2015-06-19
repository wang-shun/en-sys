package com.chinacreator.asp.comp.sys.basic.log.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.LogMessages;
import com.chinacreator.asp.comp.sys.basic.log.dao.LogDao;
import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.asp.comp.sys.basic.log.eo.LogEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
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
public class LogHistoryServiceImpl implements LogHistoryService {

	@Autowired
	private LogDao logDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteAll() {
		logDao.deleteAll(LogDao.LOG_HIS_TABLE);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... logIds) {
		if (null != logIds) {
			if (logIds.length > 0) {
				for (String logId : logIds) {
					if (isBlank(logId)) {
						throw new NullPointerException(
								LogMessages
										.getString("LOGHISTORY.LOG_ID_ARRAY_HAS_NULL_ITEM"));
					}
				}
				logDao.deleteByPKs(logIds, LogDao.LOG_HIS_TABLE);
			} else {
				throw new NullPointerException(
						LogMessages
								.getString("LOGHISTORY.LOG_ID_ARRAY_HAS_NOITEM"));
			}
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOGHISTORY.LOG_ID_ARRAY_IS_NULL"));
		}

	}

	public Page<LogDTO> queryAll(Pageable pageable, Sortable sortable) {

		// 查询所有日志，返回分页
		Page<LogEO> logEoPage = logDao.queryAll(LogDao.LOG_HIS_TABLE, pageable,
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
					LogDao.LOG_HIS_TABLE, pageable, sortable);

			Page<LogDTO> logDtoPage = BeanCopierUtil.copyPage(logEoPage,
					LogEO.class, LogDTO.class);
			return logDtoPage;
		} else {
			throw new NullPointerException(
					LogMessages.getString("LOGHISTORY.LOGHISTORY_DTO_IS_NULL"));
		}
	}

	public LogDTO queryByPK(String logId) {
		if (!isBlank(logId)) {
			LogEO logEo = logDao.queryByPK(logId, LogDao.LOG_HIS_TABLE);

			LogDTO logDto = null;
			if (null != logEo) {
				logDto = new LogDTO();
			}
			BeanCopierUtil.copy(logEo, logDto);
			return logDto;
		} else {
			throw new NullPointerException(
					LogMessages
							.getString("LOGHISTORY.LOG_ID_IS_NULL_EMPTY_BLANK"));
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
