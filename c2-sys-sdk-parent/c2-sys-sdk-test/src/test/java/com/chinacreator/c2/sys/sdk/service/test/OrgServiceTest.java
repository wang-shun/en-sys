package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import junit.C2JunitTests;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
import com.chinacreator.c2.sys.sdk.service.OrgService;

public class OrgServiceTest extends C2JunitTests {
    @Autowired
    private OrgService orgService;
    
    // @Rule
    // public ParameterRule testRule = new ParameterRule();
    // public static Map map=new HashMap();
    
    public void create() {
        Orgnization org = new Orgnization();
        org.setParentId("15C497DA3E814840881EC13073E604EA");
        org.setCode("0830");
        org.setOrgNumber("0830");
        org.setOrgName("南海问题办公室");
        org.setOrgShowName("南海问题办公室");
        String id = null;
        try {
            id = orgService.create(org);
        }
        catch (SysResourcesException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(id);
    }
    
    public void update() {
        Orgnization org = new Orgnization();
        org.setOrgId("366BEA00DBB5472DA3D3F16F95E187BF");
        org.setOrgName("测试机构");
        try {
            orgService.update(org);
        }
        catch (SysResourcesException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void delete() {
        String orgId = "366BEA00DBB5472DA3D3F16F95E187BF";
        try {
            orgService.delete(orgId);
        }
        catch (SysResourcesException e) {
            e.printStackTrace();
        }
    }
    
    public void query() {
        Orgnization org = new Orgnization();
        org.setOrgId("025026B2A1864DD1A1256DA55A7739E7");
        List<Orgnization> query = orgService.query(org);
        System.err.println(JSON.toJSONString(query));
        Assert.assertTrue(query.size() > 0);
    }
    
    public void get() {
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        Orgnization orgnization = orgService.get(orgId);
        System.err.println(JSON.toJSONString(orgnization));
        Assert.assertNotNull(orgnization);
    }
    
    public void getParents() {
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        List<Orgnization> parents = orgService.getParents(orgId);
        System.err.println(JSON.toJSONString(parents));
        Assert.assertTrue(parents.size() > 0);
    }
    
    public void getChildrens() {
        String orgId = "2FE83BF947244C85B299F58CDF0E2CEF";
        boolean cascade = true;
        List<Orgnization> childrens = orgService.getChildrens(orgId, cascade);
        System.err.println(JSON.toJSONString(childrens));
        Assert.assertTrue(childrens.size() > 0);
    }
    
    public boolean containsUser(String orgId, String userId) {
        return orgService.containsUser(orgId, userId);
    }
    
    public boolean hasRole(String orgId, String roleId) {
        return orgService.hasRole(orgId, roleId);
    }
}
