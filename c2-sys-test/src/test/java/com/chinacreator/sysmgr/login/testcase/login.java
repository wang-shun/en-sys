package com.chinacreator.sysmgr.login.testcase;

import static org.junit.Assert.*;
import junit.framework.*;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Window;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class login extends TestCase{
	@Before
	public void setUp() throws Exception {
//		//firefox浏览器
//		TestAll.driver = new FirefoxDriver();
//		
//		//chrome浏览器
//		System.setProperty("webdriver.chrome.driver","C:/webdriver/chromedriver.exe"); 
//		All.driver = new ChromeDriver();
//			
		//TestAll.nodeUrl = "http://172.16.72.5:32768/wd/hub";
		//TestAll.nodeUrl = "http://172.16.25.157:4444/wd/hub";
		
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setBrowserName("firefox");
		TestAll.driver = new RemoteWebDriver(new URL(TestAll.nodeUrl), capability);

	    //TestAll.baseUrl = "http://172.16.25.21:8080";
	    TestAll.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void testlogin() {
		Common.DeleteDirectory();//删除截图目录下的所有文件
//		TestAll.driver.get(TestAll.baseUrl + "/sys");
		TestAll.driver.get(TestAll.baseUrl + "/c2_sys_full_test");
	  	TestAll.driver.manage().window().maximize();
	  	
	  	System.out.println("title,url = "+TestAll.driver.getTitle()+","+TestAll.driver.getCurrentUrl());
	  	
		TestAll.driver.findElement(By.id("username")).clear();
		TestAll.driver.findElement(By.id("username")).sendKeys("admin");
		TestAll.driver.findElement(By.id("password")).clear();
		TestAll.driver.findElement(By.id("password")).sendKeys("123456");
		TestAll.driver.findElement(By.xpath("//button[@type='submit']")).click();
		
//		//判断是否登录成功，失败则截图
//		try 
//		 { 
//			TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div")); //是否出现消息弹出框
//			Common.TakePic();
//		} 
//		catch (Error e) { } 
		System.out.println("登录成功！");
		
		//登录成功后，等待页面加载完成
		for (int second = 0;; second++) {
	    	if (second >= 10) fail("timeout");
	    	try { 
	    		 if ("Hello，系统支持覆盖home.html来实现你自己的首页!".equals(TestAll.driver.findElement(By.cssSelector("h1.ng-scope")).getText())) 
	    			break; 
	    		 } 
	    	catch (Exception e) {
	    	}
	    }
		}
	}


