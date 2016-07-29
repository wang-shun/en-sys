package com.chinacreator.c2.sys.selecttree.spi.impl.user;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 系统管理用户拦截类
 * 
 * @author 彭盛
 * 
 */
@Aspect
public class SysMgrUserInterceptor {

	@Autowired
	private OrgUserTreeCache cache;

	/**
	 * 设置机构下用户的排序号后拦截
	 * 
	 * @param joinPoint
	 */
	@AfterReturning(value = "execution(* com.chinacreator.asp.comp.sys.basic.user.service.UserService.setOrderInOrg(..))")
	public void afterReturningBySetOrder(JoinPoint joinPoint) {
		if (null != joinPoint && null != joinPoint.getArgs() && joinPoint.getArgs().length > 0) {
			Object object = joinPoint.getArgs()[0];
			if (null != object) {
				cache.setOrderByUser((List<Map<String, Object>>) object);
			}
		}
	}
}
