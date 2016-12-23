package com.chinacreator.c2.sysmgr;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.AccessControlMessages;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.asp.comp.sys.core.security.shiro.token.SysMgrUsernamePasswordToken;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.provider.UserPropertyProvider;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sysmgr.exception.AuthenticationException;

/**
 * 访问控制服务接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrAuthenticationProviderImpl implements AuthenticationProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(SysMgrAuthenticationProviderImpl.class);

	@Override
	public boolean login(AuthenticationToken token) throws AuthenticationException {
		boolean isLogin = false;
		try {
			isLogin = getAccessControlService().login(token);
		} catch (SysException e) {
			throw new AuthenticationException(e);
		} catch (Exception e) {
			throw new AuthenticationException(e);
		}
		return isLogin;
	}

	@Override
	public User login(String username, String password) throws AuthenticationException {
		if (null != username && null != password && !"".equals(username.trim()) && !"".equals(password.trim())) {
			AuthenticationToken token = new SysMgrUsernamePasswordToken();
			((SysMgrUsernamePasswordToken) token).setUsername(username);
			((SysMgrUsernamePasswordToken) token).setPassword(password.toCharArray());
			if (login(token)) {
				return getSubject();
			}
		} else {
			throw new SysException(AccessControlMessages.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
		}
		return null;
	}

	@Override
	public String loginToRedirectUri(String username, String password) throws AuthenticationException {
		if (null != username && null != password && !"".equals(username.trim()) && !"".equals(password.trim())) {
			AuthenticationToken token = new SysMgrUsernamePasswordToken();
			((SysMgrUsernamePasswordToken) token).setUsername(username);
			((SysMgrUsernamePasswordToken) token).setPassword(password.toCharArray());
			if (login(token)) {
				LoginSuccessToRedirectUri loginSuccessToRedirectUri = null;
				try {
					loginSuccessToRedirectUri = (LoginSuccessToRedirectUri) ApplicationContextManager.getContext().
							getBean("LoginSuccessToRedirectUri");
					if (null != loginSuccessToRedirectUri) {
						return loginSuccessToRedirectUri.getRedirectUri();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new SysException(AccessControlMessages.getString("ACCESSCONTROL.USERNAME_ORPASSWORD_NULL"));
		}
		return null;
	}

	@Override
	public String logout() {
		return getAccessControlService().logout();
	}

	@Override
	public boolean isAuthenticated() {
		return getAccessControlService().isAuthenticated();
	}

	@Override
	public User getSubject() {
		AccessControlService accessControlService = getAccessControlService();
		if (accessControlService.isAuthenticated()) {
			User user = populateUserDto(accessControlService.getUser());

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
		return null;
	}
	
	/**
	 * 系统管理的User对象转为平台User对象，目前只使用其中的三个属性
	 * 
	 * @param dto
	 * @return
	 */
	private User populateUserDto(UserDTO dto){
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
	private void populateProperties(User user , Map<String, Object> properties){
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
	private boolean isValidProperty(String propertyKey) {
		if (StringUtils.equals(propertyKey, User.KEYS.ID)
				|| StringUtils.equals(propertyKey, User.KEYS.USER_NAME)) {
			return false;
		}
		return true;
	}

	private AccessControlService getAccessControlService() {
		return ApplicationContextManager.getContext().getBean(AccessControlService.class);
	}
}
