package com.chinacreator.sysmgr.resourcemgr.testcase.delrescource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelMenu_multiple extends TestCase{

	@Test
	public void testDelMenu_multiple() throws Exception{
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
		
		//要删除的第一个资源
		WebElement del1 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]div中打开']"));
		
		//要删除的第二个资源
		WebElement del2 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]百度_新页面中打开']"));
		
		//要删除的第三个资源
		WebElement del3 = TestAll.driver.findElement(By.xpath("//a[@title='[菜单]不显示菜单']"));
		
		//按住CTRL键多选
		Actions actions = new Actions(TestAll.driver);
		actions.click(del1).keyDown(Keys.CONTROL).click(del2).click(del3).keyUp(Keys.CONTROL).build().perform();
		
		//点击删除按钮
		TestAll.driver.findElement(By.id("newField15")).click();
		Common.waitFor(1, TestAll.driver);
			
		//确认
		TestAll.driver.findElement(By.id("del_btn")).click();
		
		try {
		      assertEquals("删除资源成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
		
		//刷新
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[3]/a")).click();
				
	}

}
