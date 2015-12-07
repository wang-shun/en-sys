package com.chinacreator.sysmgr.usermgr.testcase.adduser.normal;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.usermgr.bean.UserBean;
import com.chinacreator.sysmgr.usermgr.testcase.adduser.ReadUserXlsUtils;
import com.chinacreator.sysmgr.utils.Common;



public class AddUserXls extends TestCase{

	@Test
	public void testAddUserXls() throws Exception{
		List<UserBean> list = ReadUserXlsUtils.getInstance().testData(ReadUserXlsUtils.NORMALDATA);
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
												
				try {
					assertEquals("新增用户成功！", TestAll.driver.findElement(By.xpath("//div/div")).getText());
				    } catch (Error e) {
				      TestAll.verificationErrors.append(e.toString());
				      Common.TakePic();
				    }
				 
				//关闭
				TestAll.driver.findElement(By.id("newField1")).click();
				Common.waitFor(1, TestAll.driver);
				}
			
				//刷新
				TestAll.driver.findElement(By.id("newField20")).click();
			}

	}
}
