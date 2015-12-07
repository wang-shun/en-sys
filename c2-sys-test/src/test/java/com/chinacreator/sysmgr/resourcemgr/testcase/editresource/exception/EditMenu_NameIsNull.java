package com.chinacreator.sysmgr.resourcemgr.testcase.editresource.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditMenu_NameIsNull extends TestCase{

	@Test
	public void testEditMenu_NameIsNull() throws Exception{
		//刷新菜单
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
		
		//选择需要编辑的菜单
		TestAll.driver.findElement(By.xpath("//a[@title='[菜单]iframe修改后']")).click();
		
		//修改资源名称
		TestAll.driver.findElement(By.id("privilegeName_Field")).clear();
				
		//保存
		TestAll.driver.findElement(By.id("newField4")).click();
		try {
		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
	}

}
