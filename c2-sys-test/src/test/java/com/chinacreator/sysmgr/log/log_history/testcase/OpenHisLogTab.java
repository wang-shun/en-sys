package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenHisLogTab extends TestCase{
	public void testOpenLogHis() throws Exception {
	  	
	    TestAll.driver.findElement(By.xpath("//div[@id='newGroup']/ul/li[2]/a/div")).click();
  }
}

