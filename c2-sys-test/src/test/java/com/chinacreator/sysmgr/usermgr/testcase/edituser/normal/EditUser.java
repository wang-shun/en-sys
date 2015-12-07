package com.chinacreator.sysmgr.usermgr.testcase.edituser.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;


public class EditUser extends TestCase{

	@Test
	public void testEditUser() throws Exception{
		//查询要编辑的用户
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();
		TestAll.driver.findElement(By.id("userRealname_Field")).sendKeys("张三");
		Common.waitFor(2, TestAll.driver);
				
		//勾选要编辑的用户
		TestAll.driver.findElement(By.xpath("//table[@id='userGroup']/tbody/tr[2]/td/input")).click();
		
		//编辑
		TestAll.driver.findElement(By.id("newField14")).click();
		
		//所属机构不允许修改，disabled属性为true则不允许修改
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("newField3")).getAttribute("disabled"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	
		//账号不允许修改
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("userName")).getAttribute("disabled"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//修改姓名
		TestAll.driver.findElement(By.id("userRealname")).clear();
		TestAll.driver.findElement(By.id("userRealname")).sendKeys("张三丰");
		
		//密码不允许修改
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("userPassword")).getAttribute("disabled"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//拼音修改
		TestAll.driver.findElement(By.id("userPinyin")).clear();
		TestAll.driver.findElement(By.id("userPinyin")).sendKeys("zhangsanfeng");
		
		//修改性别
		TestAll.driver.findElement(By.id("userSex")).sendKeys("未知");
		
		//生日删除
		TestAll.driver.findElement(By.xpath("//*[@id='userBirthdayWrapper']/div/div/span[1]/i")).click();
		
		//身份证修改
		TestAll.driver.findElement(By.id("userIdcard")).clear();
		TestAll.driver.findElement(By.id("userIdcard")).sendKeys("000000000000000000");
		
		//手机号码修改
		TestAll.driver.findElement(By.id("userMobiletel1")).clear();
		TestAll.driver.findElement(By.id("userMobiletel1")).sendKeys("16800000000");
		
		//登录次数不允许修改
		System.out.println(TestAll.driver.findElement(By.id("userLogincount")).getAttribute("disabled"));
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("userLogincount")).getAttribute("disabled"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//最后登录IP不允许修改
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("loginIp")).getAttribute("disabled"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//最后登录时间不允许修改
		try {
			assertEquals("true", TestAll.driver.findElement(By.id("lastloginDate")).getAttribute("readonly"));
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//保存
		TestAll.driver.findElement(By.id("newField")).click();
		try {
			assertEquals("编辑用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		
		//关闭
		TestAll.driver.findElement(By.id("newField1")).click();
	
		//清空查询条件
		TestAll.driver.findElement(By.id("userRealname_Field")).clear();  
		
		Common.waitFor(1, TestAll.driver);
	}

}
