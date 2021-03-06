package com.chinacreator.sysmgr.dicmgr.testcase.dictypemgr.editdictype.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditDicType_TypeNameIsAgain extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditDicType_TypeNameIsAgain.class);
	@Test
	public void testEditDicType_TypeNameIsAgain() throws Exception{
		//选择要编辑的类型
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
	
	    //编辑
	    TestAll.driver.findElement(By.id("newField8")).click();
	    
	    //字典类型名称
	    TestAll.driver.findElement(By.id("dicttypeName")).clear();
	    TestAll.driver.findElement(By.id("dicttypeName")).sendKeys("性别");
	      
	    
	    //字典类型描述
	    TestAll.driver.findElement(By.id("dicttypeDesc")).clear();
	    TestAll.driver.findElement(By.id("dicttypeDesc")).sendKeys("测试类型修改后");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField2")).click();
	    
//	    try {
//		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-error"))
			logger.info("异常流-编辑字典类型-类型名称重复： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.error("异常流-编辑字典类型-类型名称重复： 操作失败!!!");
			Common.TakePic();
			}
		
		//关闭提示信息
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		
	    //关闭
	  	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	  	Common.waitFor(2, TestAll.driver);
	  	
	  	//展开刷新按钮
  		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
  		
  		//点击刷新按钮
  		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[2]/a")).click();	
  		Common.waitFor(2, TestAll.driver);
	}

}
