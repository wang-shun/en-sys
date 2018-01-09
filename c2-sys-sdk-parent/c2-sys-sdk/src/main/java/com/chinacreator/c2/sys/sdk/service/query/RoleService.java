package com.chinacreator.c2.sys.sdk.service.query;

import com.chinacreator.c2.sys.sdk.bean.Role;


/**
 * 角色信息查询服务
 * <p>集成版和分布式版本系统管理的统一接口，其中查询类接口包括：
 * <ul>
 * 	<li>{@link UserService}</li>
 *  <li>{@link OrganizationService}</li>
 *  <li>{@link RoleService}</li>
 *  <li>{@link OrgUserTreeService}</li>
 * </ul>
 * 管理类接口包括：
 * <ul>
 * 	<li>{@link UserManageService}(暂未实现)</li>
 * </ul>
 *
 * @see OrganizationService
 * @see OrgUserTreeService
 * @see UserService
 * @author Vurt
 * @since 5.0
 */
public interface RoleService {
    /**
     * 查询角色详情
     * @param id 角色id
     * @return 角色详情
     */
    public Role get(String id);
    
    /**
     * 通过角色名称查询角色详情
     * @param roleName 角色显示名称
     * @return 角色详情
     */
    public Role getByName(String roleName);
}
