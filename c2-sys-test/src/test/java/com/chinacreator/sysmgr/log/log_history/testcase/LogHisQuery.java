package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.log.log_msg.testcase.LogBackUp;
import com.chinacreator.sysmgr.utils.Common;

public class LogHisQuery extends TestCase{
	Logger logger = LoggerFactory.getLogger(LogHisQuery.class);
	public void testLogHisQuery() throws Exception {

	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[3]")).click();
	    Common.onBlur();
	    //操作用户
	    TestAll.driver.findElement(By.id("logHis_logOperUser_Field")).clear();
	    TestAll.driver.findElement(By.id("logHis_logOperUser_Field")).sendKeys("系统管理员");
	    //操作类型
	    TestAll.driver.findElement(By.id("logHis_operType_Field")).sendKeys("其他");
	    //日志描述
	    TestAll.driver.findElement(By.id("logHis_logOperdesc_Field")).clear();
	    TestAll.driver.findElement(By.id("logHis_logOperdesc_Field")).sendKeys("删除");
	    //日志来源
	    TestAll.driver.findElement(By.id("logHis_logVisitorial_Field")).clear();
	    TestAll.driver.findElement(By.id("logHis_logVisitorial_Field")).sendKeys("172.16.");
	    //操作时间
	    
	    //日志状态
	    TestAll.driver.findElement(By.id("logHis_logStatus_Field")).sendKeys("成功");
	    
	    try {
	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*删除[\\s\\S]*$"));
	      logger.info("历史日志查询：操作成功！");
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	      Common.TakePic();
	      logger.error("历史日志查询：操作失败！");
	    }
	    
	    //关闭查询条件输入栏
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[3]")).click();
	  }
}
