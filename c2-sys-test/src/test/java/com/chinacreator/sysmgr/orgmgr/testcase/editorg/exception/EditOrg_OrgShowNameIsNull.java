package com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditOrg_OrgShowNameIsNull extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditOrg_OrgShowNameIsNull.class);
	@Test
	public void testEditOrg_OrgShowNameIsNull() throws Exception {
		// 修改后信息
	    String orgNumber = "test";
	    String orgName = "test";
	    String orgShowName = "";
	    String orgXzqm = "07331";
	    String orgDesc = "编辑机构_修改机构显示名称为空";
	    
	    // 选择要修改机构
	 	TestAll.driver.findElement(By.xpath("//a[@title='衡阳市修改后se']")).click();
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
 	    TestAll.driver.findElement(By.id("orgDesc")).clear();
 	    TestAll.driver.findElement(By.id("orgDesc")).sendKeys(orgDesc);
 	    // 保存
 	    TestAll.driver.findElement(By.id("newField1")).click();
 	   
// 		try {
// 		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//
// 		    } catch (Error e) {
// 		      TestAll.verificationErrors.append(e.toString());
// 		      Common.TakePic();
//
// 		    }
 	    
 	   //判断alert为正确弹框还是错误弹框
 		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
 		if (webElement.getAttribute("class").contains("message-error"))
 			logger.info("异常流-编辑机构-机构显示名称为空： 操作成功！");
 		else if (webElement.getAttribute("class").contains("message-success"))
 			{
 			logger.error("异常流-编辑机构-机构显示名称为空： 操作失败！");
 			Common.TakePic();
 			}
 	 	//关闭alert弹框
 		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
 		 
 		 //关闭
 		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
 		 
		 Common.waitFor(3, TestAll.driver);
	}

}
