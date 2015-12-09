package com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.delroletype.normal;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.delrole.normal.DelRole;
import com.chinacreator.sysmgr.utils.Common;

public class DelRoleType extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelRoleType.class);
	public void testDelRoleType() throws Exception{
		// 要删除的角色类型
		TestAll.driver.findElement(By.xpath("//a[@title='测试角色类型se']")).click();
		
		//展开删除按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
		
		//删除
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[1]/a")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除角色类型： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除角色类型： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		Common.waitFor(3, TestAll.driver);
	}

}