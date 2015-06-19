package com.chinacreator.asp.comp.sys.core.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;

/**
 * 用户实例工具类
 * 
 * @author 彭盛
 * 
 */
@Component
public class UserInstanceUtil {

    @Autowired
    private UserInstanceDao userInstanceDao;

    /**
     * 获取用户实例
     * 
     * @param userIds
     *            用户Id数组
     * @return 用户实例ID数组
     */
    public String[] getUserInstanceIdByUserId(String... userIds) {
        // 用户ID验证，并去重复与空值
        String[] uIds = ValidatorUtil.validateUserId(userIds);

        // 获取用户实例
        Set<String> userInstanceIdSet = new HashSet<String>();
        for (String userId : uIds) {
            UserInstanceEO userInstanceEO = new UserInstanceEO();
            userInstanceEO.setUserId(userId);
            List<UserInstanceEO> userInstanceEOList = userInstanceDao
                    .queryByUserInstance(userInstanceEO);
            if (!userInstanceEOList.isEmpty()) {
                for (UserInstanceEO eo : userInstanceEOList) {
                    userInstanceIdSet.add(eo.getId());
                }
            }
        }

        if (!userInstanceIdSet.isEmpty()) {
            String[] userInstanceIds = new String[userInstanceIdSet.size()];
            userInstanceIdSet.toArray(userInstanceIds);
            return userInstanceIds;
        }
        return new String[] {};
    }

    /**
     * 获取用户实例
     * 
     * @param userId
     *            用户ID
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户实例ID数组
     */
    public String getUserInstanceIdByUserIdAndScope(String userId,
            int scopeType, String scopeId) {
        // 用户ID验证，并去重复与空值
        ValidatorUtil.validateUserId(userId);
        // 用户活动范围验证
        ValidatorUtil.validateScope(scopeType, scopeId);

        UserInstanceEO userInstanceEO = userInstanceDao.queryByUserAndScope(
                userId, scopeType + "", scopeId);

        return (null != userInstanceEO && null != userInstanceEO.getId() && !userInstanceEO
                .getId().trim().equals("")) ? userInstanceEO.getId() : null;
    }

    /**
     * 获取用户实例
     * 
     * @param userIds
     *            用户ID数组
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     * @return 用户实例ID数组
     */
    public String[] getUserInstanceIdByUserIdAndScope(String[] userIds,
            int scopeType, String scopeId) {
        // 用户ID验证，并去重复与空值
        String[] uIds = ValidatorUtil.validateUserId(userIds);
        // 用户活动范围验证
        ValidatorUtil.validateScope(scopeType, scopeId);

        // 获取用户实例
        Set<String> userInstanceIdSet = new HashSet<String>();
        for (String userId : uIds) {
            UserInstanceEO userInstanceEO = userInstanceDao
                    .queryByUserAndScope(userId, scopeType + "", scopeId);
            if (null != userInstanceEO && null != userInstanceEO.getId()
                    && !userInstanceEO.getId().trim().equals("")) {
                userInstanceIdSet.add(userInstanceEO.getId());
            }
        }

        if (!userInstanceIdSet.isEmpty()) {
            String[] userInstanceIds = new String[userInstanceIdSet.size()];
            userInstanceIdSet.toArray(userInstanceIds);
            return userInstanceIds;
        }
        return new String[] {};
    }
}
