package com.chinacreator.c2.sys.sdk.service.query;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sysmgr.User;

/**
 * 机构用户树型数据查询服务
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
 * @see UserService
 * @see OrganizationService
 * @see RoleService
 * @author Vurt
 * @since 5.0
 */
public interface OrgUserTreeService {
    /**
     * 查询指定机构下所有的机构和用户数据，用户所属的机构的信息在 {@link User#get("orgId")}的属性中,
     * {@link User#getExtFields()}返回的Map Key如下:orgId-机构ID,sn-机构编号,orgShowName-机构显示名称
     * 
     * @param orgId 机构id，null表示查所有
     * @return 机构用户型数据
     * @see Organization
     * @see User
     */
    public List<Object> getOrgAndUser(String orgId);
}
