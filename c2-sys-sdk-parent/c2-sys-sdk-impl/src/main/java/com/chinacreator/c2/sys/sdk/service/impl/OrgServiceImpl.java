package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.c2.sys.sdk.BeanUtils;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrganizationService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class OrgServiceImpl implements OrganizationService {
	
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.org.service.OrgService orgService;

    public Organization get(String orgId) {
		OrgDTO orgDTO = orgService.queryByPK(orgId);
		if(orgDTO == null){
			return null;
		}
		
		return BeanUtils.toBean(orgDTO);
    }

    @Override
	public Organization getByName(String orgName) {
		  OrgDTO orgDTO = new OrgDTO();
		  orgDTO.setOrgName(orgName);
		  List<OrgDTO> orgList = orgService.queryByOrg(orgDTO);
		  if(orgList != null && !orgList.isEmpty()){
			  orgDTO = orgList.get(0);
	        }
		  
	       return BeanUtils.toBean(orgDTO);
		  
	}
    
    public List<Organization> getParents(String id) {
		List<OrgDTO> orgList = orgService.queryFatherOrgs(id, true);
		if(orgList == null || orgList.size()==0){
			return new ArrayList<Organization>();
		}
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
        if(orgList == null || orgList.size()==0){
        	return new ArrayList<Organization>();
        }
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
