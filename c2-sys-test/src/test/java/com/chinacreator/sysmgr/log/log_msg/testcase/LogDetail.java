package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;

public class LogDetail extends TestCase{
	public void testLogDetail() throws Exception {
		 TestAll.driver.findElement(By.cssSelector("td[title=\"系统管理员\"]")).click();
		 //详情
		 TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[1]")).click();
		 // Warning: verifyTextPresent may require manual changes
		 try {
		 assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*日志详情[\\s\\S]*$"));
		 
		 } catch (Error e) {
		 TestAll.verificationErrors.append(e.toString());
		
		 }
		 TestAll.driver.findElement(By.id("newField2")).click();
		 Thread.sleep(1000);
		 }
}
