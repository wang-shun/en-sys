package com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelOrg_Multiple extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelOrg_Multiple.class);
	@Test
	public void testDelOrg_Multiple() throws Exception{
		// 要删除的机构
		WebElement org1 = TestAll.driver.findElement(By.xpath("//a[@title='株洲市se']"));
		WebElement org2 = TestAll.driver.findElement(By.xpath("//a[@title='湘潭市se']"));
		WebElement org3 = TestAll.driver.findElement(By.xpath("//a[@title='长沙市se']"));
		
		//多选
		Actions actions = new Actions(TestAll.driver);
		actions.click(org1).keyDown(Keys.CONTROL).click(org2).click(org3).keyUp(Keys.CONTROL).build().perform();
	
		//删除
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/ul/li[1]/a")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
//		try {
//		      assertEquals("删除机构成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除多个机构： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除多个机构： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		Common.waitFor(3, TestAll.driver);
	}

}
