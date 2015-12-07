package com.chinacreator.sysmgr.usermgr.testcase.orderuser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OrderUser extends TestCase{

	@Test
	public void testOrderUser() throws Exception{
		//排序
		TestAll.driver.findElement(By.id("newField16")).click();
		//将表格中第一行的用户拖到第二行
		WebElement FirstRow = TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td[2]"));
		WebElement SecondRow = TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[3]/td[2]"));
		
		Actions actions = new Actions(TestAll.driver);
		actions.clickAndHold(FirstRow).moveToElement(SecondRow).release().build().perform();
		
		try {
			assertEquals("用户排序保存成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(1, TestAll.driver);
		
	}

}
