package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.deldicdata.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class DelDicData_Cancel extends TestCase{

	@Test
	public void testDelDicData_Cancel() throws Exception{
		
		//选择字典类型树
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后']")).click();
		
		//选择要删除的字典数据
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td/input")).click();
	    
	    //删除
	    TestAll.driver.findElement(By.id("newField17")).click();
	    
	    //取消
	    TestAll.driver.findElement(By.id("cancel_btn")).click();
	    Common.waitFor(1, TestAll.driver);
		}

}
