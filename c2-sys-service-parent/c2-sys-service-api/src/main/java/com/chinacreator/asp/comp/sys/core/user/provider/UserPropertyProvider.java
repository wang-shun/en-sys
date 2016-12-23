package com.chinacreator.asp.comp.sys.core.user.provider;

import java.util.Map;

import com.chinacreator.c2.sysmgr.User;

/**
 * 用户扩展属性提供接口，一个系统可以有多个属性提供器，
 * 
 * @author Vurt
 */
public interface UserPropertyProvider {
	/**
	 * 通过用户基础信息获取扩展属性，如果不同的提供器返回了相同的属性Key，那么系统只会保留其中一个(顺序不可预期)，请业务系统自己控制好不要有Key冲突
	 * 
	 * @param user
	 * @return 扩展属性
	 */
	public Map<String, Object> getUserProperties(User user);
}
