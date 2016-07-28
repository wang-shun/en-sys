package com.chinacreator.c2.sys.sdk;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.OrgUserModel;
import com.chinacreator.c2.sys.sdk.bean.User;

public interface Utils {
    /**
     * 查询指定机构下所有的机构和用户数据，用户所属的机构的信息在 {@link User#getExtFields()}的属性中
     * @param orgId 
     * @return 机构用户型数据
     */
    public List<? extends OrgUserModel> getOrgAndUser(String orgId);
}
