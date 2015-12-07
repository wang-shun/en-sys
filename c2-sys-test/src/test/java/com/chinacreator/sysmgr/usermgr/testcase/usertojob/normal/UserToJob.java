package com.chinacreator.sysmgr.usermgr.testcase.usertojob.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class UserToJob extends TestCase{

	@Test
	public void testUserToJob() throws Exception{
		//��ѯ���ø�λ���û�
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("˾˼");
		Common.waitFor(2, TestAll.driver);
		
		//��ѡ�û�
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//���ø�λ
		TestAll.driver.findElement(By.id("newField2")).click();
		
		//��ѡ����Ա��λ
		TestAll.driver.findElement(By.xpath("//div[@tree-id='jobTree']/ul/li/ul/li/a[@title='����Ա']/preceding-sibling::*[not(@title)]")).click();
		
		//����
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[1]")).click();
		
		try {
			assertEquals("���ø�λ�ɹ�", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//�ر�
		TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		
		//�����ѯ����
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
	}

}
