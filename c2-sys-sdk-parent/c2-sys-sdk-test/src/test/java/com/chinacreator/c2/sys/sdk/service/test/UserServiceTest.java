package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import junit.C2JunitTests;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.query.UserService;
import com.chinacreator.c2.sysmgr.User;

public class UserServiceTest extends C2JunitTests {
    @Autowired
    @Qualifier("sdkUserService")
    private UserService userService;
    
    /*@Test
    public void current() {
        User query = userService.current();
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }*/
    
    @Test
    public void get() {
        String userId = "00149E82F603428BA15C0433E29E3CC7";
        User query = userService.get(userId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void queryMulti() {
        String userId1 = "00149E82F603428BA15C0433E29E3CC7";
        String userId2 = "0075BDF253C6473EABB7019461E22DA4";
        List<User> query = userService.queryMulti(userId1,userId2);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void getByUsername() {
        String userName = "apple";
        User query = userService.getByUsername(userName);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void queryMultiByUsername() {
        String[] userName = {"apple"};
        List<User> query = userService.queryMultiByUsername(userName);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void queryByOrg() {
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        List<User> query = userService.queryByOrg(orgId, true);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void queryByRole() {
    	String roleId = "2";
        List<User> query = userService.queryByRole(roleId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void getOrgs() {
        String userId = "00149E82F603428BA15C0433E29E3CC7";
        List<Organization> query = userService.getOrgs(userId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void getMainOrg() {
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        Organization query = userService.getMainOrg(orgId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    @Test
    public void inOrg() {
        String userId = "00149E82F603428BA15C0433E29E3CC7";
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        boolean query = userService.inOrg(userId, orgId);
        System.err.println(JSON.toJSONString(query));
    }
    
    @Test
    public void isMainOrg() {
        String userId = "00149E82F603428BA15C0433E29E3CC7";
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        boolean query = userService.isMainOrg(userId, orgId);
        System.err.println(JSON.toJSONString(query));
    }
    
    @Test
    public void getRoles() {
        String userId = "0163BB2AAF8E410DACF52CA2944F8E08";
        List<Role> query = userService.getRoles(userId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertTrue(query.size() > 0);
    }
    
   @Test
    public void hasRole() {
        String userId = "0163BB2AAF8E410DACF52CA2944F8E08";
        String roleId = "2";
        boolean query = userService.hasRole(userId, roleId);
        System.err.println(JSON.toJSONString(query));
    }
    
   @Test
   public void queryByRoleInOrg() {
       String orgId = "055B53C79F1C4A0981C149C3669F311C";
       String roleId = "2";
       List<User> query = userService.queryByRoleInOrg(orgId, roleId, true);
       System.err.println(JSON.toJSONString(query));
   //    Assert.assertTrue(query.size() > 0);
   }
}
