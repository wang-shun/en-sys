package com.chinacreator.c2.sys.sdk.client;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrgnizationService;

@Service
public class OrgnizationServiceImpl implements OrgnizationService {

	@Override
	public Organization get(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> getChildren(String orgId, boolean cascade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Organization> getParents(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsUser(String orgId, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
