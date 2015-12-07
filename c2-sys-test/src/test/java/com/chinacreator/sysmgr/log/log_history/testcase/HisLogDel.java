package com.chinacreator.sysmgr.log.log_history.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class HisLogDel extends TestCase{
	public void testLogHisDel() throws Exception {
	  	//选择第一条日志
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td/input")).click();
	    //删除
	    TestAll.driver.findElement(By.xpath("//div[@id='t_logHistoryGroup']/div/button[2]")).click();
	    Common.onBlur();
	    //确认
	    TestAll.driver.findElement(By.id("del_btn")).click();
	    Common.waitFor(1, TestAll.driver);
	    try {
	      assertTrue(TestAll.driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*删除历史日志成功[\\s\\S]*$"));
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    }

	  }
}
