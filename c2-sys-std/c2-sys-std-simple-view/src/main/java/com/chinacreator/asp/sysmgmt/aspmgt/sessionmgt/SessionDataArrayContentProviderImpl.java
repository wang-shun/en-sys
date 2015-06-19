package com.chinacreator.asp.sysmgmt.aspmgt.sessionmgt;

import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.chinacreator.asp.comp.sys.core.security.dto.OnlineUserDTO;
import com.chinacreator.asp.comp.sys.core.security.service.OnlineUserService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class SessionDataArrayContentProviderImpl implements
		ArrayContentProvider {

	private OnlineUserService onlineUserService = ApplicationContextManager
			.getContext().getBean(OnlineUserService.class);

	public Page<OnlineUserDTO> getElements(ArrayContext context) {
		Page<OnlineUserDTO> page = new Page<OnlineUserDTO>(new Pageable(),
				new ArrayList<OnlineUserDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();

			if (null != map && !map.isEmpty()) {
				OnlineUserDTO onlineUserDTO = JSON.parseObject(
						JSON.toJSONBytes(map), OnlineUserDTO.class);

				page = onlineUserService.queryOnlineUsers(onlineUserDTO,
						context.getPageable(), context.getSortable());
			}

		}

		return page;
	}

}
