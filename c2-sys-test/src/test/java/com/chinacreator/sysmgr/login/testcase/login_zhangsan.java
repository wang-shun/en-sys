package com.chinacreator.sysmgr.login.testcase;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.chinacreator.sysmgr.TestAll;

public class login_zhangsan extends TestCase{
	@Before
	public void setUp() throws Exception {
		//TestAll.driver = new FirefoxDriver();
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setBrowserName("firefox");
		TestAll.driver = new RemoteWebDriver(new URL(TestAll.nodeUrl), capability);
		
	    //TestAll.baseUrl = "http://172.16.25.21:8080/";
	    TestAll.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	@Test
	public void testlogin_zhangsan() {
		TestAll.driver.get(TestAll.baseUrl + "/sys");
	  	TestAll.driver.manage().window().maximize();
		TestAll.driver.findElement(By.id("username")).clear();
		TestAll.driver.findElement(By.id("username")).sendKeys("zhangsan");
		TestAll.driver.findElement(By.id("password")).clear();
		TestAll.driver.findElement(By.id("password")).sendKeys("123456");
		TestAll.driver.findElement(By.xpath("//button[@type='submit']")).click();
	 	try {
	      assertEquals("首页 \n 首页 Hello，系统支持覆盖home.html来实现你自己的首页! \n \n \n \n 科创信息 © 2013-2014", TestAll.driver.findElement(By.id("main-container")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	}

}