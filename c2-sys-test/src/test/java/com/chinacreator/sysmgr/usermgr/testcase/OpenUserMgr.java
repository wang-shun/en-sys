package com.chinacreator.sysmgr.usermgr.testcase;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class OpenUserMgr extends TestCase{

	@Test
	public void testOpenUserMgr() {
		TestAll.driver.findElement(By.linkText("系统管理")).click();
		TestAll.driver.findElement(By.linkText("系统设置")).click();
		TestAll.driver.findElement(By.linkText("用户管理")).click();
	    try {
	      assertEquals("用户管理 新增 编辑 删除 排序 设置主机构 设置岗位 重置密码 刷新", TestAll.driver.findElement(By.id("t_userGroup")).getText());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	      Common.TakePic();
	    }
	    Common.onBlur();
	}

}
