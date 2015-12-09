package com.chinacreator.sysmgr.orgmgr.testcase.addorg.normal;

import static org.junit.Assert.*;
import junit.framework.*;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.login.testcase.login;
import com.chinacreator.sysmgr.orgmgr.bean.OrgBean;
import com.chinacreator.sysmgr.orgmgr.testcase.addorg.ReadOrgXlsUtils;
import com.chinacreator.sysmgr.utils.Common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Test;

public class AddOrgXls extends TestCase{
	Logger logger = LoggerFactory.getLogger(AddOrgXls.class);
	public void testAddOrgXls() throws Exception{
		logger.info("=========================开始新增机构============================");
		List<OrgBean> list = ReadOrgXlsUtils.getInstance().testData(ReadOrgXlsUtils.NORMALDATA);
		if(list!=null&&!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				OrgBean bean = list.get(i);
				// 等待 zTree 初始化完毕，Timeout 设置10秒
				  try {
			        	(new WebDriverWait(TestAll.driver, 10, 500)).until(new ExpectedCondition<Boolean>() {
			                public Boolean apply(WebDriver d) {
			                    WebElement element = (WebElement) ((JavascriptExecutor)TestAll.driver).executeScript("return $('#orgTree li').get(0);");
			                    return element != null;
			                }
			            });
			             
			        } catch(Exception e) {
			            e.printStackTrace();
			            logger.error("新增机构-机构树加载失败！");
			        }
				
		         
				// 选择父机构
				TestAll.driver.findElement(By.xpath(bean.getParentorg())).click();
				// 新增
				TestAll.driver.findElement(By.id("newField2")).click();

				// 输入信息
				TestAll.driver.findElement(By.id("orgNumber")).clear();
				TestAll.driver.findElement(By.id("orgNumber")).sendKeys(bean.getOrgNumber());
				TestAll.driver.findElement(By.id("orgName")).clear();
				TestAll.driver.findElement(By.id("orgName")).sendKeys(bean.getOrgName());
				TestAll.driver.findElement(By.id("orgShowName")).clear();
				TestAll.driver.findElement(By.id("orgShowName")).sendKeys(bean.getOrgShowName());
				TestAll.driver.findElement(By.id("orgXzqm")).clear();
				TestAll.driver.findElement(By.id("orgXzqm")).sendKeys(bean.getOrgXzqm());
				TestAll.driver.findElement(By.id("jp")).clear();
				TestAll.driver.findElement(By.id("jp")).sendKeys(bean.getJp());
				TestAll.driver.findElement(By.id("qp")).clear();
				TestAll.driver.findElement(By.id("qp")).sendKeys(bean.getQp());
				//下拉框
				Select orgLevel= new Select(TestAll.driver.findElement(By.id("orgLevel")));
				//验证下拉列表的数量
				try {
				      assertEquals(4, orgLevel.getOptions().size());
				    } catch (Error e) {
				      TestAll.verificationErrors.append(e.toString());
				      logger.error("新增机构-行政级别下拉框内容显示错误！");
				    }
				//选择下拉项
				orgLevel.selectByVisibleText("市州级");
						
				// new Select(driver.findElement(By.id("orgLevel"))).selectByVisibleText("市州级");
//				WebElement orgLevel = TestAll.driver.findElement(By.id("orgLevel"));
//				orgLevel.sendKeys(bean.getRgLevel());
				TestAll.driver.findElement(By.id("orgDesc")).clear();
				TestAll.driver.findElement(By.id("orgDesc")).sendKeys(bean.getOrgDesc());
				
				// 保存
				TestAll.driver.findElement(By.id("newField1")).click();
	
				//判断alert为正确弹框还是错误弹框
				WebElement webElement = TestAll.driver.findElement(By.xpath("//ul[contains(@class,'messenger')]/li[1]/div"));
				if (webElement.getAttribute("class").contains("message-success"))
					logger.info("正常流：机构 '"+bean.getOrgShowName()+"' 添加成功@@");
				else if (webElement.getAttribute("class").contains("message-error"))
					{
					logger.error("正常流：机构 '"+bean.getOrgShowName()+"' 添加失败!!!");
					Common.TakePic();
					}
				
//				for (int second = 0;; second++) {
//			    	if (second >= 60) fail("timeout");
//			    	try { if ("新增机构成功！".equals(TestAll.driver.findElement(By.xpath("//div/div")).getText()))
//			    		{logger.info("正常流：机构 '"+bean.getOrgShowName()+"' 添加成功@@");
//			    		break;} 
//			    	} catch (Exception e) {
//			    		logger.info("正常流：机构 '"+bean.getOrgShowName()+"' 添加失败!!!");
//			    	}
//			    }
				//关闭alert弹框
				TestAll.driver.findElement(By.xpath("//*[@id='ng-app']/body/ul/li[1]/div/button")).click();
				
				 //关闭
				 TestAll.driver.findElement(By.xpath("//div[@class='modal-footer']/button[3]")).click();
	
				 Common.waitFor(1, TestAll.driver);
				 
			}
			//展开刷新按钮
			 TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/button")).click();

			 //点击刷新按钮
			 TestAll.driver.findElement(By.xpath("//*[@id='newGroup4']/ul/li[4]/a")).click();
			 Common.waitFor(2, TestAll.driver);
		}
	}
}
			

