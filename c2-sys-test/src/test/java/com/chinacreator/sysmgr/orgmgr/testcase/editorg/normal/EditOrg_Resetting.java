package com.chinacreator.sysmgr.orgmgr.testcase.editorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditOrg_Resetting extends TestCase{

	@Test
	public void testEditOrg_Resetting() throws Exception{
		 // 修改后信息
	    String orgNumber = "103x";
	    String orgName = "hys";
	    String orgShowName = "重置测试";
	    String orgXzqm = "0731";
	    String jp = "hysx";
	    String qp = "changsha";
	    String rgLevel = "市州级";
	    String orgDesc = "编辑机构_重置";
	    //展开刷新按钮
	  	TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/button")).click();
	  	//点击刷新按钮
	  	TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/ul/li[4]/a")).click();
	  	// 等待 zTree 初始化完毕，Timeout 设置10秒
	     try {
	        	(new WebDriverWait(TestAll.driver, 10, 500)).until(new ExpectedCondition<Boolean>() {
	                public Boolean apply(WebDriver d) {
	                    WebElement element = (WebElement) ((JavascriptExecutor)TestAll.driver).executeScript("return $('#orgTree li').get(0);");
	                    return element != null;
	                }
	            });
	             
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    // 选择要修改机构
 		TestAll.driver.findElement(By.xpath("//a[@title='衡阳市修改后']")).click();
 		
 		// 编辑
 		TestAll.driver.findElement(By.id("newField3")).click();
 		    
 	    // 修改信息
 	    TestAll.driver.findElement(By.id("orgNumber")).clear();
 	    TestAll.driver.findElement(By.id("orgNumber")).sendKeys(orgNumber);
 	    TestAll.driver.findElement(By.id("orgName")).clear();
 	    TestAll.driver.findElement(By.id("orgName")).sendKeys(orgName);
 	    TestAll.driver.findElement(By.id("orgShowName")).clear();
 	    TestAll.driver.findElement(By.id("orgShowName")).sendKeys(orgShowName);
 	    TestAll.driver.findElement(By.id("orgXzqm")).clear();
 	    TestAll.driver.findElement(By.id("orgXzqm")).sendKeys(orgXzqm);
 	    TestAll.driver.findElement(By.id("jp")).clear();
 	    TestAll.driver.findElement(By.id("jp")).sendKeys(jp);
 	    TestAll.driver.findElement(By.id("qp")).clear();
 	    TestAll.driver.findElement(By.id("qp")).sendKeys(qp);
 	    new Select(TestAll.driver.findElement(By.id("orgLevel"))).selectByVisibleText(rgLevel);
 	    TestAll.driver.findElement(By.id("orgDesc")).clear();
 	    TestAll.driver.findElement(By.id("orgDesc")).sendKeys(orgDesc);
 	    // 重置
 	    TestAll.driver.findElement(By.id("newField")).click();
 		 
 		 //关闭
 		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	}

}
