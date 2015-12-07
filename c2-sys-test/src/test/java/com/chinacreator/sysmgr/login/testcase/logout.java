package com.chinacreator.sysmgr.login.testcase;

import static org.junit.Assert.*;
import junit.framework.*;

import com.chinacreator.sysmgr.TestAll;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class logout extends TestCase{

	@Test
	public void testlogout() {
		TestAll.driver.findElement(By.xpath("//*[@id='navbar-container']/div[2]/ul/li/a")).click();
	    TestAll.driver.findElement(By.linkText("退出")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("C2平台开发者社区 - 登录".equals(TestAll.driver.getTitle())) break; } catch (Exception e) {}
	    }
	    TestAll.driver.quit();
	}


}
