package com.chinacreator.c2.sys.selecttree.spi.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.spi.AfterUpdateUserSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 编辑用户后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterUpdateUserSpiImpl implements AfterUpdateUserSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterUpdate(UserDTO userDTO) {
		cache.replaceUser(userDTO);
	}

	@Override
	public void updateExceptionCallback(UserDTO userDTO) {
	}

}
