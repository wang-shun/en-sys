package com.chinacreator.c2.sys.sdk.service.management;
//TODO sdk中暂不提供任何管理接口
//package com.chinacreator.c2.sys.sdk.service;
//
//import javax.validation.constraints.NotNull;
//
//import com.chinacreator.c2.sys.sdk.bean.User;
//import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
//
//
///**
// * 用户管理接口
// *
// * @author 彭盛
// */
//public interface UserManageService {
//    /**
//     * 在指定机构下新增用户
//     * 约束：
//     * 1.orgId不能为空
//     * 2.用户名都会被强制转换成小写进行判断和存储
//     * 3.用户名不能已存在
//     * 4.密码不能为空
//     * 5.用户实名不能为空
//     * 
//     * @param user 用户数据传输对象
//     * @param orgId 机构ID
//     * @param sn 用户在机构下的排序号
//     * @return 修改后的用户对象
//     */
//    public User create(@NotNull(message="org.id.NotNull.message") String orgId,@NotNull(message="user.NotNull.message") User user) throws SysResourcesException;
//
//    /**
//     * 添加用户的所属机构
//     * 如需设置主机构请使用{@link #setMainOrg(String[], String, boolean)}
//     *
//     * @param userId 用户ID
//     * @param orgId 机构ID
//     */
//    public void addOrg(@NotNull(message="user.id.NotNull.message") String userId, @NotNull(message="org.id.NotNull.message") String orgId)
//        throws SysResourcesException;
//
//    /**
//     * 删除用户及用户所有相关的关联关系
//     *
//     * @param userId 用户ID串，多个用逗号分开
//     */
//    public void delete(@NotNull String userIds) throws SysResourcesException;
//
//    /**
//     * 删除用户和机构的关联关系
//     * 主机构不能删
//     *
//     * @param userId 用户ID
//     * @param orgId 机构ID
//     */
//    public void deleteUserOrgRelationship(@NotNull(message="user.id.NotNull.message") String userId, @NotNull(message="org.id.NotNull.message") String orgId)
//        throws SysResourcesException;
//
//    /**
//     * 修改用户
//     *
//     * @param id 用户ID
//     * @param user 用户数据传输对象
//     * @return 修改后的用户对象
//     */
//    public User update(@NotNull(message="user.id.NotNull.message") String id,@NotNull(message="user.NotNull.message") User user) throws SysResourcesException;
//   
//    /**
//     * 替换用户信息，使用参数中的用户对象（包含空属性）整体替换库中现有的记录,如果用户id不存在则创建一条新纪录
//     *
//     * @param id 用户ID
//     * @param user 用户数据传输对象
//     * @return 修改后的用户对象
//     */
//    public User replace(@NotNull(message="user.id.NotNull.message") String id,@NotNull(message="user.NotNull.message") User user) throws SysResourcesException;
//    
//    /**
//     * 用户设置主机构
//     *
//     * @param userIds 用户ID数组
//     * @param orgId 主机构ID
//     * @param isRetain 用户是否保留在原机构下(true:是，false:否)
//     */
//    public void setMainOrg(@NotNull(message="user.id.NotNull.message") String userIds,@NotNull(message="org.id.NotNull.message") String orgId, boolean isRetain)
//        throws SysResourcesException;
//}
