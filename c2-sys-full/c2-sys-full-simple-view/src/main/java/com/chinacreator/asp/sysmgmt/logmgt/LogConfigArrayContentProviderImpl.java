package com.chinacreator.asp.sysmgmt.logmgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.basic.log.dto.LogConfigDTO;
import com.chinacreator.asp.comp.sys.basic.log.service.LogConfigService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class LogConfigArrayContentProviderImpl implements ArrayContentProvider {

	private LogConfigService logConfigService = ApplicationContextManager
			.getContext().getBean(LogConfigService.class);

	@Override
	public Page<LogConfigDTO> getElements(ArrayContext context) {
		Page<LogConfigDTO> page = new Page<LogConfigDTO>(new Pageable(),
				new ArrayList<LogConfigDTO>());
		LogConfigDTO logConfigDTO = new LogConfigDTO();
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				try {
					BeanUtils.populate(logConfigDTO, map);
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
		page = logConfigService.queryByLogConfig(logConfigDTO,
				context.getPageable(), context.getSortable());
		return page;
	}

}
