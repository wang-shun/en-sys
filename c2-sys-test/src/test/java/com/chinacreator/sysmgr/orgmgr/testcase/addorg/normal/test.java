package com.chinacreator.sysmgr.orgmgr.testcase.addorg.normal;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.chinacreator.sysmgr.TestAll;

public class test extends TestCase{

	@Test
	public void test() throws Exception{
		//ztree���ڵ��µ����нڵ�
		List<WebElement> treenode = TestAll.driver.findElements(By.xpath("//ul[@id='orgTree']/li/ul/li/a"));
		//��ӡ�ڵ�����
		for (int i = 0; i<treenode.size();i++){
			System.out.println(treenode.get(i).getAttribute("title"));
		}
		for (WebElement node : treenode){
			if (node.getAttribute("title").equals("��ɳ")){
				node.click();
			}
			
		}
			
	}

}
