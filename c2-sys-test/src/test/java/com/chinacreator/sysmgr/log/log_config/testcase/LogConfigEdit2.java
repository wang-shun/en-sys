package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogConfigEdit2 extends TestCase{
	 public void testLogConfigEdit2() throws Exception {
		  	//选择一条记录
		    TestAll.driver.findElement(By.xpath("//tbody/tr[3]/td[@title='备份日志']")).click();
		    //编辑
		    TestAll.driver.findElement(By.xpath("//div[@id='logConfig']/div/div/div[3]/div[2]/div/button[1]")).click();
		    Common.onBlur();
		    TestAll.driver.findElement(By.id("newField2")).sendKeys("记录");
		    
		    //保存
		    TestAll.driver.findElement(By.id("newField4")).click();
		    
		    
		    // Warning: verifyTextPresent may require manual changes
		    try {
		      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*编辑日志配置成功[\\s\\S]*$"));

		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());

		    }
		    //关闭
		    TestAll.driver.findElement(By.id("newField5")).click();
		  }
}
