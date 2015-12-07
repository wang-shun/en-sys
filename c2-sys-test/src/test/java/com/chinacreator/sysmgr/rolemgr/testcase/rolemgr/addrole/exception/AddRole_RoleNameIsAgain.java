package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception;

import junit.framework.TestCase;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddRole_RoleNameIsAgain extends TestCase{
	public void testAddRole_RoleNameIsAgain() throws Exception{
		//选择角色类型
	    TestAll.driver.findElement(By.xpath("//a[@title='一般类型']")).click();
		
	    //新增
	    TestAll.driver.findElement(By.id("newField10")).click();
	    
	    //角色名称
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("产品经理");
	    
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
	    TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    Common.waitFor(1, TestAll.driver);
	    
	    //关闭
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(2, TestAll.driver);
	}
}

