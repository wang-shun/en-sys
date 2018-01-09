package com.chinacreator.c2.sys.sdk.util;

import com.chinacreator.c2.dao.mybatis.enhance.Pageable;

/**
 * 分页工具类
 */
public class PageableUtils {
	
	/**
	 * 获取分页对象
	 * @param page：
	 * 		  页码
	 * @param 
	 * 		每页多少条数据
	 * @return 分页后的数据
	 */
	public static Pageable getPageable(int page,int limit){
		if(page != 0 && limit != 0 || page == 0 && limit != 0){
			return new Pageable(page, limit) ;
		}
		return null ;
	}

}
