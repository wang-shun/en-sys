package com.chinacreator.sysmgr.usermgr.testcase.edituser.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditUser_UserRealnameIsNull extends TestCase{

	@Test
	public void testEditUser_UserRealnameIsNull() throws Exception{
		//查询要编辑的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("张三");
		Common.waitFor(2, TestAll.driver);
				
		//勾选要编辑的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//编辑
		TestAll.driver.findElement(By.id("newField14")).click();
		
		//修改姓名
		TestAll.driver.findElement(By.id("userRealname")).clear();
		
		//保存
		TestAll.driver.findElement(By.id("newField")).click();
		try {
			assertEquals("保存失败！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.id("newField1")).click();
		
		//清空查询条件
		TestAll.driver.findElement(By.id("userRealname_Field")).clear(); 
		Common.waitFor(3, TestAll.driver);
	}

}
