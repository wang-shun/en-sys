package com.chinacreator.sysmgr.usermgr.testcase.edituser.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditUser_UserRealnameIsAgain extends TestCase{

	@Test
	public void testEditUser_UserRealnameIsAgain() throws Exception{
		//��ѯҪ�༭���û�
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("����");
		Common.waitFor(2, TestAll.driver);
				
		//��ѡҪ�༭���û�
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//�༭
		TestAll.driver.findElement(By.id("newField14")).click();
		
		//�޸�����
		TestAll.driver.findElement(By.id("userRealname")).clear();
		TestAll.driver.findElement(By.id("userRealname")).sendKeys("����");
		
		//����
		TestAll.driver.findElement(By.id("newField")).click();
		try {
			assertEquals("����ʧ�ܣ�", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//�ر�
		TestAll.driver.findElement(By.id("newField1")).click();
		
		//��ղ�ѯ����
		TestAll.driver.findElement(By.id("userRealname_Field")).clear(); 
	}

}
