package com.chinacreator.asp.comp.sys.basic.org.spi;

/**
 * 自定义生成机构编码接口
 * 
 * @author 彭盛
 * 
 */
public interface GenerateOrgNumberSpi {

	/**
	 * 获取机构编码（只能由数字、字母、下划线组成,字符长度不超过100）
	 * 
	 * @return 机构编码
	 */
	public String getOrgNumber();
}
