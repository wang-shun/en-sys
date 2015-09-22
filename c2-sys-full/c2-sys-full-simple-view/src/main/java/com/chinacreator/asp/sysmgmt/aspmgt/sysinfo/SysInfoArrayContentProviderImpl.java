package com.chinacreator.asp.sysmgmt.aspmgt.sysinfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemInfoDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.service.SysRTSettingService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class SysInfoArrayContentProviderImpl implements ArrayContentProvider {

	private SysRTSettingService sysRTSettingService = ApplicationContextManager
			.getContext().getBean(SysRTSettingService.class);

	@Override
	public Page<SystemInfoDTO> getElements(ArrayContext context) {
		Page<SystemInfoDTO> page = new Page<SystemInfoDTO>(new Pageable(),
				new ArrayList<SystemInfoDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String infoTypeId = (String) map.get("infoTypeId");
				if (null != infoTypeId && !infoTypeId.trim().equals("")
						&& !infoTypeId.trim().equals("0")) {
					SystemInfoDTO systemInfoDTO = new SystemInfoDTO();
					try {
						BeanUtils.populate(systemInfoDTO, map);
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
					systemInfoDTO.setInfoType(infoTypeId);
					page = sysRTSettingService.queryBySystemInfo(systemInfoDTO,
							context.getPageable(), context.getSortable());
				}
			}
		}
		return page;
	}

}
