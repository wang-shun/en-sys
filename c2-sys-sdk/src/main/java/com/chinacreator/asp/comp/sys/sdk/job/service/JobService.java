package com.chinacreator.asp.comp.sys.sdk.job.service;

import com.chinacreator.asp.comp.sys.sdk.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.sdk.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 岗位服务接口
 * @author 彭盛
 */
public interface JobService {
    /**
    * 新增岗位
    * @param jobDTO岗位数据传输对象
    */
    public void create(JobDTO jobDTO);

    /**
    * 修改岗位
    * @param jobDTO岗位数据传输对象
    */
    public void update(JobDTO jobDTO);

    /**
    * 删除岗位
    * @param jobIds岗位ID数组
    */
    public void deleteByPKs(String... jobIds);

    /**
    * 查询岗位
    * @param jobId岗位ID
    * @return 岗位数据传输对象列表<br>
    * 没查询到的情况下返回null
    */
    public JobDTO queryByPK(String jobId);

    /**
    * 查询岗位
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryAll();

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryByJob(JobDTO jobDTO);

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<JobDTO> queryByJob(JobDTO jobDTO, Pageable pageable,
        Sortable sortable);

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @param orgId机构ID
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryByOrg(JobDTO jobDTO, String orgId);

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<JobDTO> queryByOrg(JobDTO jobDTO, String orgId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @param userId用户ID
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryByUser(JobDTO jobDTO, String userId);

    /**
    * 查询岗位
    * @param jobDTO岗位数据传输对象
    * @param userId用户ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<JobDTO> queryByUser(JobDTO jobDTO, String userId,
        Pageable pageable, Sortable sortable);

    /**
    * 查询岗位
    * @param orgId机构ID
    * @param userId用户ID
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryByOrgUser(String orgId, String userId);

    /**
    * 查询指定岗位所拥有的角色
    * @param jobId岗位ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String jobId);

    /**
    * 查询指定岗位所属机构
    * @param jobId岗位ID
    * @return 机构数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public OrgDTO queryOrgByJobId(String jobId);

    /**
    * 添加岗位到机构
    * @param jobId岗位ID
    * @param orgIds机构ID数组
    */
    public void addToOrgs(String jobId, String... orgIds);

    /**
    * 添加岗位到机构
    * @param jobIds岗位ID数组
    * @param orgIds机构ID数组
    */
    public void addToOrgs(String[] jobIds, String[] orgIds);

    /**
    * 设置岗位到机构
    * @param jobId岗位ID
    * @param orgIds机构ID数组
    */
    public void setToOrgs(String jobId, String... orgIds);

    /**
    * 设置岗位到机构
    * @param jobIds岗位ID数组
    * @param orgIds机构ID数组
    */
    public void setToOrgs(String[] jobIds, String[] orgIds);

    /**
    * 删除岗位所属的所有机构关系
    * @param jobIds岗位ID数组
    */
    public void removeFromAllOrgs(String... jobIds);

    /**
    * 删除岗位所属的机构关系
    * @param jobId岗位ID
    * @param orgIds机构ID数组
    */
    public void removeFromOrgs(String jobId, String... orgIds);

    /**
    * 删除岗位所属的机构关系
    * @param jobIds岗位ID数组
    * @param orgIds机构ID数组
    */
    public void removeFromOrgs(String[] jobIds, String[] orgIds);

    /**
    * 添加岗位到机构下用户
    * @param jobId岗位ID
    * @param userIds用户ID数组
    */
    public void addToUsers(String jobId, String... userIds);

    /**
    * 添加岗位到机构下用户
    * @param jobIds岗位ID数组
    * @param userIds用户ID数组
    */
    public void addToUsers(String[] jobIds, String[] userIds);

    /**
    * 设置岗位到机构下用户
    * @param jobId岗位ID
    * @param userIds用户ID数组
    */
    public void setToUsers(String jobId, String... userIds);

    /**
    * 设置岗位到机构下用户
    * @param jobIds岗位ID数组
    * @param userIds用户ID数组
    */
    public void setToUsers(String[] jobIds, String[] userIds);

    /**
    * 删除岗位所属的所有机构下用户的关系
    * @param jobIds岗位ID数组
    */
    public void removeFromAllUsers(String... jobIds);

    /**
    * 删除岗位所属的所有机构下用户的关系
    * @param jobId岗位ID
    * @param userIds用户ID数组
    */
    public void removeFromUsers(String jobId, String... userIds);

    /**
    * 删除岗位所属的所有机构下用户的关系
    * @param jobIds岗位ID数组
    * @param userIds用户ID数组
    */
    public void removeFromUsers(String[] jobIds, String[] userIds);

    /**
    * 添加岗位拥有的角色
    * @param jobId岗位ID
    * @param roleIds角色ID数组
    */
    public void assignRoles(String jobId, String... roleIds);

    /**
    * 批量添加岗位拥有的角色
    * @param jobIds岗位ID数组
    * @param roleIds角色ID数组
    */
    public void assignRoles(String[] jobIds, String[] roleIds);

    /**
    * 设置岗位拥有的角色
    * @param jobId岗位ID
    * @param roleIds角色ID数组
    */
    public void setRoles(String jobId, String... roleIds);

    /**
    * 批量设置岗位拥有的角色
    * @param jobIds岗位ID数组
    * @param roleIds角色ID数组
    */
    public void setRoles(String[] jobIds, String[] roleIds);

    /**
    * 批量回收岗位拥有的所有角色关系
    * @param jobIds岗位ID数组
    */
    public void revokeAllRoles(String... jobIds);

    /**
    * 回收岗位拥有的指定角色关系
    * @param jobId岗位ID
    * @param roleIds角色ID数组
    */
    public void revokeRoles(String jobId, String... roleIds);

    /**
    * 批量回收岗位拥有的指定角色关系
    * @param jobIds岗位ID数组
    * @param roleIds角色ID数组
    */
    public void revokeRoles(String[] jobIds, String[] roleIds);

    /**
    * 判断岗位是否属于指定机构
    * @param jobId岗位ID
    * @param orgId机构ID
    * @return true:是，false:否
    */
    public boolean belongsToOrg(String jobId, String orgId);

    /**
    * 判断岗位是否拥有指定角色
    * @param jobId岗位ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasRole(String jobId, String roleId);

    /**
    * 查询岗位下的用户
    * @param jobId岗位ID
    * @return 用户信息列表
    */
    public List<UserDTO> queryUsers(String jobId);

    /**
    * 判断岗位下是否有指定用户
    * @param jobId岗位ID
    * @param userId用户ID
    * @return true:有，false:无
    */
    public boolean containsUser(String jobId, String userId);

    /**
    * 判断机构下岗位是否有指定用户
    * @param orgId机构ID
    * @param jobId岗位ID
    * @param userId用户ID
    * @return true:有，false:无
    */
    public boolean containsUser(String orgId, String jobId, String userId);
}
