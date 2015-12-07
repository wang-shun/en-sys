package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.adddicdata.exception;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.chinacreator.sysmgr.TestAll;

public class AddDicData_DicDataNameIsNull extends TestCase{

	@Test
	public void testAddDicData_DicDataNameIsNull() throws Exception{
		//选择字典类型
	    TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后']")).click();
	    //新增
	    TestAll.driver.findElement(By.id("newField")).click();
	    
	    //字典真实值
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    
	    //字典显示值
	    TestAll.driver.findElement(By.id("dictdataValue")).clear();
	    TestAll.driver.findElement(By.id("dictdataValue")).sendKeys("一季度");
	    
	    //是否默认值
	    new Select(TestAll.driver.findElement(By.id("dictdataIsdefault"))).selectByVisibleText("是");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	    
	    try {
		      assertEquals("保存失败", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	    //关闭提示信息
	  	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	    
	  	//关闭
	  	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	  		
		
	}

}
