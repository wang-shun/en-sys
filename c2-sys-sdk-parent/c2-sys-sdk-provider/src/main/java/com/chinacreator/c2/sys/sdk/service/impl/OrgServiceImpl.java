package com.chinacreator.c2.sys.sdk.service.impl;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.service.OrgService;

import org.mvel2.optimizers.impl.refl.nodes.ArrayAccessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sdkOrgService")
public class OrgServiceImpl implements OrgService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.org.service.OrgService orgService;

    @Override
    public void create(Orgnization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(orgDTO, org);
        orgService.create(orgDTO);
    }

    @Override
    public void update(Orgnization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(orgDTO, org);
        orgService.update(orgDTO);
    }

    @Override
    public void delete(String orgId) {
        orgService.deleteByPKs(orgId);
    }

    @Override
    public List<Orgnization> query(Orgnization org) {
        OrgDTO orgDTO = new OrgDTO();
        BeanCopierUtil.copy(org, orgDTO);

        List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
        List<Orgnization> retList = new ArrayList<Orgnization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Orgnization.class);

        return retList;
    }

    @Override
    public Orgnization get(String orgId) {
        Orgnization orgnization = new Orgnization();
        OrgDTO orgDto = orgService.queryByPK(orgId);
        BeanCopierUtil.copy(orgDto, orgnization);

        return orgnization;
    }

    @Override
    public List<Orgnization> getParents(String orgId) {
        List<OrgDTO> orgList = orgService.queryFatherOrgs(orgId, true);
        List<Orgnization> retList = new ArrayList<Orgnization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Orgnization.class);

        return retList;
    }

    @Override
    public List<Orgnization> getChildrens(String orgId, boolean cascade) {
        List<OrgDTO> orgList = orgService.queryChildOrgs(orgId, true);
        List<Orgnization> retList = new ArrayList<Orgnization>();
        BeanCopierUtil.copy(orgList, retList, OrgDTO.class, Orgnization.class);

        return retList;
    }

    @Override
    public boolean containsUser(String orgId, String userId) {
        return orgService.containsUser(orgId, userId);
    }

    @Override
    public boolean hasRole(String orgId, String roleId) {
        return orgService.hasRole(orgId, roleId);
    }
}
