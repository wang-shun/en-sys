package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import junit.C2JunitTests;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.c2.sys.sdk.bean.Role;
import com.chinacreator.c2.sys.sdk.service.RoleService;


public class RoleServiceTest extends C2JunitTests {
    @Autowired
    private RoleService roleSevice;
    @Test
    public void query() {
        List<Role> query = roleSevice.query(null);
        Assert.assertTrue(query.size()>0);
    }
}
