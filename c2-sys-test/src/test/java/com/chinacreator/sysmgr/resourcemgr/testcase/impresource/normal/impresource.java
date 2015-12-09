/*
 * 资源同步
 */

package com.chinacreator.sysmgr.resourcemgr.testcase.impresource.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.resourcemgr.testcase.editresource.normal.EditMenu;
import com.chinacreator.sysmgr.utils.Common;

public class impresource extends TestCase{
	Logger logger = LoggerFactory.getLogger(impresource.class);
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
//
//		try {
//		      assertEquals("同步资源成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//		    } catch (Error e) {
//		      TestAll.verificationErrors.append(e.toString());
//		    }
		 
			//判断alert为正确弹框还是错误弹框
			WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
			if (webElement.getAttribute("class").contains("message-success"))
				{
				logger.info("正常流-同步资源: 操作成功@@");
				//关闭alert弹框
				TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
				}
			else 
				{
				logger.error("正常流-同步资源： 操作失败!!!");
				Common.TakePic();
				}
			
		Common.waitFor(3, TestAll.driver);
	}

}
