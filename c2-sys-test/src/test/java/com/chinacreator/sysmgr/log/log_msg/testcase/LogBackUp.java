package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogBackUp extends TestCase{
	 public void testLogBackUp() throws Exception {

		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[2]")).click();
		    //备份天数
		    TestAll.driver.findElement(By.id("backupDay_Field")).clear();
		    TestAll.driver.findElement(By.id("backupDay_Field")).sendKeys("1");
		    
		    //备份
		    TestAll.driver.findElement(By.id("newField")).click();
		    
		    // 确认
		    TestAll.driver.findElement(By.id("ok_btn")).click();
		    try {
		      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*备份日志成功[\\s\\S]*$"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		      Common.TakePic();
		    }
		    Common.waitFor(5, TestAll.driver);
	 }
}
