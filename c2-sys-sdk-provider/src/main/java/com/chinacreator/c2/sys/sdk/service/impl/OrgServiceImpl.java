package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.List;

import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.service.OrgService;

public class OrgServiceImpl implements OrgService {
    
    @Override
    public void create(Orgnization orgDTO) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void update(Orgnization orgDTO) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void delete(String... orgIds) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public List<Orgnization> query(Orgnization orgDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Orgnization get(String orgId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Orgnization> getParents(String orgId) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Orgnization> getChildrens(String orgId, boolean cascade) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean containsUser(String orgId, String userId) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean hasRole(String orgId, String roleId) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
