package com.chinacreator.c2.sys.selecttree.spi.impl.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.org.spi.AfterDeleteOrgSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 删除机构后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterDeleteOrgSpiImpl implements AfterDeleteOrgSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterDeleteByPKs(String... orgIds) {
		cache.removeOrg(orgIds);
	}

	@Override
	public void deleteByPKsExceptionCallback(String... orgIds) {
	}

}
