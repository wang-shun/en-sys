package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.sdk.service.query.UserService;

@Service("sdkUserService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
    private com.chinacreator.asp.comp.sys.advanced.user.service.UserService userService;
    @Autowired
    @Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
    private OrgService orgService;
    
	@Override
	public User current() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> queryMulti(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User getByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> queryMultiByUsername(String... username) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> queryByOrg(String orgId, boolean cascade) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> queryByRole(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Organization> getOrgs(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Organization getMainOrg(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean inOrg(String userId, String orgId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isMainOrg(String userId, String orgId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Role> getRoles(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasRole(String userId, String roleId) {
		// TODO Auto-generated method stub
		return false;
	}

}
