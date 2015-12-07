package com.chinacreator.sysmgr.mypane.myinfo;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;

import junit.framework.TestCase;

public class OpenMyInfo extends TestCase{
	public void testOpenMyInfo() throws Exception{
		TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("我的面板")).click();
		TestAll.driver.findElement(By.linkText("个人信息")).click();
	    try {
	      assertEquals("个人信息", TestAll.driver.findElement(By.xpath("//form/div/div/div/div")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	}

}
