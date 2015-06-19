package com.chinacreator.asp.comp.sys.core.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceGroupEO;

/**
 * 用户实例与用户组关系数据访问接口
 * 
 * @author 林伏山
 * 
 */
@Repository
public interface UserInstanceGroupDao {

    /**
     * 新增用户实例与用户组关系
     * 
     * @param userInstanceGroupEO
     *            用户实例与用户组关系数据传输对象
     * @return 影响到的行数
     */
    public int create(UserInstanceGroupEO userInstanceGroupEO);

    /**
     * 批量新增用户实例与用户组关系
     * 
     * @param userInstanceGroupEOs
     *            用户实例与用户组关系数据传输对象集合
     * @return 影响到的行数
     */
    public int createBatch(List<UserInstanceGroupEO> userInstanceGroupEOs);

    /**
     * 修改用户实例与用户组关系的排序号
     * 
     * @param userInstanceGroupEO
     *            用户实例与用户组关系数据传输对象
     * @return 数据库执行操作影响到的行数
     */
    public int updateSN(UserInstanceGroupEO userInstanceGroupEO);

    /**
     * 删除用户实例与用户组关系
     * 
     * @param groupIds
     *            用户组ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByGroupIds(String... groupIds);

    /**
     * 删除用户实例与用户组关系
     * 
     * @param userInstanceIds
     *            用户实例ID数组
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByUserInstanceIds(String... userInstanceIds);

    /**
     * 删除用户实例与用户组关系
     * 
     * @param userInstanceId
     *            用户实例ID
     * @param groupId
     *            用户组ID
     * @return 数据库执行操作影响到的行数
     */
    public int deleteByUserInstanceIdAndGroupId(
            @Param("userInstanceId") String userInstanceId,
            @Param("groupId") String groupId);

}
