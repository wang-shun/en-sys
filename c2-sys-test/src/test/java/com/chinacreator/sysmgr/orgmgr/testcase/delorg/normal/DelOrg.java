package com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.usermgr.testcase.deluser.normal.DelUser_Multiple;
import com.chinacreator.sysmgr.utils.Common;

public class DelOrg extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelOrg.class);
	@Test
	public void testDelOrg() throws Exception{
		// 选择要删除的机构
		TestAll.driver.findElement(By.xpath("//a[@title='衡阳市修改后se']")).click();
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
			logger.info("正常流-删除机构： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除机构： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		Common.waitFor(3, TestAll.driver);
	}

}
