package com.chinacreator.sysmgr.login.testcase;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;

public class logout_lilei extends TestCase{

	@Test
	public void testlogout_lilei() {
		TestAll.driver.findElement(By.xpath("//*[@id='navbar-container']/div[2]/ul/li/a")).click();
	    TestAll.driver.findElement(By.linkText("退出")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("C2平台开发者社区 - 登录".equals(TestAll.driver.getTitle())) break; } catch (Exception e) {}
	    }
	    TestAll.driver.quit();
	}
}
