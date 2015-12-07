package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roleprivilege.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleSetPrivilege_recovery extends TestCase{

	@Test
	public void testRoleSetPrivilege_recovery() throws Exception{
		//查询要设置权限的角色
		 TestAll.driver.findElement(By.id("newField")).clear();
		 TestAll.driver.findElement(By.id("newField")).sendKeys("产品经理");
		 Common.waitFor(1, TestAll.driver);
		 		 
		 //选择要设置权限的角色
		 TestAll.driver.findElement(By.xpath("//td[@title='产品经理']")).click();
		 
		 //设置权限
		 TestAll.driver.findElement(By.id("newField13")).click();
		 
		 //回收
		 TestAll.driver.findElement(By.id("newField4")).click();
		 
		 //确认
		 TestAll.driver.findElement(By.id("ok_btn")).click();
		 
		 try {
		      assertEquals("回收权限成功", TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 //关闭
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		
	}

}
