package com.chinacreator.sysmgr.log;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dictypemgr.adddictype.normal.AddDicType;
import com.chinacreator.sysmgr.utils.Common;

public class OpenLogMgr extends TestCase{
	Logger logger = LoggerFactory.getLogger(OpenLogMgr.class);
	 public void testOpenLogQuery() throws Exception {
		  	TestAll.driver.manage().window().maximize();
		  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		  	TestAll.driver.findElement(By.linkText("日志管理")).click();
		  	TestAll.driver.findElement(By.linkText("日志查询")).click();
		  	
			Common.onBlur();
		  	Common.waitFor(5, TestAll.driver);;
		  	logger.info("打开日志管理页！");
	
	  }
}
