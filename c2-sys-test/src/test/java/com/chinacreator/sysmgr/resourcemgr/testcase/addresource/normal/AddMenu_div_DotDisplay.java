package com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddMenu_div_DotDisplay extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddMenu_div_DotDisplay.class);
	@Test
	public void testAddMenu_div_DotDisplay() throws Exception{
		//选择父级菜单
		TestAll.driver.findElement(By.xpath("//div[@tree-id='resTree']/ul/li/ul/li/a[@title='菜单']")).click();
		
		//新增
		TestAll.driver.findElement(By.id("newField7")).click();
		
		//资源编码
		TestAll.driver.findElement(By.id("privilegeCode_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeCode_Field")).sendKeys("DotDisplay");
		
		//资源名称
		TestAll.driver.findElement(By.id("privilegeName_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeName_Field")).sendKeys("不显示菜单se");
		
		//是否显示
		TestAll.driver.findElement(By.id("newField3")).click();
		
		//打开方式
		TestAll.driver.findElement(By.id("displayMode_Field")).sendKeys("div中");
		
		//图标选择
		TestAll.driver.findElement(By.xpath("//*[@id='icon_FieldWrapper']/div/div/span/i")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='web-application']/div/div[15]/a")).click();
			
		//链接路径
		TestAll.driver.findElement(By.id("href_Field")).clear();
		TestAll.driver.findElement(By.id("href_Field")).sendKeys("#/f/personInfo");
				
		//保存
		TestAll.driver.findElement(By.id("newField4")).click();
		
//		try {
//		      assertEquals("新增菜单成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.info("正常流-新增菜单-不显示: 操作成功@@");
			//关闭alert弹框
			TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
			}
		else 
			{
			logger.error("正常流-新增菜单-不显示： 操作失败!!!");
			Common.TakePic();
			}
		
		Common.waitFor(3, TestAll.driver);		 
	}

}
