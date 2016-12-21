package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.c2.sys.sdk.BeanUtils;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;


@Service("sdkOrgService")
public class OrgServiceImpl implements OrgnizationService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.org.service.OrgService orgService;

    public Organization get(String orgId) {
        OrgDTO orgDTO = orgService.queryByPK(orgId);
        Organization orgnization = BeanUtils.toBean(orgDTO);
        return orgnization;
    }

    public List<Organization> getParents(String id) {
        List<OrgDTO> orgList = orgService.queryFatherOrgs(id, true);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return BeanUtils.toBean(input);
                    }
                });

        return retList;
    }

    public List<Organization> getChildren(String id, boolean cascade) {
        List<OrgDTO> orgList = orgService.queryChildOrgs(id, cascade);
        List<Organization> retList = Lists.transform(orgList,
                new Function<OrgDTO, Organization>() {
                    public Organization apply(OrgDTO input) {
                        return BeanUtils.toBean(input);
                    }
                });

        return retList;
    }

    public boolean containsUser(String orgId, String userId) {
        return orgService.containsUser(orgId, userId);
    }
}
