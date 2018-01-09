package com.chinacreator.asp.comp.sys.basic.org.spi;

/**
 * 自定义生成机构行政区码接口
 * 
 * @author 彭盛
 * 
 */
public interface GenerateOrgXzqmSpi {

	/**
	 * 获取机构行政区码（只能由数字、字母、下划线组成，字符长度不超过12）
	 * 
	 * @return 行政区码
	 */
	public String getOrgXzqm();
}
