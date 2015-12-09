package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class checkLog extends TestCase{
	Logger logger = LoggerFactory.getLogger(checkLog.class);
	  public void testcheckLog() throws Exception {
		  	
		  try {
		      assertTrue(TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td[3]")).getText().matches("^[\\s\\S]*备份日志[\\s\\S]*$"));
		      logger.info("日志配置修改生效！");
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());

		    }
	  }
}
