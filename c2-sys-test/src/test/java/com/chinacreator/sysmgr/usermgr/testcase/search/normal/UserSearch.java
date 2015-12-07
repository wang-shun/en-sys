package com.chinacreator.sysmgr.usermgr.testcase.search.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;

public class UserSearch extends TestCase{

	@Test
	public void testUserSearch() throws Exception{
		//输入查询条件-所属机构
		TestAll.driver.findElement(By.xpath("//*[@id='org_FieldWrapper']/div/div[1]/span[1]/i")).click();
		TestAll.driver.findElement(By.xpath("//div[@id='org_Field_treeWapper']/ul/li/ul/li/a[@title='长沙市']")).click();
		try {
		   assertEquals("(主)长沙市", TestAll.driver.findElement(By.xpath("//tr[2]/td[6]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		
		//清空查询条件
		TestAll.driver.findElement(By.xpath("//*[@id='org_FieldWrapper']/div/div[1]/span[2]")).click();
		
		
		//输入查询条件-账号
		TestAll.driver.findElement(By.id("userName_Field")).clear();
		TestAll.driver.findElement(By.id("userName_Field")).sendKeys("lilei");
		try {
		   assertEquals("(李雷", TestAll.driver.findElement(By.xpath("//tr[2]/td[3]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		//清空查询条件
		TestAll.driver.findElement(By.id("userName_Field")).clear();
		
		
		//输入查询条件-姓名
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("李雷");
		try {
		   assertEquals("(lilei", TestAll.driver.findElement(By.xpath("//tr[2]/td[2]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		//清空查询条件
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		
		
		//输入查询条件-性别
		TestAll.driver.findElement(By.id("userSex_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='userSex_Field']/option[3]")).click();
		try {
		   assertEquals("男", TestAll.driver.findElement(By.xpath("//tr[2]/td[4]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		//清空查询条件
		TestAll.driver.findElement(By.id("userSex_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='userSex_Field']/option[1]")).click();
		
		
		//输入查询条件-状态
		TestAll.driver.findElement(By.id("userIsvalid_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='userIsvalid_Field']/option[4]")).click();
		try {
		   assertEquals("开通", TestAll.driver.findElement(By.xpath("//tr[2]/td[5]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		//清空查询条件
		TestAll.driver.findElement(By.id("userIsvalid_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='userIsvalid_Field']/option[1]")).click();
		
		
		
	}

}
