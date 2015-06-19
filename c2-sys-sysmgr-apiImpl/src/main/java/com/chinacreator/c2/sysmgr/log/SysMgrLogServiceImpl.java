package com.chinacreator.c2.sysmgr.log;

import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.log.Log;
import com.chinacreator.c2.log.LogService;

public class SysMgrLogServiceImpl implements LogService {

	@Override
	public void insert(Log log) {
		if (null != log) {
			LogDTO logDTO = new LogDTO();
			logDTO.setLogOperUser(log.getUser());
			logDTO.setLogType(log.getType());
			logDTO.setLogOper(log.getOperation());
			logDTO.setLogOperdesc(log.getDesc());
			logDTO.setLogVisitorial(log.getIp());
			logDTO.setLogContent(log.getContent());
			logDTO.setLogOperTime(log.getTime());
			logDTO.setOperType(log.getOpType());
			logDTO.setLogStatus(log.getStatus());
			logDTO.setRemark1(log.getRemark1());
			logDTO.setRemark2(log.getRemark2());
			logDTO.setRemark3(log.getRemark3());
			logDTO.setRemark4(log.getRemark4());
			logDTO.setRemark5(log.getRemark5());

			getLogService().createToLogQueue(logDTO);
		}
	}

	private com.chinacreator.asp.comp.sys.basic.log.service.LogService getLogService() {
		return ApplicationContextManager
				.getContext()
				.getBean(
						com.chinacreator.asp.comp.sys.basic.log.service.LogService.class);
	}
}
