package com.chinacreator.sysmgr.rolemgr.testcase.rolemgr.roleprivilege.normal;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class RoleSetPrivilege_predefine extends TestCase{

	@Test
	public void testRoleSetPrivilege_predefine() throws Exception{
		//��ѯҪ����Ȩ�޵Ľ�ɫ
		 TestAll.driver.findElement(By.id("newField")).clear();
		 TestAll.driver.findElement(By.id("newField")).sendKeys("�鳤��ɫ");
		 Common.waitFor(1, TestAll.driver);
		 		 
		 //ѡ��Ҫ����Ȩ�޵Ľ�ɫ
		 TestAll.driver.findElement(By.xpath("//td[@title='�鳤��ɫ']")).click();
		 
		 //����Ȩ��
		 TestAll.driver.findElement(By.id("newField13")).click();
		 
		 //չ��Ԥ����
		 TestAll.driver.findElement(By.xpath("//*[@id='newField2Wrapper']/div/div[1]/span[1]/i")).click();
		 
		 //չ����ɫ����
		 TestAll.driver.findElement(By.xpath("//a[@title='һ������']/preceding-sibling::*[(@title)]")).click();
		 
		 //��ѡԤ����Ľ�ɫ��
		 TestAll.driver.findElement(By.xpath("//a[@title='��Ʒ����']/preceding-sibling::*[not(@title)]")).click();
		 
		 //������������д��
		 TestAll.driver.findElement(By.xpath("//*[@id='newField2Wrapper']/div/input")).click();
		 
		 //����
		 TestAll.driver.findElement(By.id("newField3")).click();
		 
		 try {
		      assertEquals("����Ȩ�޳ɹ���", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		 
		 //�ر�
		 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[4]")).click();
		 
		 //�����ѯ����
		 TestAll.driver.findElement(By.id("newField")).clear();
	}

}
