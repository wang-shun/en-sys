package com.chinacreator.sysmgr.resourcemgr.testcase.editresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditMenu extends TestCase{

	@Test
	public void testEditMenu() throws Exception{
		//刷新菜单
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
		
		//展开菜单
		TestAll.driver.findElement(By.xpath("//a[@title='菜单']/preceding-sibling::*")).click();
		
		//选择需要编辑的菜单
		TestAll.driver.findElement(By.xpath("//a[@title='[菜单]iframe中打开']")).click();
		
		//修改资源名称
		TestAll.driver.findElement(By.id("privilegeName_Field")).clear();
		TestAll.driver.findElement(By.id("privilegeName_Field")).sendKeys("iframe修改后");
		
		//修改是否显示
		TestAll.driver.findElement(By.id("newField3")).click();
		
		//保存
		TestAll.driver.findElement(By.id("newField4")).click();
		try {
		      assertEquals("编辑菜单成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
	}

}
