package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.log.log_msg.testcase.LogBackUp;
import com.chinacreator.sysmgr.utils.Common;

public class HisLogDel extends TestCase{
	Logger logger = LoggerFactory.getLogger(HisLogDel.class);
	public void testLogHisDel() throws Exception {
		Common.waitFor(2, TestAll.driver);
		
	  	//选择第一条日志
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td/input")).click();
	    
	    //删除
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[2]")).click();
	    Common.waitFor(2, TestAll.driver);
	    //确认
	    TestAll.driver.findElement(By.id("del_btn")).click();
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除历史日志： 操作成功@@");
		else
			{
			logger.error("正常流-删除历史日志： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
//	    try {
//	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*删除历史日志成功[\\s\\S]*$"));
//	    } catch (Error e) {
//	      TestAll.verificationErrors.append(e.toString());
//	    }
	    

	  }
}
