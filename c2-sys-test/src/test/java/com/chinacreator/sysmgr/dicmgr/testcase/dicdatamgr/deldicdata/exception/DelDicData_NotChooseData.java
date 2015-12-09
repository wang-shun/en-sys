package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.deldicdata.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.deldicdata.normal.DelDicData_All;
import com.chinacreator.sysmgr.utils.Common;

public class DelDicData_NotChooseData extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelDicData_NotChooseData.class);
	@Test
	public void testDelDicData_NotChooseData() throws Exception{
		//选择字典类型树
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
				   
	    //删除
	    TestAll.driver.findElement(By.id("newField17")).click();
	    
//	    try {
//		      assertEquals("请选择需要删除的字典数据", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
	    
	  //判断alert为正确弹框还是错误弹框
  		WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
  		if (webElement.getAttribute("class").contains("message-error"))
  			logger.info("异常流-删除字典数据-未选择数据： 操作成功@@");
  		else if (webElement.getAttribute("class").contains("message-success"))
  			{
  			logger.error("异常流-删除字典数据-未选择数据： 操作失败!!!");
  			Common.TakePic();
  			}
  		
	    //关闭提示信息
  		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
  		Common.waitFor(1, TestAll.driver);	    
	}

}
