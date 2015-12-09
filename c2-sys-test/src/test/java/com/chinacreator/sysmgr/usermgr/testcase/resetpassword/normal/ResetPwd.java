package com.chinacreator.sysmgr.usermgr.testcase.resetpassword.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.usermgr.testcase.search.normal.UserSearch;
import com.chinacreator.sysmgr.utils.Common;

public class ResetPwd extends TestCase{
	Logger logger = LoggerFactory.getLogger(ResetPwd.class);
	@Test
	public void testResetPwd() throws Exception{
		//查询要编辑的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("张三");
		Common.waitFor(2, TestAll.driver);
				
		//勾选要编辑的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//重置密码
		TestAll.driver.findElement(By.id("newField17")).click();
		
		//确认
		TestAll.driver.findElement(By.id("ok_btn")).click();
		
//		try {
//			assertEquals("重置密码成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-用户重置密码：操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-用户重置密码：操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();

	}

}
