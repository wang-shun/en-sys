//package com.chinacreator.c2.sys.sdk.service.test;
//
//
//import java.util.List;
//
//import junit.C2JunitTests;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.chinacreator.c2.sys.sdk.bean.Orgnization;
//import com.chinacreator.c2.sys.sdk.bean.Role;
//import com.chinacreator.c2.sys.sdk.bean.User;
//import com.chinacreator.c2.sys.sdk.exception.SysResourcesException;
//import com.chinacreator.c2.sys.sdk.service.UserService;
//
//
//public class UserServiceTest extends C2JunitTests {
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void create(User user, String orgId) throws SysResourcesException {
//        userService.create(user, orgId);
//    }
//
//    @Test
//    public void addOrg(String userId, String orgId)
//        throws SysResourcesException {
//        userService.addOrg(userId, orgId);
//    }
//
//    @Test
//    public void delete(String... userId) throws SysResourcesException {}
//
//    @Test
//    public void deleteUserOrgRelationship(String userId, String orgId)
//        throws SysResourcesException {}
//
//    @Test
//    public void update(User user) throws SysResourcesException {}
//
//    @Test
//    public void setMainOrg(String[] userIds, String orgId, boolean isRetain)
//        throws SysResourcesException {}
//
//    @Test
//    public List<User> queryByOrg(User user, String orgId, boolean cascade) {}
//
//    @Test
//    public List<User> queryByRole(User user, String roleId) {}
//
//    @Test
//    public List<Orgnization> getOrgs(String userId) {}
//
//    @Test
//    public Orgnization getMainOrg(String userId) {}
//
//    @Test
//    public boolean inOrg(String userId, String orgId) {}
//
//    @Test
//    public boolean isMainOrg(String userId, String orgId) {}
//
//    @Test
//    public List<User> query(User user) {}
//
//    @Test
//    public User get(User user) {}
//
//    @Test
//    public List<User> query(String... userIds) {}
//
//    @Test
//    public List<Role> getRoles(String userId) {}
//
//    @Test
//    public boolean hasRole(String userId, String roleId) {}
//}
