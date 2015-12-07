package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roleprivilege.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleSetPrivilege extends TestCase{

	@Test
	public void testrolesetprivilege() throws Exception{
		
		//查询要设置权限的角色
		 TestAll.driver.findElement(By.id("newField")).clear();
		 TestAll.driver.findElement(By.id("newField")).sendKeys("产品经理");
		 Common.waitFor(1, TestAll.driver);
		 		 
		 //选择要设置权限的角色
		 TestAll.driver.findElement(By.xpath("//td[@title='产品经理']")).click();
		 
		 //设置权限
		 TestAll.driver.findElement(By.id("newField13")).click();
		 Common.waitFor(5, TestAll.driver);
		 
		 //=============授予菜单权限=====================
//		//利用 expandNode 方法展开菜单下的所有子节点
//		 ((JavascriptExecutor)TestAll.driver).executeScript("window.zTreeObj = $.fn.zTree.getZTreeObj('resTree');");
//		 ((JavascriptExecutor)TestAll.driver).executeScript("window.zTreeNode = window.zTreeObj.getNodeByParam('id','resTree_2'); window.zTreeObj.expandTestAll(true);");
//		 Common.waitFor(3, TestAll.driver);	 
//	        
//		 
		 //展开菜单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[1]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 		 
		//展开系统管理
		 TestAll.driver.findElement(By.xpath("//a[@title='系统管理']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //展开日志管理菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='日志管理']/preceding-sibling::*[@title]")).click();
		 Common.waitFor(1, TestAll.driver);
		
		//勾选日志查询菜单
		 TestAll.driver.findElement(By.xpath("//a[@title='日志查询']/preceding-sibling::*[not(@title)]")).click();
		 
		
		 //===============授予表单权限=======================
		 //展开表单
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //展开日志查询
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选日志列表
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[1]/span[2]")).click();

		 //勾选备份日志
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[2]/span[2]")).click();

		 //勾选历史日志列表
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[3]/span[2]")).click();

		 //勾选删除历史日志
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[4]/span[2]")).click();
		 
		 //勾选日志配置
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[5]/span[2]")).click();
		 
		 //勾选编辑日志配置
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[21]/ul/li[6]/span[2]")).click();
		 
		//展开备份日志
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[22]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选备份
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[2]/ul/li[22]/ul/li/span[2]")).click();


		 
		 //===============授予服务权限=======================
		 //展开服务
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/span[1]")).click();
		 Common.waitFor(1, TestAll.driver);
		 
		 //勾选备份日志
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[42]/span[2]")).click();
		 
		 //勾选删除历史日志
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[43]/span[2]")).click();
		 
		 //勾选修改日志记录状态
		 TestAll.driver.findElement(By.xpath("//ul[@id='resTree']/li/ul/li[4]/ul/li[44]/span[2]")).click();
		 
	
		 
		 //保存
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		 
		 try {
		      assertEquals("设置权限成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		 //关闭
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		 Common.waitFor(2, TestAll.driver);
		 
		 //清除查询条件
		 TestAll.driver.findElement(By.id("newField")).clear();
		 
		 //刷新
		 TestAll.driver.findElement(By.id("newField16")).click();
		 Common.waitFor(1, TestAll.driver);
		 
	}

}
