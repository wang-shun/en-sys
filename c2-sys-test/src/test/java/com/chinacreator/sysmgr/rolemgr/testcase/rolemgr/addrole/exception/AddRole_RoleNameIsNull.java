package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal.AddRole_Res;
import com.chinacreator.sysmgr.utils.Common;

public class AddRole_RoleNameIsNull extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddRole_RoleNameIsNull.class);
	public void testAddRole_RoleNameIsNull() throws Exception{
		//选择角色类型
	    TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
		
	    //新增
	    TestAll.driver.findElement(By.id("newField10")).click();
	    Common.waitFor(1, TestAll.driver);
	    
	    //角色名称
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("");
	    
	    //角色描述
	    TestAll.driver.findElement(By.id("roleDesc")).clear();
	    TestAll.driver.findElement(By.id("roleDesc")).sendKeys("test");

	    //保存
	    TestAll.driver.findElement(By.id("newField3")).click();
	    	    
//	    try {
//		      assertEquals("保存失败！验证未通过", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
//	    TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
//	    Common.waitFor(1, TestAll.driver);
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-error"))
			logger.info("异常流-新增角色-角色名称为空： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.error("异常流-新增角色-角色名称为空： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
	    //关闭 
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(3, TestAll.driver);
	}
}
