package com.chinacreator.sysmgr.usermgr.testcase.deluser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelUser_Multiple extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelUser_Multiple.class);
	@Test
	public void testDelUser_Multiple() throws Exception{
		//修改每页显示条数
		TestAll.driver.findElement(By.xpath("//option[@value='100']")).click();
		Common.waitFor(3, TestAll.driver);
		
		//查询测试用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("se");
		Common.waitFor(2, TestAll.driver);
		
		//全选用户
		TestAll.driver.findElement(By.id("cb_userGroup")).click();
		
		//删除
		TestAll.driver.findElement(By.id("newField15")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
//		try {
//			assertEquals("删除用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除多个用户： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除多个用户： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		Common.waitFor(3, TestAll.driver);
	}

}
