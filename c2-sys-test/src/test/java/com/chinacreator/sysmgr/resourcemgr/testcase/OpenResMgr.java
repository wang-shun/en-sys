package com.chinacreator.sysmgr.resourcemgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenResMgr extends TestCase{

	@Test
	public void testOpenResMgr() throws Exception{
		TestAll.driver.manage().window().maximize();
	  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("资源维护")).click();
	    try {
	      assertEquals("资源 新增 删除 移动 导入 刷新", TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/div")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	    Common.onBlur();
	    Common.waitFor(2, TestAll.driver);
	}

}
