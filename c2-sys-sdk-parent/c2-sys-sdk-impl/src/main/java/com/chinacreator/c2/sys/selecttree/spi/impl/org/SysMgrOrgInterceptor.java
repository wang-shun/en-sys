package com.chinacreator.c2.sys.selecttree.spi.impl.org;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

/**
 * 系统管理机构拦截类
 * 
 * @author 彭盛
 * 
 */
@Aspect
public class SysMgrOrgInterceptor {

	@Autowired
	private OrgUserTreeCache cache;

	/**
	 * 设置机构排序后拦截
	 * 
	 * @param joinPoint
	 */
	@AfterReturning(value = "execution(* com.chinacreator.asp.comp.sys.basic.org.service.OrgService.setOrder(..))")
	public void afterReturningBySetOrder(JoinPoint joinPoint) {
		if (null != joinPoint && null != joinPoint.getArgs() && joinPoint.getArgs().length > 0) {
			Object object = joinPoint.getArgs()[0];
			if (null != object) {				
				cache.setOrderByOrg((List<OrgDTO>) object);
			}
		}
	}
}
