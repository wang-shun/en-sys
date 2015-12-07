package com.chinacreator.sysmgr.usermgr.testcase.edituser.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditUser_NochooseUser extends TestCase{

	@Test
	public void testEditUser_NochooseUser() throws Exception{
		//编辑
		TestAll.driver.findElement(By.id("newField14")).click();
		
		try {
			assertEquals("请选择需要编辑的用户", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		Common.waitFor(3, TestAll.driver);
	}

}
