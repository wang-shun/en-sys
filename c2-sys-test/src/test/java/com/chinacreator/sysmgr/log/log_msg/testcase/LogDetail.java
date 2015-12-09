package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;

public class LogDetail extends TestCase{
	Logger logger = LoggerFactory.getLogger(LogDetail.class);
	public void testLogDetail() throws Exception {
		 TestAll.driver.findElement(By.cssSelector("td[title=\"系统管理员\"]")).click();
		 //详情
		 TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[1]")).click();
		 // Warning: verifyTextPresent may require manual changes
		 try {
		 assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*日志详情[\\s\\S]*$"));
		 logger.info("查看日志详情：操作成功！");
		 } catch (Error e) {
		 TestAll.verificationErrors.append(e.toString());
		 logger.info("查看日志详情：操作失败！");
		 }
		 TestAll.driver.findElement(By.id("newField2")).click();
		 Thread.sleep(1000);
		 }
}
