package com.chinacreator.c2.sys.sdk.service.management;
//TODO 系统管理sdk暂不提供任何管理接口
//package com.chinacreator.c2.sys.sdk.service;
//
//import java.util.List;
//import java.util.Set;
//
//import com.chinacreator.c2.sys.sdk.bean.Role;
//import com.chinacreator.c2.web.exception.ResourceNotFoundException;
//import com.chinacreator.platform.mvc.perm.Resource;
//
//public interface AuthorizationService {
//	/**
//	 * 获取系统内部所有的权限资源信息
//	 */
//	public List<Resource> getResources();
//
//	/**
//	 * 获取权限资源详情
//	 * @param id 资源id
//	 * @exception ResourceNotFoundException 权限资源不存在
//	 */
//	public Resource getResource(String id) throws ResourceNotFoundException;
//	
//	/**
//	 * 获取有权访问资源的角色
//	 * @param resource 资源id
//	 * @return 有权访问该资源的角色列表 
//	 * @exception ResourceNotFoundException 权限资源不存在
//	 */
//	public List<Role> getAuthorizedRoles(String resource) throws ResourceNotFoundException;
//	
//	/**
//	 * 授予角色资源权限
//	 * 
//	 * @param role 角色
//	 * @param resources 资源id集合
//	 * @exception ResourceNotFoundException 权限资源不存在
//	 */
//	public void grant(String role, Set<String> resources) throws ResourceNotFoundException;
//	
//	/**
//	 * 取消已授予的权限
//	 * 
//	 * @param role 角色id
//	 * @param resources 资源id
//	 */
//	public void cancelGranted(String role, Set<String> resources);
//}
