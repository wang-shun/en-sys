package com.chinacreator.c2.sys.sdk.service;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;

import java.util.List;

import javax.validation.constraints.NotNull;


/**
 * 机构服务接口
 * 只能查出授权给应用的机构数据，如果在新增、修改、删除此外的机构，将会抛出没有没有权限的异常{@link com.chinacreator.c2.sys.sdk.exception.SysResourceUnauthorizedException}
 *
 * @author 彭盛
 */
public interface OrgService {
    /**
     * 新增机构
     *
     * @param org 机构数据传输对象
     */
    public String create(@NotNull(message="{org.NotNull.message}") Orgnization org) throws SysResourcesException;

    /**
     * 删除机构
     *
     * @param orgIds 机构ID
     * @throws SysResourcesException 删除机构时发生时错误，如果机构id不存在，不会抛出异常
     */
    public void delete(@NotNull(message="{org.id.NotNull.message}") String orgId) throws SysResourcesException;

    /**
     * 修改机构
     *
     * @param org 机构数据传输对象
     */
    public void update(@NotNull(message="{org.NotNull.message}") Orgnization org) throws SysResourcesException;

    /**
     * 查询机构
     *
     * @param orgId 机构ID
     * @return 机构数据传输对象<br>
     *         没查询到的情况下返回null
     */
    public Orgnization get(@NotNull(message="{org.id.NotNull.message}") String orgId);

    /**
     * 查询子机构，不包含自身
     *
     * @param orgId
     * @param cascade
     * @return
     */
    public List<Orgnization> getChildrens(@NotNull(message="{org.id.NotNull.message}") String orgId, boolean cascade);

    /**
     * 查询当前机构及其所有父机构，不包含自身
     *
     * @param orgId 机构ID
     * @return 当前机构及其父机构数据传输对象列表，第一个是顶级机构<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Orgnization> getParents(@NotNull(message="{org.id.NotNull.message}") String orgId);

    /**
     * 判断机构是否拥有指定角色
     *
     * @param orgId 机构ID
     * @param roleId 角色ID
     * @return true:有，false:无
     */
    public boolean hasRole(@NotNull(message="{org.id.NotNull.message}") String orgId,@NotNull  String roleId);

    /**
     * 查询机构
     *
     * @param org 机构数据传输对象,null查所有机构
     * @return 机构数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Orgnization> query(Orgnization org);

    /**
     * 判断机构下是否有指定用户
     *
     * @param orgId 机构ID
     * @param userId 用户ID
     * @return true:有，false:无
     */
    public boolean containsUser(@NotNull(message="{org.id.NotNull.message}") String orgId,@NotNull String userId);
}
