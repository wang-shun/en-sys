package com.chinacreator.sysmgr.dicmgr.testcase.dictypemgr.editdictype.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class EditDicType extends TestCase{

	@Test
	public void testEditDicType() throws Exception{
		//选择要编辑的类型
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型']")).click();
	
	    //编辑
	    TestAll.driver.findElement(By.id("newField8")).click();
	    
	    //字典类型名称
	    TestAll.driver.findElement(By.id("dicttypeName")).clear();
	    TestAll.driver.findElement(By.id("dicttypeName")).sendKeys("测试类型修改后");
	    
	    //字典类型描述
	    TestAll.driver.findElement(By.id("dicttypeDesc")).clear();
	    TestAll.driver.findElement(By.id("dicttypeDesc")).sendKeys("测试类型修改后");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField2")).click();
	    
	    try {
		      assertEquals("编辑字典类型成功", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
		//关闭提示信息
		TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
	    //关闭
	  	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	  		
	  	Common.waitFor(2, TestAll.driver);
	  	
	  	//展开刷新按钮
  		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/button")).click();
  		
  		//点击刷新按钮
  		TestAll.driver.findElement(By.xpath("//*[@id='newGroup2']/ul/li[2]/a")).click();
	}

}
