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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.orgmgr.testcase.addorg.exception.AddOrgXlsException;
import com.chinacreator.sysmgr.utils.Common;


public class EditOrg extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditOrg.class);
	public void testEditOrg() throws Exception {
		String orgNumber = "103x";
		String orgName = "hys";
		String orgShowName = "衡阳市修改后se";
		String orgXzqm = "0731";
		String jp = "hysx";
		String qp = "changsha";
		String rgLevel = "市州级";
		String orgDesc = "编辑机构";
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
		TestAll.driver.findElement(By.xpath("//a[@title='衡阳市se']")).click();
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
	    // 保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	    
	  //判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-编辑机构： 操作成功！");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-编辑机构： 操作失败！");
			Common.TakePic();
			}
	   
//		for (int second = 0;; second++) {
//	    	if (second >= 60) fail("timeout");
//	    	try { if ("编辑机构成功！".equals(TestAll.driver.findElement(By.xpath("//div/div")).getText()))break;
//	    	} catch (Exception e) {
//			    	
//	    	}
//	    }
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		
		 //关闭
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		 Common.waitFor(1,TestAll.driver);
		 
	  }
	}


