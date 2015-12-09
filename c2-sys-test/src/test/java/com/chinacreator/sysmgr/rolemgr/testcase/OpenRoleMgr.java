package com.chinacreator.sysmgr.rolemgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.resourcemgr.testcase.impresource.normal.impresource;
import com.chinacreator.sysmgr.utils.Common;
public class OpenRoleMgr extends TestCase{
	Logger logger = LoggerFactory.getLogger(OpenRoleMgr.class);
	@Test
	public void testOpenRoleMgr() {
	  	TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("角色管理")).click();
//	    try {
//	      assertEquals("角色 新增 编辑 删除 设置权限 分配机构 分配用户 刷新", TestAll.driver.findElement(By.id("t_roleGroup")).getText());
//	    } catch (Error e) {
//	      TestAll.verificationErrors.append(e.toString());
//	    }
	    Common.onBlur();
	    Common.waitFor(5, TestAll.driver);
	}

}
