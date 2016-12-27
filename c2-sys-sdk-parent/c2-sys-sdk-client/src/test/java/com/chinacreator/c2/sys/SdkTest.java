package com.chinacreator.c2.sys;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.c2.C2UnitTests;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;

public class SdkTest extends C2UnitTests{
	@Autowired
	private OrgnizationService orgService;
	@Test
	public void testConnection(){
		orgService.get("1");
	}
}
