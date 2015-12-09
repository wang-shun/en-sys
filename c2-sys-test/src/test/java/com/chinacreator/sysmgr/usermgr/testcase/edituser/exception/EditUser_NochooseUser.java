package com.chinacreator.sysmgr.usermgr.testcase.edituser.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditUser_NochooseUser extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditUser_NochooseUser.class);
	@Test
	public void testEditUser_NochooseUser() throws Exception{
		//编辑
		TestAll.driver.findElement(By.id("newField14")).click();
		
//		try {
//			assertEquals("请选择需要编辑的用户", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-error"))
			logger.info("异常流-编辑用户-未选择用户：操作成功@@");
		else if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.error("异常流-编辑用户-未选择用户：操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();

		Common.waitFor(3, TestAll.driver);
	}

}
