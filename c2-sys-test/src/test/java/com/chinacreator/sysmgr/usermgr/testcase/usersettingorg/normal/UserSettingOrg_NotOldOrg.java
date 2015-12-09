package com.chinacreator.sysmgr.usermgr.testcase.usersettingorg.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class UserSettingOrg_NotOldOrg extends TestCase{

	@Test
	public void testUserSettingOrg_NotOldOrg() throws Exception{
		//查询要设置主机构的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("郝思嘉");
		Common.waitFor(2, TestAll.driver);
						
		//勾选设置主机构的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td[1]/input")).click();
		
		//设置主机构
		TestAll.driver.findElement(By.id("newField18")).click();
		
		//高级设置
		TestAll.driver.findElement(By.id("newField4")).click();
		
		//选择主机构
		TestAll.driver.findElement(By.xpath("//a[@title='株洲市se']/preceding-sibling::*[@class='button chk radio_false_full']")).click();
		
		//去掉用户保留在原机构下
		TestAll.driver.findElement(By.xpath("//*[@id='newField5Wrapper']/div/span/label")).click();
		
		//保存
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
		try {
			assertEquals("设置主机构成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		
		//查询设置主机构的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("郝思嘉");
		Common.waitFor(2, TestAll.driver);
		
		//检查是否设置成功
		try {
			assertEquals("(主)株洲市se", TestAll.driver.findElement(By.xpath("//tr[2]/td[6]")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		 }
	}

}
