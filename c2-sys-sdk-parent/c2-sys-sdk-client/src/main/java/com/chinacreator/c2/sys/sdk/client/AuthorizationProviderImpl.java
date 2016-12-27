package com.chinacreator.c2.sys.sdk.client;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.sysmgr.AuthorizationProvider;
import com.chinacreator.c2.sysmgr.exception.AuthenticationException;

@Service
public class AuthorizationProviderImpl implements AuthorizationProvider {

	@Override
	public boolean isAllPermitted(String... resource)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermitted(String resource) throws AuthenticationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] isPermitted(String... resource) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Boolean> isPermittedByBatch(String... resource)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

}
