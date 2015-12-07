package com.chinacreator.sysmgr.mypane.mypassword;

import junit.framework.TestCase;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;

public class OpenMyPassword extends TestCase{
	public void testOpenMyPassword() throws Exception{
		TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("我的面板")).click();
		TestAll.driver.findElement(By.linkText("密码修改")).click();
	    try {
	      assertEquals("修改密码", TestAll.driver.findElement(By.xpath("//form/div/div/div/div")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	}

}
