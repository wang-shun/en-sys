package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.deldicdata.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.search.normal.DicDataSearch;
import com.chinacreator.sysmgr.utils.Common;

public class DelDicData_Cancel extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelDicData_Cancel.class);
	@Test
	public void testDelDicData_Cancel() throws Exception{
		
		//选择字典类型树
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
		
		//选择要删除的字典数据
	    TestAll.driver.findElement(By.xpath("//tbody/tr/td[@title='1se']")).click();
	    
	    //删除
	    TestAll.driver.findElement(By.id("newField17")).click();
	    
	    //取消
	    TestAll.driver.findElement(By.id("cancel_btn")).click();
	    Common.waitFor(1, TestAll.driver);
	    logger.info("删除字典数据-取消：操作成功！");
		}

}
