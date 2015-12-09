package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.log.OpenLogMgr;
import com.chinacreator.sysmgr.utils.Common;

public class LogQuery extends TestCase{
	Logger logger = LoggerFactory.getLogger(LogQuery.class);
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
		    
		    try {
		      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*删除字典类型[\\s\\S]*$"));
		      logger.info("日志查询：操作成功！");
		  
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		      logger.info("日志查询：操作失败！");
	
		    }
		    
		    //清空查询条件
		    TestAll.driver.findElement(By.id("log_logOperUser_Field")).clear();
		    TestAll.driver.findElement(By.id("log_operType_Field")).sendKeys("");
		    TestAll.driver.findElement(By.id("log_logOperdesc_Field")).clear();
		    TestAll.driver.findElement(By.id("log_logVisitorial_Field")).clear();
		    TestAll.driver.findElement(By.id("log_logStatus_Field")).sendKeys("");
		    Common.waitFor(3, TestAll.driver);
		    
		    //点击查询按钮收起查询框
		    TestAll.driver.findElement(By.xpath("//div[@id='t_logGroup']/div/button[3]")).click();
		  }
	  		
}
