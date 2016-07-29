package com.chinacreator.c2.sys.selecttree.spi.impl.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.spi.AfterCreateOrgSpi;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 新增机构后调用接口
 * 
 * @author 彭盛
 * 
 */
@Component
public class AfterCreateOrgSpiImpl implements AfterCreateOrgSpi {

	@Autowired
	private OrgUserTreeCache cache;

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void afterCreate(OrgDTO orgDTO) {
		cache.addOrg(orgDTO);
	}

	@Override
	public void createExceptionCallback(OrgDTO orgDTO) {
	}

}
