package com.chinacreator.sysmgr.dicmgr.testcase.dictypemgr.deldictype.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.deldicdata.exception.DelDicData_NotChooseData;
import com.chinacreator.sysmgr.utils.Common;

public class DelDicType_Cancel extends TestCase{
	Logger logger = LoggerFactory.getLogger(DelDicType_Cancel.class);
	@Test
	public void testDelDicType_Cancel() throws Exception{
		//选择要删除的字典类型
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
		
		//展开删除按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
		
		//删除
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[1]/a")).click();
		
		//取消
		TestAll.driver.findElement(By.id("cancel_btn")).click();
		Common.waitFor(1, TestAll.driver);
		logger.info("删除字典类型-取消：操作成功！");
	}

}
