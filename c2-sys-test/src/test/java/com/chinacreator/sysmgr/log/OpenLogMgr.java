package com.chinacreator.sysmgr.log;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenLogMgr extends TestCase{
	 public void testOpenLogQuery() throws Exception {
		  	TestAll.driver.manage().window().maximize();
		  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		  	TestAll.driver.findElement(By.linkText("日志管理")).click();
		  	TestAll.driver.findElement(By.linkText("日志查询")).click();
		  	
			Common.onBlur();
		  	Common.waitFor(1, TestAll.driver);;
	
	  }
}
