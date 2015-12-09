package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.adddicdata.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dictypemgr.adddictype.normal.AddDicType;
import com.chinacreator.sysmgr.utils.Common;


public class AddDicData extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddDicData.class);
	@Test
	public void testAddDicData() throws Exception{
		//选择字典类型
	    TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
	    
	    //新增
	    TestAll.driver.findElement(By.id("newField")).click();
	    
	    //字典真实值
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    TestAll.driver.findElement(By.id("dictdataName")).sendKeys("1");
	    
	    //字典显示值
	    TestAll.driver.findElement(By.id("dictdataValue")).clear();
	    TestAll.driver.findElement(By.id("dictdataValue")).sendKeys("一季度");
	    
	    //是否默认值
	    new Select(TestAll.driver.findElement(By.id("dictdataIsdefault"))).selectByVisibleText("是");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	    
//	    try {
//		      assertEquals("新增字典数据成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-success"))
			logger.info("正常流-新增字典数据1： 添加成功@@");
		else if (webElement.getAttribute("class").contains("message-error"))
			{
			logger.error("正常流-新增字典数据1： 添加失败!!!");
			Common.TakePic();
			}
		
		//关闭提示信息
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		Common.waitFor(2, TestAll.driver);
	    
	    //新增第二条记录
		//新增
		 TestAll.driver.findElement(By.id("newField")).click();
		
	    //字典真实值
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    TestAll.driver.findElement(By.id("dictdataName")).sendKeys("2");
	    
	    //字典显示值
	    TestAll.driver.findElement(By.id("dictdataValue")).clear();
	    TestAll.driver.findElement(By.id("dictdataValue")).sendKeys("二季度");
	    
	    //是否默认值
	    new Select(TestAll.driver.findElement(By.id("dictdataIsdefault"))).selectByVisibleText("否");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	    
//	    try {
//		      assertEquals("新增字典数据成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
	    //判断alert为正确弹框还是错误弹框
	    WebElement webElement2 = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
  		if (webElement2.getAttribute("class").contains("message-success"))
  			logger.info("正常流-新增字典数据2： 添加成功@@");
  		else if (webElement.getAttribute("class").contains("message-error"))
  			{
  			logger.error("正常流-新增字典数据2： 添加失败!!!");
  			Common.TakePic();
  			}
	  		
	  		
	    //关闭提示信息
	  	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	  	
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		Common.waitFor(2, TestAll.driver);
		
	    //新增第三条记录
		//新增
		 TestAll.driver.findElement(By.id("newField")).click();
		 
	    //字典真实值
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    TestAll.driver.findElement(By.id("dictdataName")).sendKeys("3");
	    
	    //字典显示值
	    TestAll.driver.findElement(By.id("dictdataValue")).clear();
	    TestAll.driver.findElement(By.id("dictdataValue")).sendKeys("三季度");
	    
	    //是否默认值
	    new Select(TestAll.driver.findElement(By.id("dictdataIsdefault"))).selectByVisibleText("否");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	    
//	    try {
//		      assertEquals("新增字典数据成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
	    //判断alert为正确弹框还是错误弹框
	    WebElement webElement3 = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement3.getAttribute("class").contains("message-success"))
  			logger.info("正常流-新增字典数据3： 添加成功@@");
  		else if (webElement.getAttribute("class").contains("message-error"))
  			{
  			logger.error("正常流-新增字典数据3： 添加失败!!!");
  			Common.TakePic();
  			}
	  		
	  		
	    //关闭提示信息
	  	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	  	
	    //关闭
	  	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		Common.waitFor(2, TestAll.driver);	
		
	}

}
