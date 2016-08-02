package com.chinacreator.c2.sys.selecttree.spi.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.std.user.spi.AfterSetMainOrgSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 用户设置主机构后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterSetMainOrgSpiImpl implements AfterSetMainOrgSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterSetMainOrg(String[] userIds, String orgId, boolean isRetain) {
		cache.setMainOrgByUser(userIds, orgId, isRetain);
	}

}
