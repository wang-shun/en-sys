package com.chinacreator.sysmgr.usermgr.testcase.usertojob.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class UserToJob extends TestCase{

	@Test
	public void testUserToJob() throws Exception{
		//查询设置岗位的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("司思");
		Common.waitFor(2, TestAll.driver);
		
		//勾选用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//设置岗位
		TestAll.driver.findElement(By.id("newField2")).click();
		
		//勾选管理员岗位
		TestAll.driver.findElement(By.xpath("//div[@tree-id='jobTree']/ul/li/ul/li/a[@title='管理员']/preceding-sibling::*[not(@title)]")).click();
		
		//保存
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
		try {
			assertEquals("设置岗位成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		
		//清除查询条件
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
	}

}
