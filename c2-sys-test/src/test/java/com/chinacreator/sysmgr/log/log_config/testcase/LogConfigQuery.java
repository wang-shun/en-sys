package com.chinacreator.sysmgr.log.log_config.testcase;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogConfigQuery extends TestCase{
	public void testLogConfigQuery() throws Exception {
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logConfigGroup']/div/button[2]")).click();
	    //日志描述
	    TestAll.driver.findElement(By.id("logCon_logOperdesc_Field")).clear();
	    TestAll.driver.findElement(By.id("logCon_logOperdesc_Field")).sendKeys("备份日志");

	    //日志类型
	    TestAll.driver.findElement(By.id("logCon_logType_Field")).sendKeys("服务");
	    
	    ////回车
	    //TestAll.driver.findElement(By.id("logCon_logOper_Field")).sendKeys(Keys.ENTER);
    	  
	    //查询
	    TestAll.driver.findElement(By.xpath("//div[@id='searchLogConfigDiv']/div[5]/div/button[1]")).click();
	    
	    try {
	      assertTrue(TestAll.driver.findElement(By.xpath("//table[@id='logConfigGroup']/tbody/tr[2]")).getText().matches("^[\\s\\S]*备份日志[\\s\\S]*$"));

	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());

	      Common.TakePic();
	    }
	    Thread.sleep(1000);
	    //重置查询条件
	    TestAll.driver.findElement(By.xpath("//div[@id='searchLogConfigDiv']/div[5]/div/button[2]")).click();
	    //查询
	    TestAll.driver.findElement(By.xpath("//div[@id='searchLogConfigDiv']/div[5]/div/button[1]")).click();
	  //关闭查询条件框
 		TestAll.driver.findElement(By.xpath("//div[@id='t_logConfigGroup']/div/button[2]")).click();
	  }
 		
}
