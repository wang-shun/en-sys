package com.chinacreator.c2.sys.sdk.service.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.chinacreator.c2.sys.sdk.Utils;
import com.chinacreator.c2.sys.sdk.bean.OrgUserModel;

import junit.C2JunitTests;

public class UtilsTest extends C2JunitTests {
    @Autowired
    Utils utils;
    @Test
    public void getOrgAndUser() {
         List<? extends OrgUserModel> orgAndUser = utils.getOrgAndUser(null);
         System.err.println(JSON.toJSONString(orgAndUser));
         Assert.assertTrue(orgAndUser.size()>0);
    }
}
