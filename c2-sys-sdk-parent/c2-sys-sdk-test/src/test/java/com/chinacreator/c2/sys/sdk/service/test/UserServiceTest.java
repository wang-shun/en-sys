package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import junit.C2JunitTests;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
import com.chinacreator.c2.sys.sdk.service.UserService;

public class UserServiceTest extends C2JunitTests {
    @Autowired
    private UserService userService;
    
    public void create()
        throws SysResourcesException {
        String orgId = "0227928B2E7D46E0A5A19A0A831617FF";
        User user = new User();
        user.setUserId("7424");
        user.setUserRealname("test");
        user.setUserName("test");
        user.setUserPassword("1");
        String id = userService.create(user, orgId);
        Assert.assertNotNull(id);
    }
    
    public void addOrg()
        throws SysResourcesException {
        String userId = "007D332057E44A2DB0ADEB33CE15D408";
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        userService.addOrg(userId, orgId);
    }
    
    public void delete()
        throws SysResourcesException {
        String userId = "007D332057E44A2DB0ADEB33CE15D408";
        userService.delete(userId);
    }
    
    public void deleteUserOrgRelationship()
        throws SysResourcesException {
        String userId = "007D332057E44A2DB0ADEB33CE15D408";
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        userService.deleteUserOrgRelationship(userId, orgId);
    }
    
    public void update()
        throws SysResourcesException {
        User user = new User();
        user.setUserId("01DC06C4C71E410B84F9C0437703EB7C");
        user.setUserEmail("bowen.tan@chinacreator.com");
        userService.update(user);
    }
    
    public void setMainOrg()
        throws SysResourcesException {
        String[] userIds = {"01DC06C4C71E410B84F9C0437703EB7C"};
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        boolean isRetain = true;
        userService.setMainOrg(userIds, orgId, isRetain);
    }
    
    public void queryByOrg() {
        User user = new User();
        user.setUserId("01DC06C4C71E410B84F9C0437703EB7C");
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        boolean cascade = true;
        List<User> queryByOrg = userService.queryByOrg(user, orgId, cascade);
        System.err.println(JSON.toJSONString(queryByOrg));
    }
    @Test
    public void queryByRole() {
        User user = new User();
        user.setUserName("01DC06C4C71E410B84F9C0437703EB7C");
        String roleId = "2";
        List<User> queryByRole = userService.queryByRole(user, roleId);
        System.err.println(JSON.toJSONString(queryByRole));
    }
    @Test
    public void getOrgs() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        List<Orgnization> orgs = userService.getOrgs(userId);
        System.err.println(JSON.toJSONString(orgs));
        Assert.assertTrue(orgs.size() > 0);
    }
    
    public void getMainOrg() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        Orgnization mainOrg = userService.getMainOrg(userId);
        Assert.assertNotNull(mainOrg);
    }
    
    public void inOrg() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        boolean inOrg = userService.inOrg(userId, orgId);
        System.err.println(inOrg);
    }
    
    public void isMainOrg() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        String orgId = "025026B2A1864DD1A1256DA55A7739E7";
        boolean inOrg = userService.isMainOrg(userId, orgId);
        System.err.println(inOrg);
    }
    
    public void query() {
        User user = new User();
        user.setUserName("mei.liu1");
        List<User> query = userService.query(user);
        System.err.println(JSON.toJSONString(query));
        Assert.assertTrue(query.size() > 0);
    }
    
    public void get() {
        User user = new User();
        user.setUserName("mei.liu1");
        User query = userService.get(user);
        System.err.println(JSON.toJSONString(query));
        Assert.assertNotNull(query);
    }
    
    public void queryByIds() {
        String[] userIds = {"01DC06C4C71E410B84F9C0437703EB7C"};
        List<User> query = userService.query(userIds);
        System.err.println(JSON.toJSONString(query));
        Assert.assertTrue(query.size() > 0);
    }
    
    public void getRoles() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        List<Role> query = userService.getRoles(userId);
        System.err.println(JSON.toJSONString(query));
        Assert.assertTrue(query.size() > 0);
    }
    
    
    public void hasRole() {
        String userId = "01DC06C4C71E410B84F9C0437703EB7C";
        String roleId = "2";
        boolean query = userService.hasRole(userId, roleId);
        System.err.println(JSON.toJSONString(query));
    }
}
