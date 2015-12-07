package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class LogQuery extends TestCase{
	 public void testLogQuery() throws Exception {

		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[3]")).click();
		    Common.onBlur();
		    //操作用户
		    TestAll.driver.findElement(By.id("log_logOperUser_Field")).clear();
		    TestAll.driver.findElement(By.id("log_logOperUser_Field")).sendKeys("系统管理员");
		    //操作类型
		    TestAll.driver.findElement(By.id("log_operType_Field")).sendKeys("其他");
		    //日志描述
		    TestAll.driver.findElement(By.id("log_logOperdesc_Field")).clear();
		    TestAll.driver.findElement(By.id("log_logOperdesc_Field")).sendKeys("删除");
		    //日志来源
		    TestAll.driver.findElement(By.id("log_logVisitorial_Field")).clear();
		    TestAll.driver.findElement(By.id("log_logVisitorial_Field")).sendKeys("172.16.25.");
		    //操作时间
		    
		    //日志状态
		    TestAll.driver.findElement(By.id("log_logStatus_Field")).sendKeys("成功");
		    
		    //查询
		    TestAll.driver.findElement(By.id("log_searchLog_button")).click();
		    // Warning: verifyTextPresent may require manual changes
		    try {
		      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*删除机构[\\s\\S]*$"));
		  
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
	
		    }
		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[3]")).click();
		  }
	  		
}
