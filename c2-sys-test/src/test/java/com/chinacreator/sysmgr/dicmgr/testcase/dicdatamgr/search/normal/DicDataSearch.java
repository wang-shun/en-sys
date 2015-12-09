package com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.search.normal;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.dicmgr.testcase.dicdatamgr.order.normal.DicDataOrder;
import com.chinacreator.sysmgr.utils.Common;

public class DicDataSearch extends TestCase{
	Logger logger = LoggerFactory.getLogger(DicDataSearch.class);
	@Test
	public void testDicDataSearch() throws Exception{
		//选择字典类型树
		TestAll.driver.findElement(By.xpath("//a[@title='测试类型修改后se']")).click();
		
		//按照字典真实值查询
		TestAll.driver.findElement(By.id("dictdataName_Field")).clear();
		TestAll.driver.findElement(By.id("dictdataName_Field")).sendKeys("1");
		Common.waitFor(1, TestAll.driver);
		
		try {
		     assertEquals("一季度", TestAll.driver.findElement(By.xpath("//tr[2]/td[3]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		
		//删除查询条件
		TestAll.driver.findElement(By.id("dictdataName_Field")).clear();
		
		//按照字典显示值查询
		TestAll.driver.findElement(By.id("dictdataValue_Field")).clear();
		TestAll.driver.findElement(By.id("dictdataValue_Field")).sendKeys("二季度");
		Common.waitFor(1, TestAll.driver);
		try {
		     assertEquals("二季度", TestAll.driver.findElement(By.xpath("//tr[2]/td[3]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		//删除查询条件
		TestAll.driver.findElement(By.id("dictdataValue_Field")).clear();
		
		
		//按照是否默认值查询
		TestAll.driver.findElement(By.id("dictdataIsdefault_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='dictdataIsdefault_Field']/option[2]")).click();
		Common.waitFor(1, TestAll.driver);
		try {
		   assertEquals("一季度", TestAll.driver.findElement(By.xpath("//tr[2]/td[3]")).getText());
		   } catch (Error e) {
		     TestAll.verificationErrors.append(e.toString());
		   }
		
		//查询条件选择为空
		TestAll.driver.findElement(By.id("dictdataIsdefault_Field")).click();
		TestAll.driver.findElement(By.xpath("//*[@id='dictdataIsdefault_Field']/option[1]")).click();
		Common.waitFor(2, TestAll.driver);
		logger.info("字典数据查询：操作成功！");
	}

}
