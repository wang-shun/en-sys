package com.chinacreator.sysmgr.resourcemgr.testcase.addresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddMenu_NewPage extends TestCase{

	@Test
	public void testAddMenu_NewPage() throws Exception{
		Common.waitFor(2, TestAll.driver);
		
		//选择父级菜单
		TestAll.driver.findElement(By.xpath("//div[@tree-id='resTree']/ul/li/ul/li/a[@title='菜单']")).click();
		
		//新增
		TestAll.driver.findElement(By.id("newField7")).click();
		
		//资源编码
		TestAll.driver.findElement(By.id("privilegeCode_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeCode_Field")).sendKeys("testbaidu");
		
		//资源名称
		TestAll.driver.findElement(By.id("privilegeName_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeName_Field")).sendKeys("百度_新页面中打开");
		
		//打开方式
		TestAll.driver.findElement(By.id("displayMode_Field")).sendKeys("新页面中");
		
		//图标选择
		TestAll.driver.findElement(By.xpath("//*[@id='icon_FieldWrapper']/div/div/span/i")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='web-application']/div/div[30]/a")).click();
		
		//链接路径
		TestAll.driver.findElement(By.id("href_Field")).clear();
		TestAll.driver.findElement(By.id("href_Field")).sendKeys("http://www.baidu.com");
		
		//扩展属性
		TestAll.driver.findElement(By.id("menuExt_Field")).clear();
		TestAll.driver.findElement(By.id("menuExt_Field")).sendKeys("测试打开新页面方式");
				
		//保存
		TestAll.driver.findElement(By.id("newField4")).click();
		
		try {
		      assertEquals("新增菜单成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		Common.waitFor(3, TestAll.driver);
	}

}
