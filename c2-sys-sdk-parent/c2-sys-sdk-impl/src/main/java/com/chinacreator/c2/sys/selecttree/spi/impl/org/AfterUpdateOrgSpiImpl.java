package com.chinacreator.c2.sys.selecttree.spi.impl.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.spi.AfterUpdateOrgSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 编辑机构后调用接口实现
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterUpdateOrgSpiImpl implements AfterUpdateOrgSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterUpdate(OrgDTO orgDTO) {
		cache.replaceOrg(orgDTO);
	}

	@Override
	public void updateExceptionCallback(OrgDTO orgDTO) {
	}
}
