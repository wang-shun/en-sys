package com.chinacreator.asp.comp.sys.common.spiutil;

/**
 * 通用排序spi接口
 * 
 * @author 彭盛
 * 
 */
public interface CommonSortSpi {

	/**
	 * 调用优先级(数值越大，优先级越高)
	 * 
	 * @return 优先级
	 */
	public int getPriority();
}
