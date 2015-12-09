package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.delrole.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal.DelMenu_multiple;
import com.chinacreator.sysmgr.utils.Common;

public class DelRole extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelRole.class);
	@Test
	public void testDelRole() throws Exception{
		//选择角色类型
		TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
		Common.waitFor(2, TestAll.driver);
		
		//选择要删除的角色
		TestAll.driver.findElement(By.xpath("//td[@title='产品经理se']")).click();
		Common.waitFor(1, TestAll.driver);
		
		//删除
		TestAll.driver.findElement(By.id("newField12")).click();
		 
		 //确认
		 TestAll.driver.findElement(By.id("del_btn")).click();
		 
//		 try {
//		      assertEquals("删除角色成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		 
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除一个角色： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除一个角色： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
	}
}
