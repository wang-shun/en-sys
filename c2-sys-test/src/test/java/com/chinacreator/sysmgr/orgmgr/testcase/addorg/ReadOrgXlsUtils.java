package com.chinacreator.sysmgr.orgmgr.testcase.addorg;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinacreator.sysmgr.orgmgr.bean.*;
import com.chinacreator.sysmgr.utils.Common;

public class ReadOrgXlsUtils {

	private final String xlspath = Common.getResdataPath()+"/orgdata.xls";
	private static ReadOrgXlsUtils instance;
	public static final int NORMALDATA = 0;       	//正常流数据从第一个sheet中读取
	public static final int EXCEPTIONDATA = 1;		//异常流数据从第二个sheet中读取
	
	public static ReadOrgXlsUtils getInstance(){
		if(instance==null){
			instance = new ReadOrgXlsUtils();
		}
		return instance;
	}

	public static void main(String[] args) {
		try {
			getInstance().testData(ReadOrgXlsUtils.NORMALDATA);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "resource", "unused" })
	public List<OrgBean> testData(int type) throws Exception {
		InputStream is = new FileInputStream(xlspath);
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = null;
		if(type == NORMALDATA){
			sheet = workbook.getSheetAt(0);
		}else if(type == EXCEPTIONDATA){
			sheet = workbook.getSheetAt(1);
		}

		List<OrgBean> list = new ArrayList<OrgBean>();
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		Map<Integer, String> temp = new HashMap<Integer, String>();
		readTitle(sheet, temp);
		;

		BeanInfo beanInfo = Introspector.getBeanInfo(OrgBean.class);
		List<String[]> records = new ArrayList<String[]>();
		for (int i = 1; i <= rowNum; i++) {
			// 当前行
			HSSFRow row = sheet.getRow(i);
			int colNum = row.getLastCellNum();
			// String[] data = new String[colNum];
			OrgBean orgbean = new OrgBean();
			for (int j = 0; j < colNum; j++) {
				HSSFCell cell = row.getCell(j);
				String value = "";
				if(cell!=null){
					value = getCellValue(cell, cell.getCellType());
				}
				String title = temp.get(j);
				Method th = getMethod(beanInfo, title);
				th.invoke(orgbean, value);
//				System.out.println(orgbean.getOrgNumber());
			}
			list.add(orgbean);
		}
		return list;
	}

	private Method getMethod(BeanInfo beaninfo, String title) {
		Method me = null;
		PropertyDescriptor[] ths = beaninfo.getPropertyDescriptors();
		if (ths != null) {
			for (PropertyDescriptor th : ths) {
				String name = th.getName();
				if (title.equalsIgnoreCase(name)) {
					me = th.getWriteMethod();
					break;
				}
			}
		}
		return me;
	}

	private void readTitle(HSSFSheet sheet, Map<Integer, String> temp)
			throws IOException {
		HSSFRow row = sheet.getRow(0);
		int colNum = row.getLastCellNum();
		for (int i = 0; i < colNum; i++) {
			HSSFCell cell = row.getCell(i);
			String title = getCellValue(cell, cell.getCellType());
			temp.put(i, title);
		}
	}

	private String getCellValue(HSSFCell cell, int type) {
		String value = "";
		switch (type) {
		case HSSFCell.CELL_TYPE_STRING:
			try {
				value = cell.getStringCellValue();
			} catch (Exception e1) {
			}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			double d = 0d;;
			try {
				d = cell.getNumericCellValue();
			} catch (Exception e) {
				break;
			}
			value = String.valueOf(d);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		default:
			break;
		}
		return value;
	}
}
