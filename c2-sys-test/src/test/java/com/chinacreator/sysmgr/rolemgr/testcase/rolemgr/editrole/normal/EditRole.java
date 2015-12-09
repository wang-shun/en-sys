package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.editrole.normal;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception.AddRole_RoleNameIsAgain;
import com.chinacreator.sysmgr.utils.Common;

public class EditRole extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditRole.class);
	public void testEditRole() throws Exception{
	//选择角色类型
	TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
	Common.waitFor(2, TestAll.driver);
	
	//选择要编辑的角色
	TestAll.driver.findElement(By.xpath("//td[@title='组长se']")).click();
	Common.waitFor(1, TestAll.driver);
	
	//编辑
	TestAll.driver.findElement(By.id("newField11")).click();
	
	//角色名称
	TestAll.driver.findElement(By.id("roleName")).clear();
	TestAll.driver.findElement(By.id("roleName")).sendKeys("组长角色se");
	
	//角色描述
	TestAll.driver.findElement(By.id("roleDesc")).clear();
	TestAll.driver.findElement(By.id("roleDesc")).sendKeys("测试编辑操作");
	
	//保存
	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		    
//	try {
//	      assertEquals("编辑角色成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//	    } catch (Error e) {
//	      TestAll.verificationErrors.append(e.toString());
//	    }
//	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
//	Common.waitFor(1, TestAll.driver);
	
    
	//判断alert为正确弹框还是错误弹框
	WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
	if (webElement.getAttribute("class").contains("message-success"))
		logger.info("正常流-编辑角色： 操作成功@@");
	else if (webElement.getAttribute("class").contains("message-error"))
		{
		logger.error("正常流-编辑角色： 操作失败!!!");
		Common.TakePic();
		}
	
	//关闭alert弹框
	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
    
	
	//关闭
	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	}
}
