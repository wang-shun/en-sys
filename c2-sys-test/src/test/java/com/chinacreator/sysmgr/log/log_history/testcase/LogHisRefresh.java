package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogHisRefresh extends TestCase{
	 public void testLogHisRefresh() throws Exception {

		    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[4]")).click();
		    Common.onBlur();
		   
		  }
}
