package com.chinacreator.asp.comp.sys.core.user.provider;

import java.util.Map;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sysmgr.User;

/**
 * 用户扩展属性提供接口，一个系统可以有多个属性提供器。<p>
 * 默认只会将系统管理的用户表中的id、username和realname属性注入到{@link User}对象，如果需要更多属性，可以使用这个接口来控制注入
 * 
 * @author Vurt
 */
public interface UserPropertyProvider {
	/**
	 * 通过用户基础信息获取扩展属性
	 * <p>
	 * 如果不同的提供器返回了相同的属性Key，那么系统只会保留其中一个(顺序不可预期)，请业务系统自己控制好不要有Key冲突
	 * 
	 * @param user 注入的目标user对象，内部会有用户的基本属性(id，name和realname)，和在此之前由其他Provider注入的属性；要注入的属性请通过返回值指定，不建议直接修改这个对象 
	 * @param dto 数据库中查询到的系统管理用户对象
	 * @return 扩展属性
	 */
	public Map<String, Object> getUserProperties(User user, UserDTO dto);
}
