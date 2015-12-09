package com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.log.log_config.testcase.checkLog;
import com.chinacreator.sysmgr.utils.Common;

public class DelMenu extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelMenu.class);
	@Test
	public void testDelMenu() throws Exception{
		//展开菜单
		TestAll.driver.findElement(By.xpath("//a[@title='菜单']/preceding-sibling::*")).click();
		Common.waitFor(1, TestAll.driver);
		
		//选择需要删除的菜单
		TestAll.driver.findElement(By.xpath("//a[@title='[菜单]iframe修改后se']")).click();
		
		//删除
		TestAll.driver.findElement(By.id("newField15")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		
//		try {
//		      assertEquals("删除资源成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
//		Common.waitFor(1, TestAll.driver);
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除一个资源： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除一个资源： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
		
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
	}

}
