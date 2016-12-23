package com.chinacreator.c2.sys.sdk.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.provider.UserPropertyProvider;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.User;

public class UserPropertyInjector {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserPropertyInjector.class);
	
	/**
	 * 系统管理的User对象转为平台User对象，目前只使用其中的三个属性；并注入业务系统自定义的用户属性
	 * 
	 * @param userDTO 系统管理库中的对象
	 * @return 完整的user对象
	 * @see UserPropertyProvider
	 */
	public static User inject(UserDTO userDTO){
		if(userDTO == null){
			return null;
		}
		User user = populateUserDto(userDTO);

		Map<String, UserPropertyProvider> providers = ApplicationContextManager.getContext().getBeansOfType(UserPropertyProvider.class);
		if(providers != null && !providers.isEmpty()){
			for(UserPropertyProvider provider : providers.values()){
				try {
					populateProperties(user,provider.getUserProperties(user));
				} catch (Exception e) {
					LOGGER.warn("注入用户属性时发生错误，忽略提供器["+provider.getClass().getName()+"]，错误原因：",e);
				}
			}
		}
		return user;
	}
	
	/**
	 * 系统管理的User对象转为平台User对象，目前只使用其中的三个属性
	 * 
	 * @param dto
	 * @return
	 */
	private static User populateUserDto(UserDTO dto){
		User user = new User();
		user.setId(dto.getUserId());
		user.setName(dto.getUserName());
		user.setRealname(dto.getUserRealname());
		return user;
	}
	
	/**
	 * 将提供器返回的数据填充到用户对象中
	 * @param user 用户对象
	 * @param properties 新的属性
	 */
	private static void populateProperties(User user , Map<String, Object> properties){
		if(properties == null || properties.isEmpty()){
			return;
		}
		for(Map.Entry<String, Object> entry : properties.entrySet()){
			String key = entry.getKey();
			if(isValidProperty(key)){
				if(user.containsKey(key)){
					LOGGER.warn("用户对象中已存在名为["+key+"]的属性，忽略");
				}else{
					user.put(key, entry.getValue());
				}
			}
		}
	}
	
	/**
	 * 检查扩展属性key是否有效，目前只不允许修改id和name属性
	 * @param propertyKey
	 * @return
	 */
	private static boolean isValidProperty(String propertyKey) {
		if (StringUtils.equals(propertyKey, User.KEY_ID)
				|| StringUtils.equals(propertyKey, User.KEY_USER_NAME)) {
			return false;
		}
		return true;
	}
}
