package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.log.log_history.testcase.HisLogDetail;
import com.chinacreator.sysmgr.utils.Common;

public class LogConfigEdit extends TestCase{
	Logger logger = LoggerFactory.getLogger(LogConfigEdit.class);
	public void testLogConfigEdit() throws Exception {
		//查询
		TestAll.driver.findElement(By.id("logCon_logOperdesc_Field")).clear();
		TestAll.driver.findElement(By.id("logCon_logOperdesc_Field")).sendKeys("备份日志");
				
	  	//选择记录
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td[1][@title='备份日志']")).click();
	    
	    //编辑
	    TestAll.driver.findElement(By.xpath("//div[@id='logConfig']/div/div/div[3]/div[2]/div/button[1]")).click();
	    Common.waitFor(1, TestAll.driver);
	    
	    //修改是否记录日志
	    TestAll.driver.findElement(By.id("newField2")).sendKeys("不记录");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField4")).click();
	    
	    
	    // Warning: verifyTextPresent may require manual changes
//	    try {
//	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*编辑日志配置成功[\\s\\S]*$"));
//
//	    } catch (Error e) {
//	      TestAll.verificationErrors.append(e.toString());
//	
//	      Common.TakePic();
//	    }
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-修改日志配置： 操作成功@@");
		else
			{
			logger.error("正常流-修改日志配置： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		
	    //关闭
	    TestAll.driver.findElement(By.id("newField5")).click();
}
}
