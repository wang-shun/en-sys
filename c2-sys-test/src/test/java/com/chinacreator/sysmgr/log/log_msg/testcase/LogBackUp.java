package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogBackUp extends TestCase{
	Logger logger = LoggerFactory.getLogger(LogBackUp.class);
	 public void testLogBackUp() throws Exception {

		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[2]")).click();
		    //备份天数
		    TestAll.driver.findElement(By.id("backupDay_Field")).clear();
		    TestAll.driver.findElement(By.id("backupDay_Field")).sendKeys("1");
		    
		    //备份
		    TestAll.driver.findElement(By.id("newField")).click();
		    
		    // 确认
		    TestAll.driver.findElement(By.id("ok_btn")).click();
//		    try {
//		      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*备份日志成功[\\s\\S]*$"));
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		      Common.TakePic();
//		    }
		    
			//判断alert为正确弹框还是错误弹框
			WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
			if (webElement.getAttribute("class").contains("message-success"))
				logger.info("正常流-备份日志： 操作成功@@");
			else
				{
				logger.error("正常流-备份日志： 操作失败!!!");
				Common.TakePic();
				}
			
			//关闭alert弹框
			TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		    
		    Common.waitFor(5, TestAll.driver);
	 }
}
