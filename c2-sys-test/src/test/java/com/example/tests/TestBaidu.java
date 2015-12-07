package com.example.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestBaidu {
	WebDriver driver;
	  String baseUrl;
	  String nodeUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
		//nodeUrl = "http://172.16.72.5:32768/wd/hub";
		nodeUrl = "http://172.16.25.157:4444/wd/hub";
		
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setBrowserName("firefox");
		driver = new RemoteWebDriver(new URL(nodeUrl), capability);

//		DesiredCapabilities capability = DesiredCapabilities.htmlUnit();
//		capability.setBrowserName("htmlUnit");
//		driver = new RemoteWebDriver(new URL(nodeUrl), capability);
		
//		DesiredCapabilities capability = DesiredCapabilities.chrome();
//		capability.setBrowserName("chrome");
//		driver = new RemoteWebDriver(new URL(nodeUrl), capability);
		
	    baseUrl = "https://www.baidu.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  public static void snapshot(TakesScreenshot drivername, String filename)
	  {
		  File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		  try {
			  System.out.println("save snapshot path is:C:/"+filename);
			  FileUtils.copyFile(scrFile, new File("C:\\"+filename));
		  } catch (IOException e) {
	              // TODO Auto-generated catch block
	             System.out.println("Can't save screenshot");
	            e.printStackTrace();
	          } 
	          finally
	         {
	             System.out.println("screen shot finished");
	         }
	   }
	  
	  @Test
	  public void testUntitled() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("kw")).click();
	    driver.findElement(By.id("kw")).clear();
	    driver.findElement(By.id("kw")).sendKeys("selenium");
	    driver.findElement(By.id("su")).click();
	    Thread.sleep(2000);
	    snapshot((TakesScreenshot)driver,"open_baidu.png");
	    System.out.println(driver.findElement(By.xpath("//div[@class='s_tab']")).getText());
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	}