package com.chinacreator.sysmgr.resourcemgr.testcase.editresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.resourcemgr.testcase.addresource.exception.AddMenu_NameIsNull;
import com.chinacreator.sysmgr.utils.Common;

public class EditMenu extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditMenu.class);
	@Test
	public void testEditMenu() throws Exception{
		//刷新菜单
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
		
		//展开菜单
		TestAll.driver.findElement(By.xpath("//a[@title='菜单']/preceding-sibling::*")).click();
		
		//选择需要编辑的菜单
		TestAll.driver.findElement(By.xpath("//a[@title='[菜单]iframe中打开se']")).click();
		
		//修改资源名称
		TestAll.driver.findElement(By.id("privilegeName_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeName_Field")).sendKeys("iframe修改后se");
		
		//修改是否显示
		TestAll.driver.findElement(By.id("newField3")).click();
		
		//保存
		TestAll.driver.findElement(By.id("newField4")).click();
//		try {
//		      assertEquals("编辑菜单成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.info("正常流-编辑菜单: 操作成功@@");
			//关闭alert弹框
			TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
			}
		else 
			{
			logger.error("正常流-编辑菜单： 操作失败!!!");
			Common.TakePic();
			}
		Common.waitFor(3, TestAll.driver);
	}

}
