package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.delrole.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelRole_Multiple extends TestCase{

	@Test
	public void testDelRole_Multiple() throws Exception{
		//选择角色类型
		TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
		Common.waitFor(2, TestAll.driver);
		
		//选择要删除的角色
		TestAll.driver.findElement(By.xpath("//*[@id='cb_roleGroup']")).click();
		Common.waitFor(1, TestAll.driver);
		
		//删除
		TestAll.driver.findElement(By.id("newField12")).click();
		 
		 //确认
		 TestAll.driver.findElement(By.id("del_btn")).click();
		 
		 try {
		      assertEquals("删除角色成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	}

}
