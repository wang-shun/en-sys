package com.chinacreator.sysmgr.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.chinacreator.sysmgr.TestAll;



public class Common {

	/***
	 * 数据文件的存放位置
	 * @return
	 */
	public static String getResdataPath(){
		File fi = new File("res");
		if(!fi.exists()){
			fi.mkdirs();
		}
		return fi.getAbsolutePath();
	}
	public static void main(String[] args){
		File fi = new File("res");
		System.out.println(fi.getAbsolutePath());
	}

	/***
	 * 将小数后面多余的0去掉
	 * @param num
	 * @return
	 */
	public static String subDoubleZero(String num){
		if(num.indexOf(".")>0){
			num = num.replaceAll("0+?$", "");
			num = num.replaceAll("[.]$", "");
		}
		return num;
	}
	/***
	 * 是否是空
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s){
		if(s==null|| "".equals(s.trim())){
			return true;
		}
		return false;
	}
	
	/***
	 * 判断是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNumric(String s){
		if(isNull(s)){
			return false;
		}
		int index = s.indexOf(".");
		if(index>=0){
			s = s.replace(".", "");
		}
		int len = s.length();
		for(int i=0;i<len;i++){
			char ch = s.charAt(i);
			if(!Character.isDigit(ch)){
				return false;
			};
		}
		return true;
	}
	
	public static void waitFor(int second, WebDriver driver) {
		// 等待 5 秒
		try {
			(new WebDriverWait(driver, second, 1000))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return false;
						}
					});

		} catch (Exception e) {
		}
	}
	
	
	/**
	 * 截图
	 */
	public static void TakePic() {

		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmsssss");
			String ds = f.format(new Date());
			File srcFile = ((TakesScreenshot) TestAll.driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File("c:\\c2log\\sys-role-421\\Error\\" + ds
					+ ".png"));
			System.out.println("save snapshot path is:c:\\c2log\\sys-role-421\\Error");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * 删除目录
	 * @return
	 */
	public static void DeleteDirectory() {
		FileHandler.delete(new File("c:\\c2log\\sys-role-421"));
	}
	
	
	/**
	 * 移开焦点
	 */
	public static void onBlur() {
		WebElement el = TestAll.driver.findElement(By.linkText("首页标题"));

		Actions m = new Actions(TestAll.driver);
		m.moveToElement(el).build().perform();
	}
	
	
	/**
	 * 判断元素是否存在
	 */
	public static boolean isElementExist (By Locator )
	{
		try
		{
			TestAll.driver.findElement( Locator );
			return true;
		}
		catch(org.openqa.selenium.NoSuchElementException ex)
		{
			return false;
		}
	}


}
