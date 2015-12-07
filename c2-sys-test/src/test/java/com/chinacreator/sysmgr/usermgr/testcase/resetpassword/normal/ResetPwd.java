package com.chinacreator.sysmgr.usermgr.testcase.resetpassword.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class ResetPwd extends TestCase{

	@Test
	public void testResetPwd() throws Exception{
		//查询要编辑的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("张三");
		Common.waitFor(2, TestAll.driver);
				
		//勾选要编辑的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//重置密码
		TestAll.driver.findElement(By.id("newField17")).click();
		
		//确认
		TestAll.driver.findElement(By.id("ok_btn")).click();
		
		try {
			assertEquals("重置密码成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
	}

}
