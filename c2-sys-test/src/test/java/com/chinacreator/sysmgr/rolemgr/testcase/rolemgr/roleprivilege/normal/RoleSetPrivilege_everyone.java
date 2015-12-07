package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roleprivilege.normal;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

import junit.framework.TestCase;

public class RoleSetPrivilege_everyone extends TestCase{
	@Test
	public void testRoleSetPrivilege_everyone() throws Exception{
		
		//查询要设置权限的角色
		 TestAll.driver.findElement(By.id("newField")).clear();
		 TestAll.driver.findElement(By.id("newField")).sendKeys("普通用户");
		 Common.waitFor(1, TestAll.driver);
		 		 
		 //选择要设置权限的角色
		 TestAll.driver.findElement(By.xpath("//td[@title='普通用户']")).click();
		 
		 //设置权限
		 TestAll.driver.findElement(By.id("newField13")).click();
		 Common.waitFor(3, TestAll.driver);
		 
		 //=============授予菜单权限=====================
		 //展开菜单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[1]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 		 
		//展开系统管理
		 TestAll.driver.findElement(By.xpath("//a[@title='系统管理']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //展开我的面板菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='我的面板']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(1, TestAll.driver);
		
		//勾选个人信息菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='个人信息']/preceding-sibling::*[not(@title)]")).click();
		 
		 //勾选密码修改菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='密码修改']/preceding-sibling::*[not(@title)]")).click();
		 
		
		 //===============授予表单权限=======================
		 //展开表单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //展开个人信息
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[1]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选个人信息-保存
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[1]/ul/li[1]/span[2]")).click();
 
		//展开密码修改
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[2]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选密码修改-保存
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[2]/ul/li[1]/span[2]")).click();


		 
		 //===============授予服务权限=======================
		 //展开服务
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选保存个人信息
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[1]/span[2]")).click();
		 
		 //勾选修改密码
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[1]/span[2]")).click();
			 
	
		 
		 //保存
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		 
		 try {
		      assertEquals("设置权限成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		 //关闭
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		 
		 //清除查询条件
		 TestAll.driver.findElement(By.id("newField")).clear();
	}

}
