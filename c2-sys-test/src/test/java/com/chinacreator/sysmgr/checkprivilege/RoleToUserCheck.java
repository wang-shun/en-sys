package com.chinacreator.sysmgr.checkprivilege;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleToUserCheck extends TestCase{
	@Test
	public void testRoleToUserCheck() throws Exception{
		TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("日志管理")).click();
		TestAll.driver.findElement(By.linkText("日志查询")).click();
	    try {
	      assertEquals("日志列表 \n 历史日志列表 \n 日志配置", TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/ul")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	}
}