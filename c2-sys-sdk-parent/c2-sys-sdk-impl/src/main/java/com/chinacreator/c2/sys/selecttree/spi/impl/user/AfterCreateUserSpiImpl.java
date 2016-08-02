package com.chinacreator.c2.sys.selecttree.spi.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.user.spi.AfterCreateUserSpi;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 新增用户后调用接口
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterCreateUserSpiImpl implements AfterCreateUserSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterCreate(UserDTO userDTO, String orgId, int sn) {
		cache.addUser(userDTO, orgId, sn);
	}

	@Override
	public void createExceptionCallback(UserDTO userDTO, String orgId, int sn) {
	}
}
