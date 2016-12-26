package com.chinacreator.c2.sys.sdk.service.query;

import com.chinacreator.c2.sys.sdk.bean.Role;


/**
 * 角色信息查询服务
 * <p>集成版和分布式版本系统管理的统一接口，其中查询类接口包括：
 * <ul>
 * 	<li>{@link UserService}</li>
 *  <li>{@link OrgnizationService}</li>
 *  <li>{@link RoleService}</li>
 *  <li>{@link OrgUserTreeService}</li>
 * </ul>
 * 管理类接口包括：
 * <ul>
 * 	<li>{@link UserManageService}(暂未实现)</li>
 * </ul>
 *
 * @see OrgnizationService
 * @see OrgUserTreeService
 * @see UserService
 * @author Vurt
 * @since 5.0
 */
public interface RoleService {
    /**
     * 查询角色列表
     * @param role 查询条件对象，null表示查所有的
     * @return 角色列表
     */
    public Role get(String id);
    
    /**
     * 查询角色列表
     * @param role 查询条件对象，null表示查所有的
     * @return 角色列表
     */
    public Role getByName(String roleName);
}
