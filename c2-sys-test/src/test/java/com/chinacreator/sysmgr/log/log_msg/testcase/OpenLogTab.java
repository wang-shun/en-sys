package com.chinacreator.sysmgr.log.log_msg.testcase;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import com.chinacreator.sysmgr.TestAll;


public class OpenLogTab extends TestCase{
	  public void testOpenLog() throws Exception {
		  	
		    TestAll.driver.findElement(By.xpath("//div[@id='newGroup']/ul/li[1]/a/div")).click();
	  }
}
