package com.chinacreator.asp.comp.sys.core.role.eo;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色与权限关系数据访问对象类
 * 
 * @author 彭盛
 * 
 */
public class RolePrivilegeEO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 角色ID */
    private String roleId;
    /** 权限ID */
    private String privilegeId;
    /** 资源ID */
    private String resId;
    /** 资源类型ID */
    private String restypeId;
    /** 标识资源维护方式，0自动维护，1手动维护 */
    private String auto;
    /** 资源名称 */
    private String resName;
    /** 资源分类 */
    private String types;
    /** 授权的方式:0:永久授权,1:计数授权,2:时效授权 */
    private Integer authorizationType;
    /** 授权可使用次数 */
    private Integer useCount;
    /** 授权使用开始时间 */
    private Date authorizationStime;
    /** 授权使用结束时间 */
    private Date authorizationEtime;
    /** 权限已使用次数 */
    private String useCounted;
    /** 机构ID */
    private String orgId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = null == roleId ? null : roleId.trim();
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = null == privilegeId ? null : privilegeId.trim();
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = null == resId ? null : resId.trim();
    }

    public String getRestypeId() {
        return restypeId;
    }

    public void setRestypeId(String restypeId) {
        this.restypeId = null == restypeId ? null : restypeId.trim();
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = null == auto ? null : auto.trim();
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = null == resName ? null : resName.trim();
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = null == types ? null : types.trim();
    }

    public Integer getAuthorizationType() {
        return authorizationType;
    }

    public void setAuthorizationType(Integer authorizationType) {
        this.authorizationType = null == authorizationType ? null
                : authorizationType;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = null == useCount ? null : useCount;
    }

    public Date getAuthorizationStime() {
        return null == authorizationStime ? null : (Date) authorizationStime
                .clone();
    }

    public void setAuthorizationStime(Date authorizationStime) {
        this.authorizationStime = null == authorizationStime ? null
                : (Date) authorizationStime.clone();
    }

    public Date getAuthorizationEtime() {
        return null == authorizationEtime ? null : (Date) authorizationEtime
                .clone();
    }

    public void setAuthorizationEtime(Date authorizationEtime) {
        this.authorizationEtime = null == authorizationEtime ? null
                : (Date) authorizationEtime.clone();
    }

    public String getUseCounted() {
        return useCounted;
    }

    public void setUseCounted(String useCounted) {
        this.useCounted = null == useCounted ? null : useCounted.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = null == orgId ? null : orgId.trim();
    }
}
