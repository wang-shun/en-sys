package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class HisLogDetail extends TestCase{
	public void testLogHisDetail() throws Exception {
	    TestAll.driver.findElement(By.xpath("//table[@id='logHistoryGroup']/tbody/tr[2]/td[1]")).click();
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[1]")).click();
	    // Warning: verifyTextPresent may require manual changes
	    try {
	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*日志详情[\\s\\S]*$"));
	   
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());

	      Common.TakePic();
	    }
	    TestAll.driver.findElement(By.id("newField2")).click();
	    Thread.sleep(1000);
	  }
}
