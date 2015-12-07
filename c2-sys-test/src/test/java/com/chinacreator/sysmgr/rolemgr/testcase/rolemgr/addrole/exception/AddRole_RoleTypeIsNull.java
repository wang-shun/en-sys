package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.addrole.exception;

import junit.framework.TestCase;

import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class AddRole_RoleTypeIsNull extends TestCase{
	public void testAddRole_RoleTypeIsNull() throws Exception{
	 
	    //����
	    TestAll.driver.findElement(By.id("newField10")).click();
	    
	    
	    //��ɫ����
	    TestAll.driver.findElement(By.id("roleName")).clear();
	    TestAll.driver.findElement(By.id("roleName")).sendKeys("test");
	    
	    //��ɫ����
	    TestAll.driver.findElement(By.id("roleDesc")).clear();
	    TestAll.driver.findElement(By.id("roleDesc")).sendKeys("test");

	    //����
	    TestAll.driver.findElement(By.id("newField3")).click();
	    	    
	    try {
		      assertEquals("����ʧ�ܣ���֤δͨ��", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	    Common.waitFor(1, TestAll.driver);
	    
	    //�ر�
	    TestAll.driver.findElement(By.id("newField5")).click();
	    Common.waitFor(3, TestAll.driver);
	}
}
