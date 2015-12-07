package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenLogConfigTab extends TestCase{
	public void testOpenLogConfig() throws Exception {
	  	
	    TestAll.driver.findElement(By.xpath("//div[@id='newGroup']/ul/li[3]/a")).click();
  }
}
