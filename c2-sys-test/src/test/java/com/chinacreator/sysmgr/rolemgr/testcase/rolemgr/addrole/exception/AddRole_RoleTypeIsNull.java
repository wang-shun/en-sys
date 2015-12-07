package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception;

import junit.framework.TestCase;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddRole_RoleTypeIsNull extends TestCase{
	public void testAddRole_RoleTypeIsNull() throws Exception{
	 
	    //新增
	    TestAll.driver.findElement(By.id("newField10")).click();
	    
	    
	    //角色名称
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("test");
	    
	    //角色描述
	    TestAll.driver.findElement(By.id("roleDesc")).clear();
	    TestAll.driver.findElement(By.id("roleDesc")).sendKeys("test");

	    //保存
	    TestAll.driver.findElement(By.id("newField3")).click();
	    	    
	    try {
		      assertEquals("保存失败！验证未通过", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	    Common.waitFor(1, TestAll.driver);
	    
	    //关闭
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(3, TestAll.driver);
	}
}
