package com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.rolemgr.testcase.OpenRoleMgr;
import com.chinacreator.sysmgr.utils.Common;

public class AddRoleType extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddRoleType.class);
	@Test
	public void testAddRoleType() throws Exception{
		//选择角色类型树根节点
		TestAll.driver.findElement(By.xpath("//a[@title='角色类型树']")).click();
		
		//新增
		TestAll.driver.findElement(By.id("newField6")).click();
		
		//角色类型名称
		TestAll.driver.findElement(By.id("typeName")).clear();
		TestAll.driver.findElement(By.id("typeName")).sendKeys("测试角色类型se");
		
		//角色类型描述
		TestAll.driver.findElement(By.id("typeDesc")).clear();
		TestAll.driver.findElement(By.id("typeDesc")).sendKeys("角色类型测试");
		
		//保存
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
//		try {
//		      assertEquals("新增角色类型成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-新增角色类型： 添加成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-新增角色类型： 添加失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		
		Common.waitFor(2, TestAll.driver);
		
		//展开刷新按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
		
		//点击刷新按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[2]/a")).click();
		 
		
	}
}

