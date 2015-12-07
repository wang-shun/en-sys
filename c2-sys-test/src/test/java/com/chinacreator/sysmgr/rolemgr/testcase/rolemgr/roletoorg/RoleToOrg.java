package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roletoorg;

import junit.framework.TestCase;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleToOrg extends TestCase{
	public void testRoleToOrg() throws Exception{
		//选择授予用户的角色
		TestAll.driver.findElement(By.xpath("//td[@title='字典管理员']")).click();
		Common.waitFor(2, TestAll.driver);
		//分配机构
		TestAll.driver.findElement(By.id("newField14")).click();
		//勾选角色授予的机构
		TestAll.driver.findElement(By.xpath("//a[@title='株洲市']/preceding-sibling::*[not(@title)]")).click();
		//保存
		TestAll.driver.findElement(By.id("newField2")).click();
		 try {
		      assertEquals("分配机构成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		 //关闭
		 TestAll.driver.findElement(By.id("newField5")).click();
		 
	}		

}
