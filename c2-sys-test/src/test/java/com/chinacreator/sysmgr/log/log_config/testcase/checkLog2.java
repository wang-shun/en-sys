package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class checkLog2 extends TestCase{
	 public void testcheckLog2() throws Exception {
		  	
		  try {
		      assertTrue(TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td[3]")).getText().matches("^[\\s\\S]*备份日志[\\s\\S]*$"));

		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		      Common.TakePic();
		    }
	  }
}
