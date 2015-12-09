/*打开机构管理页
 */
package com.chinacreator.sysmgr.orgmgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.login.testcase.login;
import com.chinacreator.sysmgr.utils.Common;

public class OpenOrgMgr extends TestCase{
	Logger logger = LoggerFactory.getLogger(OpenOrgMgr.class);
	@Test
	public void testOpenOrgMgr() {
		Common.waitFor(2, TestAll.driver);
	  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("机构管理")).click();
		
//		//等待页面加载完成
//		for (int second = 0;; second++) {
//	    	if (second >= 10) {
//	    		fail("timeout");
//	    		logger.error("打开机构管理页超时！");}
//	    	try { if ("机构管理 新增 编辑 删除 移动 刷新".equals(TestAll.driver.findElement(By.xpath("//form/div/div[2]/div/div")).getText())) 
//	    		{logger.info("打开机构管理页成功！");
//	    		break;} 
//	    	} 
//	    	catch (Exception e) {
//	    		Common.TakePic();
//	    		logger.error("打开机构管理页失败！");
//	    	}
//	    }
		
		Common.waitFor(5, TestAll.driver);
		logger.info("打开机构管理页！");
		Common.onBlur();
		  }
	}


