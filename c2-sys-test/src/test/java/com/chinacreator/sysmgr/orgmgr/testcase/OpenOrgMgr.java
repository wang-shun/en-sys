/*打开机构管理页
 */
package com.chinacreator.sysmgr.orgmgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenOrgMgr extends TestCase{

	@Test
	public void testOpenOrgMgr() {
		Common.waitFor(2, TestAll.driver);
	  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("机构管理")).click();
//		//等待页面加载完成
//		for (int second = 0;; second++) {
//	    	if (second >= 60) fail("timeout");
//	    	try { if ("机构管理 新增 编辑 删除 移动 刷新".equals(TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/div")).getText())) break; } 
//	    	catch (Exception e) {
//	    		Common.TakePic();
//	    	}
//	    }
		Common.waitFor(1, TestAll.driver);
		Common.onBlur();
		  }
	}


