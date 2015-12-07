/*
 * 新增角色：点击新增按钮后再选择角色类型，是否使用设置为不使用，对角色进行授权操作。
 */
package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.normal;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

import junit.framework.TestCase;

public class AddRole_Res extends TestCase{
	public void testAddRole_IsNotUse() throws Exception{
		
	    //新增
	    TestAll.driver.findElement(By.id("newField10")).click();
	    
	    //选择角色类型
	    TestAll.driver.findElement(By.xpath("//*[@id='newFieldWrapper']/div/input")).click();
	    TestAll.driver.findElement(By.xpath("//a[@title='业务类型']")).click();
	    	    
	    //角色名称
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("字典管理员");
	    
	    //角色描述
	    TestAll.driver.findElement(By.id("roleDesc")).clear();
	    TestAll.driver.findElement(By.id("roleDesc")).sendKeys("测试角色");

	    //角色授予资源
		 //=============授予菜单权限=====================
		 //展开菜单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[1]/span[1]")).click();
		 Common.waitFor(3, TestAll.driver);
		 		 
		//展开系统管理
		 TestAll.driver.findElement(By.xpath("//a[@title='系统管理']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(2, TestAll.driver);
		 
		 //展开系统设置菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='系统设置']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		//勾选字典管理菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='字典管理']/preceding-sibling::*[not(@title)]")).click();
		 
		
		 //===============授予表单权限=======================
		 //展开表单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //展开字典管理
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[18]/span[1]")).click();
		 
		 //勾选新增字典类型
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[18]/ul/li[1]/span[2]")).click();

		 //勾选编辑字典类型
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[18]/ul/li[2]/span[2]")).click();

		 //勾选新增字典数据
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[18]/ul/li[4]/span[2]")).click();

		 //勾选编辑字典数据
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[18]/ul/li[5]/span[2]")).click();
		 
		 
		//展开新增编辑字典类型
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[19]/span[1]")).click();
		 
		 //勾选新增编辑字典类型-保存
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[19]/ul/li/span[2]")).click();
		
		 //展开新增编辑字典数据
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[20]/span[1]")).click();
		 
		 //勾选新增编辑字典数据-保存
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[20]/ul/li/span[2]")).click();



		 
		 //===============授予服务权限=======================
		 //展开服务
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选新增字典类型
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[29]/span[2]")).click();
		 
		 //勾选编辑字典类型
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[30]/span[2]")).click();
		 
		 //勾选新增字典数据
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[32]/span[2]")).click();
		 
		 //勾选编辑字典数据
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[33]/span[2]")).click();
		 
	    //保存
	    TestAll.driver.findElement(By.id("newField3")).click();
	    	    
	    try {
		      assertEquals("新增角色成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	    TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    Common.waitFor(1, TestAll.driver);
	    
	    //关闭
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(2, TestAll.driver);
	}

}
