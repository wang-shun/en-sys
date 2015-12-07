/*
 * 资源同步
 * */

package com.chinacreator.sysmgr.resourcemgr.testcase.impresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class impresource extends TestCase{

	@Test
	public void testimpresource() throws Exception{
		//展开按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/button")).click();
		//点击导入按钮
		TestAll.driver.findElement(By.xpath("//*[@id='newGroup8']/ul/li[2]/a")).click();
		//等待加载完成
		 for (int second = 0;; second++) {
			 if (second >= 60) fail("timeout");
			 try { if ("个人信息".equals(TestAll.driver.findElement(By.xpath("//tr[2]/td[2]")).getText())) break; } catch (Exception e) {}
			 Thread.sleep(1000);
		    }
		 Common.waitFor(3, TestAll.driver);
		//点击同步按钮
		 TestAll.driver.findElement(By.id("newField5")).click();
		//确认
		 TestAll.driver.findElement(By.id("ok_btn")).click();
//		 for (int second = 0;; second++) {
//			 if (second >= 60) fail("timeout");
//			 try { if ("同步资源成功".equals(TestAll.driver.findElement(By.xpath("//div/div")).getText())) break; } catch (Exception e) {}
//			 Thread.sleep(1000);
//		    }
		try {
		      assertEquals("同步资源成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		Common.waitFor(3, TestAll.driver);
	}

}
