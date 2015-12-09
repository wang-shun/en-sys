package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class HisLogDetail extends TestCase{
	Logger logger = LoggerFactory.getLogger(HisLogDetail.class);
	public void testLogHisDetail() throws Exception {
	    TestAll.driver.findElement(By.xpath("//table[@id='logHistoryGroup']/tbody/tr[2]/td[1]")).click();
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[1]")).click();
	    // Warning: verifyTextPresent may require manual changes
	    try {
	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*日志详情[\\s\\S]*$"));
	      logger.info("查看历史日志详情：操作成功！");
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	      logger.error("查看历史日志详情：操作失败！");
	      Common.TakePic();
	    }
	    TestAll.driver.findElement(By.id("newField2")).click();
	    Common.waitFor(3, TestAll.driver);
	  }
}
