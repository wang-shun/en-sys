package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roleprivilege.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleSetPrivilege_predefine extends TestCase{

	@Test
	public void testRoleSetPrivilege_predefine() throws Exception{
		//查询要设置权限的角色
		 TestAll.driver.findElement(By.id("newField")).clear();
		 TestAll.driver.findElement(By.id("newField")).sendKeys("组长角色");
		 Common.waitFor(1, TestAll.driver);
		 		 
		 //选择要设置权限的角色
		 TestAll.driver.findElement(By.xpath("//td[@title='组长角色']")).click();
		 
		 //设置权限
		 TestAll.driver.findElement(By.id("newField13")).click();
		 
		 //展开预定义
		 TestAll.driver.findElement(By.xpath("//*[@id='newField2Wrapper']/div/div[1]/span[1]/i")).click();
		 
		 //展开角色类型
		 TestAll.driver.findElement(By.xpath("//a[@title='一般类型']/preceding-sibling::*[(@title)]")).click();
		 
		 //勾选预定义的角色名
		 TestAll.driver.findElement(By.xpath("//a[@title='产品经理']/preceding-sibling::*[not(@title)]")).click();
		 
		 //点击搜索输入框写入
		 TestAll.driver.findElement(By.xpath("//*[@id='newField2Wrapper']/div/input")).click();
		 
		 //保存
		 TestAll.driver.findElement(By.id("newField3")).click();
		 
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
