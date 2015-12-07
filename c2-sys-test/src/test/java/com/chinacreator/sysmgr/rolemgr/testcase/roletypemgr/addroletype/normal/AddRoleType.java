package com.chinacreator.sysmgr.rolemgr.testcase.roletypemgr.addroletype.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddRoleType extends TestCase{

	@Test
	public void testAddRoleType() throws Exception{
		//选择角色类型树根节点
		TestAll.driver.findElement(By.xpath("//a[@title='角色类型树']")).click();
		
		//新增
		TestAll.driver.findElement(By.id("newField6")).click();
		
		//角色类型名称
		TestAll.driver.findElement(By.id("typeName")).clear();
		TestAll.driver.findElement(By.id("typeName")).sendKeys("测试角色类型");
		
		//角色类型描述
		TestAll.driver.findElement(By.id("typeDesc")).clear();
		TestAll.driver.findElement(By.id("typeDesc")).sendKeys("角色类型测试");
		
		//保存
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
		try {
		      assertEquals("新增角色类型成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		
		Common.waitFor(2, TestAll.driver);
		
		//展开刷新按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
		
		//点击刷新按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[2]/a")).click();
		 
		
	}
}

