package com.chinacreator.c2.sys.selecttree.spi.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.core.user.spi.AfterDeleteUserSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 删除用户后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterDeleteUserSpiImpl implements AfterDeleteUserSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterDeleteByPKs(String... userIds) {
		cache.removeUser(userIds);
	}

	@Override
	public void deleteByPKsExceptionCallback(String... userIds) {
	}

}
