package com.chinacreator.sysmgr.usermgr.testcase.orderuser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.usermgr.testcase.edituser.exception.EditUser_NochooseUser;
import com.chinacreator.sysmgr.utils.Common;

public class OrderUser extends TestCase{
	Logger logger = LoggerFactory.getLogger(OrderUser.class);
	@Test
	public void testOrderUser() throws Exception{
		//排序
		TestAll.driver.findElement(By.id("newField16")).click();
		//将表格中第一行的用户拖到第二行
		WebElement FirstRow = TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td[2]"));
		WebElement SecondRow = TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[3]/td[2]"));
		
		Actions actions = new Actions(TestAll.driver);
		actions.clickAndHold(FirstRow).moveToElement(SecondRow).release().build().perform();

		
		for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("用户排序保存成功！".equals(TestAll.driver.findElement(By.xpath("//div/div")).getText()))
	    		{logger.info("正常流-用户排序：操作成功@@");
	    		break;} 
	    	} catch (Exception e) {
	    		logger.error("正常流-用户排序：操作失败!!!");
	    		Common.TakePic();
	    	}
	    }
		//关闭alert弹框
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
		Common.waitFor(1, TestAll.driver);
		
	}

}
