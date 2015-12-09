package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.editdicdata.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.editdicdata.normal.EditDicData;
import com.chinacreator.sysmgr.utils.Common;

public class EditDicData_DicDataNameIsNull extends TestCase{
	Logger logger = LoggerFactory.getLogger(EditDicData_DicDataNameIsNull.class);
	@Test
	public void testEditDicData_DicDataNameIsNull() throws Exception{
		//选择字典类型
	    TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
	    
	    //选择要编辑的字典数据
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td/input")).click();
	    
	    //编辑
	    TestAll.driver.findElement(By.id("newField4")).click();
	    
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	        
//	    try {
//		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
		//判断alert为正确弹框还是错误弹框
		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
		if (webElement.getAttribute("class").contains("message-error"))
			logger.info("异常流-编辑字典数据-名称为空： 操作成功@@");
		else if (webElement.getAttribute("class").contains("message-success"))
			{
			logger.error("异常流-编辑字典数据-名称为空： 操作失败!!!");
			Common.TakePic();
			}
		
	    //关闭提示信息
	  	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	  	
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		Common.waitFor(1, TestAll.driver);
		
	}

}
