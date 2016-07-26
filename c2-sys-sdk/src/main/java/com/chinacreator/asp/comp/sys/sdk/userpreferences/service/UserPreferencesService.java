package com.chinacreator.asp.comp.sys.sdk.userpreferences.service;

import com.chinacreator.asp.comp.sys.sdk.userpreferences.dto.UserPreferencesDTO;

import java.util.List;


/**
 * 用户偏好设置信息服务接口
 * @author 彭盛
 */
public interface UserPreferencesService {
    /**
    * 新增用户偏好设置信息
    * @param userPreferencesDTO用户偏好设置信息数据传输对象类
    */
    public void create(UserPreferencesDTO userPreferencesDTO);

    /**
    * 修改用户偏好设置信息
    * @param userPreferencesDTO用户偏好设置信息数据传输对象类
    */
    public void update(UserPreferencesDTO userPreferencesDTO);

    /**
    * 设置用户偏好设置信息<br>
    * 通过用户ID，判断信息名称是否存在<br>
    * 如果不存在，执行新增操作<br>
    * 如果存在，执行修改操作
    * @param userPreferencesDTO用户偏好设置信息数据传输对象类
    */
    public void set(UserPreferencesDTO userPreferencesDTO);

    /**
    * 删除用户偏好设置信息
    * @param userIds用户ID数组
    * @param infoNames信息名称数组
    */
    public void deleteByPKs(String[] userIds, String[] infoNames);

    /**
    * 删除用户偏好设置信息
    * @param userIds用户ID数组
    */
    public void deleteByUserIDs(String... userIds);

    /**
    * 删除用户偏好设置信息
    * @param infoNames信息名称数组
    */
    public void deleteByInfoNames(String... infoNames);

    /**
    * 查询所有用户偏好设置信息
    * @return 用户偏好设置信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserPreferencesDTO> queryAll();

    /**
    * 查询用户偏好设置信息
    * @param userSettingDTO
    * @return 用户偏好设置信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回无内容的List
    */
    public List<UserPreferencesDTO> queryByUserPreferences(
        UserPreferencesDTO userPreferencesDTO);

    /**
    * 查询用户偏好设置信息
    * @param userId用户ID
    * @param infoName信息名称
    * @return 用户偏好设置信息数据传输对象列表<br>
    * 一条记录也没查询到的情况下返回null
    */
    public UserPreferencesDTO queryByPK(String userId, String infoName);

    /**
    * 判断用户偏好设置信息是否存在
    * @param userId用户ID
    * @param infoName信息名称
    * @return true:存在，false:不存在
    */
    public boolean existsByInfoName(String userId, String infoName);
}
