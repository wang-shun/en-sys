/*
 * 新增角色：选择角色类型后再点击新增按钮，是否使用设置为不使用，直接保存，不进行授权操作。
 */
package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

import junit.framework.TestCase;

public class AddRole_IsNotUse extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddRole_IsNotUse.class);
	public void testAddRole_IsNotUse() throws Exception{
		//选择角色类型
	    TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
	    
	    //新增
	    TestAll.driver.findElement(By.id("newField10")).click();
	    
	    //角色名称
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("组长se");
	    
	    //是否使用
	    TestAll.driver.findElement(By.id("roleUsage")).click();
	    
	    //角色描述
	    TestAll.driver.findElement(By.id("roleDesc")).clear();
	    TestAll.driver.findElement(By.id("roleDesc")).sendKeys("测试组长角色");

	    //保存
	    TestAll.driver.findElement(By.id("newField3")).click();
	    	    
//	    try {
//		      assertEquals("新增角色成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-新增角色-不使用： 添加成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-新增角色-不使用： 添加失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
	    
	    //关闭
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(2, TestAll.driver);
	}

}
