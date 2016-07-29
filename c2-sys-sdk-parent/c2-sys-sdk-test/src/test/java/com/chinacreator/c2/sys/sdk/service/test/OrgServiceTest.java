package com.chinacreator.c2.sys.sdk.service.test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.C2JunitTests;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.service.OrgService;


public class OrgServiceTest  extends C2JunitTests{
    @Autowired
    private OrgService orgService;
//    @Rule
//    public ParameterRule testRule = new ParameterRule();
//    public static Map map=new HashMap();
    
    public void create(Orgnization org) {}

    
    public void update(Orgnization org) {}

    
    public void delete(String orgId) {}

    @Test
    public void query() {
        Orgnization org = new Orgnization();
        org.setOrgId("1");
        List<Orgnization> query = orgService.query(org);
        System.out.println(JSON.toJSONString(query));
    }

    
    public Orgnization get(String orgId) {
        return orgService.get(orgId);
    }

    
    public List<Orgnization> getParents(String orgId) {
        return orgService.getParents(orgId);
    }

    
    public List<Orgnization> getChildrens(String orgId, boolean cascade) {
        return orgService.getChildrens(orgId, cascade);
    }

    
    public boolean containsUser(String orgId, String userId) {
        return orgService.containsUser(orgId, userId);
    }

    
    public boolean hasRole(String orgId, String roleId) {
        return orgService.hasRole(orgId, roleId);
    }
}
