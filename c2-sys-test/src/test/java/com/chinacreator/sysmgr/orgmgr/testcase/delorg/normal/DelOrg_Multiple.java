package com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelOrg_Multiple extends TestCase{

	@Test
	public void testDelOrg_Multiple() throws Exception{
		// 要删除的机构
		WebElement org1 = TestAll.driver.findElement(By.xpath("//a[@title='株洲市']"));
		WebElement org2 = TestAll.driver.findElement(By.xpath("//a[@title='湘潭市']"));
		WebElement org3 = TestAll.driver.findElement(By.xpath("//a[@title='长沙市']"));
		
		//多选
		Actions actions = new Actions(TestAll.driver);
		actions.click(org1).keyDown(Keys.CONTROL).click(org2).click(org3).keyUp(Keys.CONTROL).build().perform();
	
		//删除
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/ul/li[1]/a")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		try {
		      assertEquals("删除机构成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
	}

}
