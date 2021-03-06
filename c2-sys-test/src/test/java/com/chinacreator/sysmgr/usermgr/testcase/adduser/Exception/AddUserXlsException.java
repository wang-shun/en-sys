package com.chinacreator.sysmgr.usermgr.testcase.adduser.Exception;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.usermgr.bean.*;
import com.chinacreator.sysmgr.usermgr.testcase.adduser.*;
import com.chinacreator.sysmgr.usermgr.testcase.adduser.normal.AddUserXls;
import com.chinacreator.sysmgr.utils.Common;

public class AddUserXlsException extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddUserXlsException.class);
	@Test
	public void testAddUserXlsException() throws Exception{
		List<UserBean> list = ReadUserXlsUtils.getInstance().testData(ReadUserXlsUtils.EXCEPTIONDATA);
		if(list!=null&&!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				UserBean bean = list.get(i);
				//新增
				TestAll.driver.findElement(By.id("newField13")).click();
				
				//所属机构
				TestAll.driver.findElement(By.xpath("//*[@id='orgId_FieldWrapper']/div/div[1]/span[1]/i")).click();
				TestAll.driver.findElement(By.xpath(bean.getOrgName())).click();
												
				//账号
				TestAll.driver.findElement(By.id("userName")).clear();
				TestAll.driver.findElement(By.id("userName")).sendKeys(bean.getUserName());
				
				//姓名
			    TestAll.driver.findElement(By.id("userRealname")).clear();
			    TestAll.driver.findElement(By.id("userRealname")).sendKeys(bean.getUserRealname());
			    
			    //密码
			    TestAll.driver.findElement(By.id("userPassword")).clear();
			    TestAll.driver.findElement(By.id("userPassword")).sendKeys(bean.getUserPassword());
			    
			    //拼音
			    TestAll.driver.findElement(By.id("userPinyin")).clear();
			    TestAll.driver.findElement(By.id("userPinyin")).sendKeys(bean.getUserPinyin());
			    
			    //生日选择今日
			    TestAll.driver.findElement(By.id("userBirthday")).click();
			    TestAll.driver.findElement(By.xpath(" //td[@class='today day']")).click();;
			 
			    TestAll.driver.findElement(By.id("userSex")).sendKeys(bean.getUserSex());
			    TestAll.driver.findElement(By.id("userIdcard")).clear();
			    TestAll.driver.findElement(By.id("userIdcard")).sendKeys(bean.getUserIdcard());
			    TestAll.driver.findElement(By.id("userMobiletel1")).clear();
			    TestAll.driver.findElement(By.id("userMobiletel1")).sendKeys(bean.getUserMobiletel1());
			    TestAll.driver.findElement(By.id("userOicq")).clear();
			    TestAll.driver.findElement(By.id("userOicq")).sendKeys(bean.getUserOicq());
			    TestAll.driver.findElement(By.id("userEmail")).clear();
			    TestAll.driver.findElement(By.id("userEmail")).sendKeys(bean.getUserEmail());
			    TestAll.driver.findElement(By.id("userAddress")).clear();
			    TestAll.driver.findElement(By.id("userAddress")).sendKeys(bean.getUserAddress());
			    TestAll.driver.findElement(By.id("userPostalcode")).clear();
				TestAll.driver.findElement(By.id("userPostalcode")).sendKeys(bean.getUserPostalcode());
				TestAll.driver.findElement(By.id("userHometel")).clear();
				TestAll.driver.findElement(By.id("userHometel")).sendKeys(bean.getUserHometel());
				TestAll.driver.findElement(By.id("userWorktel")).clear();
				TestAll.driver.findElement(By.id("userWorktel")).sendKeys(bean.getUserWorktel());
				TestAll.driver.findElement(By.id("userFax")).clear();
				TestAll.driver.findElement(By.id("userFax")).sendKeys(bean.getUserFax());
				TestAll.driver.findElement(By.id("userWorkaddress")).clear();
				TestAll.driver.findElement(By.id("userWorkaddress")).sendKeys(bean.getUserWorkaddress());
				TestAll.driver.findElement(By.id("worklength")).clear();
				TestAll.driver.findElement(By.id("worklength")).sendKeys(bean.getWorklength());
				//政治面貌
				TestAll.driver.findElement(By.xpath("//div[@id='newField17Wrapper']/div/select")).sendKeys(bean.getPolitics());
				//开通时间
				TestAll.driver.findElement(By.id("dredgeTime")).sendKeys("2014-10-10");
				Actions action = new Actions(TestAll.driver); 
				action.sendKeys(Keys.TAB);
				//过期时间
				TestAll.driver.findElement(By.id("pastTime")).sendKeys("2015-12-31");
				action.sendKeys(Keys.TAB);
				
				// 保存
				TestAll.driver.findElement(By.id("newField")).click();
												
//				try {
//					assertEquals("保存失败！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
//				    } catch (Error e) {
//				      TestAll.verificationErrors.append(e.toString());
//				    }
				 
				//判断alert为正确弹框还是错误弹框
				WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
				if (webElement.getAttribute("class").contains("message-error"))
					logger.info("异常流：用户 '"+ bean.getUserWorkaddress()+"' 操作成功@@");
				else if (webElement.getAttribute("class").contains("message-success"))
					{
					logger.error("异常流：用户 '"+ bean.getUserWorkaddress()+"' 操作失败!!!");
					Common.TakePic();
					}
				
				//关闭alert弹框
				TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
				
				//关闭
				TestAll.driver.findElement(By.id("newField1")).click();
				Common.waitFor(3, TestAll.driver);
			}
		}
	}

}
