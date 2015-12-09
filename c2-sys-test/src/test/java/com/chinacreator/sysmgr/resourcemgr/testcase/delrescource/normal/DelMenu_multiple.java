package com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal;

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

public class DelMenu_multiple extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelMenu_multiple.class);
	@Test
	public void testDelMenu_multiple() throws Exception{
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
		
		//要删除的第一个资源
		WebElement del1 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]div中打开se']"));
		
		//要删除的第二个资源
		WebElement del2 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]百度_新页面中打开se']"));
		
		//要删除的第三个资源
		WebElement del3 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]不显示菜单se']"));
		
		//按住CTRL键多选
		Actions actions = new Actions(TestAll.driver);
		actions.click(del1).keyDown(Keys.CONTROL).click(del2).click(del3).keyUp(Keys.CONTROL).build().perform();
		
		//点击删除按钮
		TestAll.driver.findElement(By.id("newField15")).click();
		Common.waitFor(1, TestAll.driver);
			
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		
//		try {
//		      assertEquals("删除资源成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
//		Common.waitFor(3, TestAll.driver);
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-删除多个资源： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-删除多个资源： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
		
		
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
				
	}

}
