package com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.exception;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddRoleType_TypeNameIsNull extends TestCase{

	@Test
	public void testAddRoleType_TypeNameIsNull() throws Exception{
		//选择角色类型树根节点
		TestAll.driver.findElement(By.xpath("//a[@title='角色类型树']")).click();
		
		//新增
		TestAll.driver.findElement(By.id("newField6")).click();
		
		//角色类型名称
		TestAll.driver.findElement(By.id("typeName")).clear();
		TestAll.driver.findElement(By.id("typeName")).sendKeys("");
		
		//角色类型描述
		TestAll.driver.findElement(By.id("typeDesc")).clear();
		TestAll.driver.findElement(By.id("typeDesc")).sendKeys("测试角色类型");
		
		//保存
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
		try {
		      assertEquals("保存失败，验证未通过", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		
		Common.waitFor(2, TestAll.driver);
		 
		
	}
}

