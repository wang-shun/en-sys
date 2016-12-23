package com.chinacreator.c2.sys.sdk.service.query;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sysmgr.User;

public interface OrgUserTreeService {
    /**
     * 查询指定机构下所有的机构和用户数据，用户所属的机构的信息在 {@link User#get("orgId")}的属性中,
     * {@link User#getExtFields()}返回的Map Key如下:orgId-机构ID,sn-机构编号,orgShowName-机构显示名称
     * 
     * @param orgId 机构id，null表示查所有
     * @return 机构用户型数据
     * @see Organization
     * @see User
     */
    public List<Object> getOrgAndUser(String orgId);
}
