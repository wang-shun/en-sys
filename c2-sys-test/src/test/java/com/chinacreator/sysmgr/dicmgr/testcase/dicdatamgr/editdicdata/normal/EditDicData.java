package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.editdicdata.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;


public class EditDicData extends TestCase{

	@Test
	public void testEditDicData() throws Exception{
		//选择字典类型
	    TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后']")).click();
	    
	    //选择要编辑的字典数据
	    TestAll.driver.findElement(By.xpath("//tbody/tr[2]/td/input")).click();
	    
	    //编辑
	    TestAll.driver.findElement(By.id("newField4")).click();
	    
	    TestAll.driver.findElement(By.id("dictdataName")).clear();
	    TestAll.driver.findElement(By.id("dictdataName")).sendKeys("2");
	    
	    //保存
	    TestAll.driver.findElement(By.id("newField1")).click();
	        
	    try {
		      assertEquals("编辑字典数据成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
		    } catch (Error e) {
		      TestAll.verificationErrors.append(e.toString());
		    }
	    
	    //关闭提示信息
	  	TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
	  	
	    //关闭
	  	TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	  	Common.waitFor(1, TestAll.driver);
	  		
	}

}