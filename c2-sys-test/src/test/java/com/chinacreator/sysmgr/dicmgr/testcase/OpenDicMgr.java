package com.chinacreator.sysmgr.dicmgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenDicMgr extends TestCase{

	@Test
	public void testOpenDicMgr() throws Exception{
		TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("字典管理")).click();
	    try {
	      assertEquals("字典类型 新增 编辑 删除 刷新", TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/div")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }
	    Common.onBlur();
	    Common.waitFor(1, TestAll.driver);
	}

}
