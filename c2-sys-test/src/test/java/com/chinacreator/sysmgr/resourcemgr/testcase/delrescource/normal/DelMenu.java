package com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelMenu extends TestCase{

	@Test
	public void testDelMenu() throws Exception{
		//展开菜单
		TestAll.driver.findElement(By.xpath("//a[@title='菜单']/preceding-sibling::*")).click();
		Common.waitFor(1, TestAll.driver);
		
		//选择需要删除的菜单
		TestAll.driver.findElement(By.xpath("//a[@title='[菜单]iframe修改后']")).click();
		
		//删除
		TestAll.driver.findElement(By.id("newField15")).click();
		
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		
		try {
		      assertEquals("删除资源成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(1, TestAll.driver);
		
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
	}

}
