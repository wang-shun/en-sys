package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;
import com.chinacreator.c2.sys.sdk.web.rest.OrgnizationResource;
import com.chinacreator.c2.sysmgr.User;

import junit.C2JunitTests;

public class OrganizationTest extends C2JunitTests {
    @Autowired
    private OrgnizationService orgnizationService;
   
    @Test
    public void get() throws Exception{
        String orgId = "F24123E299814E4A83281BB323E9907C";
        Organization query = orgnizationService.get(orgId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void getParents() {
        String id = "F24123E299814E4A83281BB323E9907C";
        List<Organization> query = orgnizationService.getParents(id);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query.size()>0);
    }
    
    @Test
    public void getChildren() {
        String id = "F24123E299814E4A83281BB323E9907C";
        List<Organization> query = orgnizationService.getChildren(id, true);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query.size()>0);
    }
    
    @Test
    public void containsUser() {
        String orgId = "00149E82F603428BA15C0433E29E3CC7";
        String userId =  "006574C1900E47AC91A623BFA788EB72";
        boolean query = orgnizationService.containsUser(orgId, userId);
        System.err.println(JSON.toJSONString(query));
    }
}
