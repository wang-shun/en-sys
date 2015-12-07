package com.chinacreator.sysmgr.orgmgr.testcase.editorg.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditOrg_OrgNameIsNull extends TestCase{

	@Test
	public void testEditOrg_OrgNameIsNull() throws Exception{
		String orgNumber = "test";
	    String orgName = "";
	    String orgShowName = "test";
	    String orgXzqm = "07331";
	    String orgDesc = "编辑机构_修改机构名称为空";
		// 选择要修改机构
		TestAll.driver.findElement(By.xpath("//a[@title='衡阳市修改后']")).click();
		// 编辑
		TestAll.driver.findElement(By.id("newField3")).click();
		    
	    // 修改信息
	    TestAll.driver.findElement(By.id("orgNumber")).clear();
	    TestAll.driver.findElement(By.id("orgNumber")).sendKeys(orgNumber);
	    TestAll.driver.findElement(By.id("orgName")).clear();
	    TestAll.driver.findElement(By.id("orgName")).sendKeys(orgName);
	    TestAll.driver.findElement(By.id("orgShowName")).clear();
	    TestAll.driver.findElement(By.id("orgShowName")).sendKeys(orgShowName);
	    TestAll.driver.findElement(By.id("orgXzqm")).clear();
	    TestAll.driver.findElement(By.id("orgXzqm")).sendKeys(orgXzqm);
	    TestAll.driver.findElement(By.id("orgDesc")).clear();
	    TestAll.driver.findElement(By.id("orgDesc")).sendKeys(orgDesc);
	    // 保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	   
		try {
		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		      Common.TakePic();
		    }
		 
		 //关闭
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
		 Common.waitFor(3, TestAll.driver);
	}

}
