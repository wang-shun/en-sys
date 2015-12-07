package com.chinacreator.sysmgr.orgmgr.testcase.delorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelOrg extends TestCase{

	@Test
	public void testDelOrg() throws Exception{
		// 选择要删除的机构
		TestAll.driver.findElement(By.xpath("//a[@title='衡阳市修改后']")).click();
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
