package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.order.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DicDataOrder extends TestCase{

	@Test
	public void testDicDataOrder() throws Exception{
		//选择字典类型
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后']")).click();
		
		//点击排序按钮
		TestAll.driver.findElement(By.id("newField18")).click();
		
		//第一行数据
		WebElement FirstRow = TestAll.driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]"));
		//第二行数据
		WebElement SecondRow = TestAll.driver.findElement(By.xpath("//table/tbody/tr[3]/td[2]"));
		
		//拖动排序
		Actions actions = new Actions(TestAll.driver);
		actions.clickAndHold(FirstRow).moveToElement(SecondRow).release().build().perform();
		
		try {
			assertEquals("字典数据排序保存成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭排序
		TestAll.driver.findElement(By.id("newField18")).click();
		
		//刷新
		TestAll.driver.findElement(By.id("newField20")).click();
		
		//点击列头标题排序
		TestAll.driver.findElement(By.id("jqgh_dictDataGroup_dictdataName")).click();
		Common.waitFor(1, TestAll.driver);
		
	}

}
