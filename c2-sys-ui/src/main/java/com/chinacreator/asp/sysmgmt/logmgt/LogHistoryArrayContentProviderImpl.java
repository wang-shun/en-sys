package com.chinacreator.asp.sysmgmt.logmgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.basic.log.dto.LogDTO;
import com.chinacreator.asp.comp.sys.basic.log.service.LogHistoryService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class LogHistoryArrayContentProviderImpl implements ArrayContentProvider {

	private LogHistoryService logHistoryService = ApplicationContextManager
			.getContext().getBean(LogHistoryService.class);

	@Override
	public Page<LogDTO> getElements(ArrayContext context) {
		Page<LogDTO> page = new Page<LogDTO>(new Pageable(),
				new ArrayList<LogDTO>());
		LogDTO logDTO = new LogDTO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				Long startDate = (Long) map.get("startDate");
				Long endDate = (Long) map.get("endDate");
				if (null != startDate) {
					paramMap.put("startDate", new Date(startDate));
				}
				if (null != endDate) {
					paramMap.put("endDate", new Date(endDate));
				}
				try {
					BeanUtils.populate(logDTO, map);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return page;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return page;
				} catch (Exception e) {
					e.printStackTrace();
					return page;
				}
			}
		}
		page = logHistoryService.queryByLog(logDTO, paramMap,
				context.getPageable(), context.getSortable());
		return page;
	}

}
