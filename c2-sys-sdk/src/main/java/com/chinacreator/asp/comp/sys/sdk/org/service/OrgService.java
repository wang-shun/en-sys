package com.chinacreator.asp.comp.sys.sdk.org.service;

import com.chinacreator.asp.comp.sys.sdk.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.sdk.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.sdk.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.sdk.user.dto.UserDTO;

import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

import java.util.List;


/**
 * 机构服务接口
 * @author 彭盛
 */
public interface OrgService {
    /**
    * 查询指定岗位所属机构
    * @param jobId岗位ID
    * @return 机构数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public OrgDTO queryByJobId(String jobId);

    /**
    * 查询机构下拥有的岗位
    * @param orgId机构ID
    * @return 岗位数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<JobDTO> queryJobByOrgId(String orgId);

    /**
    * 给机构添加岗位
    * @param orgId机构ID
    * @param jobIds岗位ID数组
    */
    public void addJobs(String orgId, String... jobIds);

    /**
    * 批量给机构添加岗位
    * @param orgIds机构ID数组
    * @param jobIds岗位ID数组
    */
    public void addJobs(String[] orgIds, String[] jobIds);

    /**
    * 给机构设置岗位
    * @param orgId机构ID
    * @param jobIds岗位ID数组
    */
    public void setJobs(String orgId, String... jobIds);

    /**
    * 批量给机构设置岗位
    * @param orgIds机构ID数组
    * @param jobIds岗位ID数组
    */
    public void setJobs(String[] orgIds, String[] jobIds);

    /**
    * 批量删除机构下所有岗位关系
    * @param orgIds机构ID数组
    */
    public void removeAllJobs(String... orgIds);

    /**
    * 删除机构下岗位关系
    * @param orgId机构ID
    * @param jobIds岗位ID数组
    */
    public void removeJobs(String orgId, String... jobIds);

    /**
    * 批量删除机构下岗位关系
    * @param orgIds机构ID
    * @param jobIds岗位ID数组
    */
    public void removeJobs(String[] orgIds, String[] jobIds);

    /**
    * 判断机构下是否有指定岗位
    * @param orgId机构ID
    * @param jobId岗位ID
    * @return true:有，false:无
    */
    public boolean containsJob(String orgId, String jobId);

    /**
    * 新增机构
    * @param orgDTO机构数据传输对象
    */
    public void create(OrgDTO orgDTO);

    /**
    * 修改机构
    * @param orgDTO机构数据传输对象
    */
    public void update(OrgDTO orgDTO);

    /**
    * 删除机构
    * @param orgIds机构ID数组
    */
    public void deleteByPKs(String... orgIds);

    /**
    * 查询所有机构
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryAll();

    /**
    * 带权限查询所有机构
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryAllByPermission();

    /**
    * 分页查询所有机构
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryAll(Pageable pageable, Sortable sortable);

    /**
    * 带权限分页查询所有机构
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryAllByPermission(Pageable pageable,
        Sortable sortable);

    /**
    * 查询机构
    * @param orgDTO机构数据传输对象
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryByOrg(OrgDTO orgDTO);

    /**
    * 带权限查询机构
    * @param orgDTO机构数据传输对象
    * @return 机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryByOrgAndPermission(OrgDTO orgDTO);

    /**
    * 分页查询机构
    * @param orgDTO机构数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryByOrg(OrgDTO orgDTO, Pageable pageable,
        Sortable sortable);

    /**
    * 带权限分页查询机构
    * @param orgDTO机构数据传输对象
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryByOrgAndPermission(OrgDTO orgDTO,
        Pageable pageable, Sortable sortable);

    /**
    * 查询机构
    * @param orgId机构ID
    * @return 机构数据传输对象<br>
    * 没查询到的情况下返回null
    */
    public OrgDTO queryByPK(String orgId);

    /**
    * 查询当前机构及其父机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有父机构（true:所有父机构，false:仅上级父机构）
    * @return 当前机构及其父机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryFatherOrgs(String orgId, boolean isRecursion);

    /**
    * 分页查询当前机构及其父机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有父机构（true:所有父机构，false:仅上级父机构）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的当前机构及其父机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryFatherOrgs(String orgId, boolean isRecursion,
        Pageable pageable, Sortable sortable);

    /**
    * 查询指定机构的子机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有子机构（true:所有子机构，false:仅下级子机构）
    * @return 指定机构的子机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryChildOrgs(String orgId, boolean isRecursion);

    /**
    * 带权限查询指定机构的子机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有子机构（true:所有子机构，false:仅下级子机构）
    * @return 指定机构的子机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<OrgDTO> queryChildOrgsByPermission(String orgId,
        boolean isRecursion);

    /**
    * 分页查询指定机构的子机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有子机构（true:所有子机构，false:仅下级子机构）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的指定机构的子子机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryChildOrgs(String orgId, boolean isRecursion,
        Pageable pageable, Sortable sortable);

    /**
    * 带权限分页查询指定机构的子机构
    * @param orgId机构ID
    * @param isRecursion是否递归取得所有子机构（true:所有子机构，false:仅下级子机构）
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的指定机构的子机构数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<OrgDTO> queryChildOrgsByPermission(String orgId,
        boolean isRecursion, Pageable pageable, Sortable sortable);

    /**
    * 查询指定机构下的用户
    * @param orgId机构ID
    * @return 用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserDTO> queryUsers(String orgId);

    /**
    * 分页查询指定机构下的用户
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的用户数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<UserDTO> queryUsers(String orgId, Pageable pageable,
        Sortable sortable);

    /**
    * 查询指定机构所拥有的角色
    * @param orgId机构ID
    * @return 角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<RoleDTO> queryRoles(String orgId);

    /**
    * 分页查询指定机构所拥有的角色
    * @param orgId机构ID
    * @param pageable分页对象
    * @param sortable排序对象
    * @return 分页后的角色数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的Page
    */
    public Page<RoleDTO> queryRoles(String orgId, Pageable pageable,
        Sortable sortable);

    /**
    * 添加用户给机构
    * @param orgId机构ID
    * @param userId用户ID
    */
    public void addUser(String orgId, String userId);

    /**
    * 批量添加用户给机构
    * @param orgIds机构ID数组
    * @param userIds用户ID数组
    */
    public void addUsers(String[] orgIds, String[] userIds);

    /**
    * 设置用户给机构
    * @param orgId机构ID
    * @param userId用户ID
    */
    public void setUser(String orgId, String userId);

    /**
    * 批量设置用户给机构
    * @param orgIds机构ID数组
    * @param userIds用户ID数组
    */
    public void setUsers(String[] orgIds, String[] userIds);

    /**
    * 批量删除机构下所有用户关系
    * @param orgIds机构ID数组
    */
    public void removeAllUsers(String... orgIds);

    /**
    * 删除机构下用户关系
    * @param orgId机构ID
    * @param userId用户ID
    */
    public void removeUser(String orgId, String userId);

    /**
    * 批量删除机构下用户关系
    * @param orgIds机构ID数组
    * @param userIds用户ID数组
    */
    public void removeUsers(String[] orgIds, String[] userIds);

    /**
    * 授予角色给机构
    * @param orgId机构ID
    * @param roleId角色ID
    */
    public void assignRole(String orgId, String roleId);

    /**
    * 批量授予角色给机构
    * @param orgIds机构ID数组
    * @param roleIds角色ID数组
    */
    public void assignRoles(String[] orgIds, String[] roleIds);

    /**
    * 设置机构拥有的角色
    * @param orgId机构ID
    * @param roleId角色ID
    */
    public void setRole(String orgId, String roleId);

    /**
    * 批量设置机构拥有的角色
    * @param orgIds机构ID数组
    * @param roleIds角色ID数组
    */
    public void setRoles(String[] orgIds, String[] roleIds);

    /**
    * 批量回收机构拥有的所有角色
    * @param orgIds机构
    */
    public void revokeAllRoles(String... orgIds);

    /**
    * 批量回收机构拥有的角色
    * @param orgId机构ID
    * @param roleIds角色ID数组
    */
    public void revokeRoles(String orgId, String... roleIds);

    /**
    * 批量添加机构管理员
    * @param orgId机构ID
    * @param userIds用户ID数组
    */
    public void addOrgMgrs(String orgId, String... userIds);

    /**
    * 批量设置机构管理员
    * @param orgId机构ID
    * @param userIds用户ID数组
    */
    public void setOrgMgrs(String orgId, String... userIds);

    /**
    * 批量回收所有机构管理员
    * @param orgIds机构Id数组
    */
    public void removeAllOrgMgrs(String... orgIds);

    /**
    * 批量回收机构管理员
    * @param orgId机构ID
    * @param userIds用户ID数组
    */
    public void removeOrgMgrs(String orgId, String... userIds);

    /**
    * 判断机构名称是否存在（全局唯一）
    * @param orgName机构名称
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgName(String orgName);

    /**
    * 判断机构名称是否存在(忽略指定机构ID名称，编辑时用)
    * @param orgName机构名称
    * @param orgId机构ID
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgNameIgnoreOrgID(String orgName, String orgId);

    /**
    * 判断机构显示名称是否存在（同级唯一）
    * @param parentId父机构ID
    * @param orgShowName机构显示名称
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgShowName(String parentId, String orgShowName);

    /**
    * 判断机构显示名称是否存在(忽略指定机构ID名称，编辑时用)
    * @param orgShowName机构显示名称
    * @param orgId机构ID
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgShowNameIgnoreOrgID(String orgShowName,
        String orgId);

    /**
    * 判断机构编号是否存在（全局唯一 ）
    * @param orgNumber机构编号
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgNumber(String orgNumber);

    /**
    * 判断机构编号是否存在(忽略指定机构ID名称，编辑时用)
    * @param orgNumber机构编号
    * @param orgId机构ID
    * @return true:存在，false:不存在
    */
    public boolean existsByOrgNumberIgnoreOrgID(String orgNumber, String orgId);

    /**
    * 判断机构下是否有指定用户
    * @param orgId机构ID
    * @param userId用户ID
    * @return true:有，false:无
    */
    public boolean containsUser(String orgId, String userId);

    /**
    * 判断机构是否拥有指定角色
    * @param orgId机构ID
    * @param roleId角色ID
    * @return true:有，false:无
    */
    public boolean hasRole(String orgId, String roleId);

    /**
    * 判断指定用户是否指定机构的机构管理员
    * @param orgId机构ID
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isOrgMgr(String orgId, String userId);

    /**
    * 设置机构排序
    * @param orgDTOList机构数据传输对象列表<br>
    * 机构数据传输对象必须包含机构ID和机构排序号
    */
    public void setOrder(List<OrgDTO> orgDTOList);

    /**
    * 判断指定机构是否用户的主机构
    * @param orgId机构ID
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isMainOrg(String orgId, String userId);

    /**
    * 判断指定机构是否用户的可访问机构
    * @param orgId机构ID
    * @param userId用户ID
    * @return true:是，false:否
    */
    public boolean isAccessPermitted(String orgId, String userId);

    /**
    * 判断指定机构是否有子机构
    * @param orgId机构ID
    * @return true:有，false:无
    */
    public boolean existsChildOrgs(String orgId);
}
