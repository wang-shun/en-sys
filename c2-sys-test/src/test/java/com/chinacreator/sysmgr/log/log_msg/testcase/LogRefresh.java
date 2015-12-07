package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogRefresh extends TestCase{
	 public void testLogRefresh() throws Exception {

		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[4]")).click();
		    Common.onBlur();
		   
		  }
}
