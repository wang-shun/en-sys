package com.chinacreator.c2.sys.sdk.web.rest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.web.rest.OrganizationResource;
import com.chinacreator.c2.sysmgr.User;

import junit.C2JunitTests;

public class OrganizationResourceTest extends C2JunitTests {
    @Autowired
    private OrganizationResource orgResourceService;
   
    /*@Test
    public void query() {
    	String orgId = "198271A15321497B8A6B4488EB360ED9";
    	String name = "dzqdxxm_dq_";
    	String pid = "10BFB700CB8B42FEABCA6AD9DA8815DC";
    	List<Organization> query = orgResourceService.query(orgId, name, pid, null, null);
    	System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query.size()>0);
    }
    
    @Test
    public void getUsers() {
    	String orgId = "198271A15321497B8A6B4488EB360ED9";
    	int page = 1;
    	int limit = 10;
    	List<User> query = orgResourceService.getUsers(orgId, page, limit, null);
    	System.err.println(JSON.toJSONString(query));
    }*/
}
