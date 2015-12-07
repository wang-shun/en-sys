package com.chinacreator.sysmgr.orgmgr.testcase.addorg.exception;

import static org.junit.Assert.*;
import junit.framework.*;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.orgmgr.bean.OrgBean;
import com.chinacreator.sysmgr.orgmgr.testcase.addorg.ReadOrgXlsUtils;
import com.chinacreator.sysmgr.utils.Common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Test;



public class AddOrgXlsException extends TestCase{

	@Test
	public void testAddOrgXlsException() throws Exception{
		List<OrgBean> list = ReadOrgXlsUtils.getInstance().testData(ReadOrgXlsUtils.EXCEPTIONDATA);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				OrgBean bean = list.get(i);
				// 选择父机构
				TestAll.driver.findElement(By.xpath(bean.getParentorg())).click();
				// 新增
				TestAll.driver.findElement(By.id("newField2")).click();
				// 输入信息
				TestAll.driver.findElement(By.id("orgNumber")).clear();
				TestAll.driver.findElement(By.id("orgNumber")).sendKeys(bean.getOrgNumber());
				TestAll.driver.findElement(By.id("orgName")).clear();
				TestAll.driver.findElement(By.id("orgName")).sendKeys(bean.getOrgName());
				TestAll.driver.findElement(By.id("orgShowName")).clear();
				TestAll.driver.findElement(By.id("orgShowName")).sendKeys(bean.getOrgShowName());
				TestAll.driver.findElement(By.id("orgXzqm")).clear();
				TestAll.driver.findElement(By.id("orgXzqm")).sendKeys(bean.getOrgXzqm());
				TestAll.driver.findElement(By.id("jp")).clear();
				TestAll.driver.findElement(By.id("jp")).sendKeys(bean.getJp());
				TestAll.driver.findElement(By.id("qp")).clear();
				TestAll.driver.findElement(By.id("qp")).sendKeys(bean.getQp());
				WebElement orgLevel = TestAll.driver.findElement(By.id("orgLevel"));
				orgLevel.sendKeys(bean.getRgLevel());
				TestAll.driver.findElement(By.id("orgDesc")).clear();
				TestAll.driver.findElement(By.id("orgDesc")).sendKeys(bean.getOrgDesc());
				// 保存
				TestAll.driver.findElement(By.id("newField1")).click();
				
//				for (int second = 0;; second++) {
//			    	if (second >= 30) fail("timeout");
//			    	try { if ("保存失败！".equals(TestAll.driver.findElement(By.xpath("//div/div")).getText()))break;
//			    	} catch (Exception e) {
//					    	
//			    	}
//			    }
				
				Common.waitFor(1, TestAll.driver);

				 
				 //关闭
				 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
				 Common.waitFor(3, TestAll.driver);
			}
		}
	}

}
