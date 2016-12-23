package com.chinacreator.asp.comp.sys.core.common;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.core.GroupMessages;
import com.chinacreator.asp.comp.sys.core.PrivilegeMessages;
import com.chinacreator.asp.comp.sys.core.RoleMessages;
import com.chinacreator.asp.comp.sys.core.UserMessages;

/**
 * 验证工具类
 * 
 * @author 彭盛
 * 
 */
@Component
public class ValidatorUtil {

    /**
     * 用户活动范围验证
     * 
     * @param scopeType
     *            用户活动范围类型（0：全局，1：机构，2：岗位）
     * @param scopeId
     *            用户活动范围ID（当用户活动范围类型为0时，该用户活动范围ID为null；当用户活动范围类型为1时，
     *            该用户活动范围ID为机构ID；当用户活动范围类型为2时， 该用户活动范围ID为岗位ID；以此类推）
     */
    public static void validateScope(int scopeType, String scopeId) {
        switch (scopeType) {
        case 0:
            if (null != scopeId) {
                throw new IllegalArgumentException(
                        UserMessages.getString("USER.SCOPEID_MUST_NULL"));
            }
            break;
        case 1:
        case 2:
            if (null == scopeId || scopeId.trim().equals("")) {
                throw new IllegalArgumentException(
                        UserMessages.getString("USER.SCOPEID_IS_NULL"));
            }
            break;
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
            break;
        default:
            throw new IllegalArgumentException(
                    UserMessages.getString("USER.SCOPETYPE_IS_INVALID"));
        }
    }

    /**
     * 用户ID验证，并去重复与空值
     * 
     * @param userIds
     *            用户ID数组
     * @return 去重复与空值后的用户ID数组
     */
    public static String[] validateUserId(String... userIds) {
        if (null != userIds && userIds.length > 0) {
            // 去除重复与空值
            Set<String> set = new HashSet<String>();
            for (String id : userIds) {
                if (null != id && !id.trim().equals("")) {
                    set.add(id);
                }
            }
            if (!set.isEmpty()) {
                String[] ids = new String[set.size()];
                set.toArray(ids);
                return ids;
            } else {
                throw new NullPointerException(
                        UserMessages.getString("USER.USERID_IS_NULL"));
            }
        } else {
            throw new NullPointerException(
                    UserMessages.getString("USER.USERID_IS_NULL"));
        }
    }

    /**
     * 角色ID验证，并去重复与空值
     * 
     * @param roleIds
     *            角色ID数组
     * @return 去重复与空值后的角色ID数组
     */
    public static String[] validateRoleId(String... roleIds) {
        if (null != roleIds && roleIds.length > 0) {
            // 去除重复与空值
            Set<String> set = new HashSet<String>();
            for (String id : roleIds) {
                if (null != id && !id.trim().equals("")) {
                    set.add(id);
                }
            }
            if (!set.isEmpty()) {
                String[] ids = new String[set.size()];
                set.toArray(ids);
                return ids;
            } else {
                throw new NullPointerException(
                        RoleMessages.getString("ROLE.ROLEID_IS_NULL"));
            }
        } else {
            throw new NullPointerException(
                    RoleMessages.getString("ROLE.ROLEID_IS_NULL"));
        }
    }

    /**
     * 用户组ID验证，并去重复与空值
     * 
     * @param groupIds
     *            用户组ID数组
     * @return 去重复与空值后的用户组ID数组
     */
    public static String[] validateGroupId(String... groupIds) {
        if (null != groupIds && groupIds.length > 0) {
            // 去除重复与空值
            Set<String> set = new HashSet<String>();
            for (String id : groupIds) {
                if (null != id && !id.trim().equals("")) {
                    set.add(id);
                }
            }
            if (!set.isEmpty()) {
                String[] ids = new String[set.size()];
                set.toArray(ids);
                return ids;
            } else {
                throw new NullPointerException(
                        GroupMessages.getString("GROUP.GROUPID_IS_NULL"));
            }
        } else {
            throw new NullPointerException(
                    GroupMessages.getString("GROUP.GROUPID_IS_NULL"));
        }
    }

    /**
     * 权限ID验证，并去重复与空值
     * 
     * @param privilegeIds
     *            权限ID数组
     * @return 去重复与空值后的权限ID数组
     */
    public static String[] validatePrivilegeId(String... privilegeIds) {
        if (null != privilegeIds && privilegeIds.length > 0) {
            // 去除重复与空值
            Set<String> set = new HashSet<String>();
            for (String id : privilegeIds) {
                if (null != id && !id.trim().equals("")) {
                    set.add(id);
                }
            }
            if (!set.isEmpty()) {
                String[] ids = new String[set.size()];
                set.toArray(ids);
                return ids;
            } else {
                throw new NullPointerException(
                        PrivilegeMessages
                                .getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
            }
        } else {
            throw new NullPointerException(
                    PrivilegeMessages
                            .getString("PRIVILEGE.PRIVILEGEID_IS_NULL"));
        }
    }
}
