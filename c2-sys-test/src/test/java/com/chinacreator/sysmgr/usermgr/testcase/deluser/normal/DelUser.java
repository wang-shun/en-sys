package com.chinacreator.sysmgr.usermgr.testcase.deluser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelUser extends TestCase{

	@Test
	public void testDelUser() throws Exception{
		//查询要删除的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("张三丰");
		Common.waitFor(2, TestAll.driver);
				
		//勾选要删除的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//删除
		TestAll.driver.findElement(By.id("newField15")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		try {
			assertEquals("删除用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		 
		 //清空查询条件
		 TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		 Common.waitFor(3, TestAll.driver);
	}

}
