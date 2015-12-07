package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roletouser;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

import junit.framework.TestCase;

public class RoleToUser extends TestCase{
	public void testRoleToUser() throws Exception{
		//刷新
		TestAll.driver.findElement(By.id("newField16")).click();
		Common.waitFor(1, TestAll.driver);
		 
		//选择授予用户的角色
		TestAll.driver.findElement(By.xpath("//td[@title='产品经理']")).click();
		
		//分配用户
		TestAll.driver.findElement(By.id("newField15")).click();
		Common.waitFor(1, TestAll.driver);
		 
		//展开机构
		TestAll.driver.findElement(By.xpath("//a[@title='长沙市']/preceding-sibling::*[@title]")).click();
		
		//勾选角色授予的用户
		TestAll.driver.findElement(By.xpath("//a[@title='张三丰']/preceding-sibling::*[not(@title)]")).click();
		
		//保存
		TestAll.driver.findElement(By.id("newField2")).click();
		 try {
		      assertEquals("分配用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		 //关闭
		 TestAll.driver.findElement(By.id("newField5")).click();
		 Common.waitFor(2, TestAll.driver);
			
	}
}
