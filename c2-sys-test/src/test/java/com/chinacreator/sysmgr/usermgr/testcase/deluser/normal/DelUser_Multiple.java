package com.chinacreator.sysmgr.usermgr.testcase.deluser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelUser_Multiple extends TestCase{

	@Test
	public void testDelUser_Multiple() throws Exception{
		//修改每页显示条数
		TestAll.driver.findElement(By.xpath("//option[@value='100']")).click();
		Common.waitFor(3, TestAll.driver);
		
		//全选用户
		TestAll.driver.findElement(By.id("cb_userGroup")).click();
		
		//删除
		TestAll.driver.findElement(By.id("newField15")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		try {
			assertEquals("删除用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
	}

}
