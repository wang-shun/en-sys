package com.chinacreator.sysmgr.checkprivilege;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;
import com.chinacreator.sysmgr.utils.Common;

public class NoPrivilegeCheck extends TestCase{
	@Test
	public void testNoPrivilegeCheck() throws Exception{
		Common.waitFor(3, TestAll.driver);
		//menus下的所有菜单
		List<WebElement> menusnode = TestAll.driver.findElements(By.xpath("//ul[@c2-menu='menus']/descendant::*"));

		try{
			assertEquals(0, menusnode.size());
	    } catch (Error e) {
	      TestAll.verificationErrors.append(e.toString());
	    //打印节点信息
			for (int i=0; i<menusnode.size();i++){
				System.out.println(menusnode.get(i).getAttribute("id"));
			}
		}
		 
	}
			
}
