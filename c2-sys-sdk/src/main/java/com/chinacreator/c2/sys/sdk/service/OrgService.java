package com.chinacreator.c2.sys.sdk.service;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;

/**
 * 机构服务接口
 * 
 * @author 彭盛
 */
// TODO 添加checked异常和异常说明
public interface OrgService {
    
    /**
     * 新增机构
     * 
     * @param orgDTO机构数据传输对象
     */
    public void create(Orgnization orgDTO);
    
    /**
     * 修改机构
     * 
     * @param orgDTO机构数据传输对象
     */
    public void update(Orgnization orgDTO);
    
    /**
     * 删除机构
     * 
     * @param orgIds机构ID数组
     */
    public void delete(String... orgIds);
    
    /**
     * 查询机构
     * 
     * @param orgDTO机构数据传输对象,null查所有机构
     * @return 机构数据传输对象列表<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Orgnization> query(Orgnization orgDTO);
    
    /**
     * 查询机构
     * 
     * @param orgId机构ID
     * @return 机构数据传输对象<br>
     *         没查询到的情况下返回null
     */
    public Orgnization get(String orgId);
    
    /**
     * 查询当前机构及其所有父机构，不包含自身
     * 
     * @param orgId机构ID
     * @return 当前机构及其父机构数据传输对象列表，第一个是顶级机构<br>
     *         一条记录也没查询到的情况下返回无内容的List
     */
    public List<Orgnization> getParents(String orgId);
    
    /**
     * 查询子机构，不包含自身
     * 
     * @param orgId
     * @param cascade
     * @return
     */
    public List<Orgnization> getChildrens(String orgId, boolean cascade);
    
    /**
     * 判断机构下是否有指定用户
     * 
     * @param orgId机构ID
     * @param userId用户ID
     * @return true:有，false:无
     */
    public boolean containsUser(String orgId, String userId);
    
    /**
     * 判断机构是否拥有指定角色
     * 
     * @param orgId机构ID
     * @param roleId角色ID
     * @return true:有，false:无
     */
    public boolean hasRole(String orgId, String roleId);
}
