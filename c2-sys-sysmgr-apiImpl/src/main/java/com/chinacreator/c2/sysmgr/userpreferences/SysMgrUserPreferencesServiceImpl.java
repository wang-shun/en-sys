package com.chinacreator.c2.sysmgr.userpreferences;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.basic.userpreferences.dto.UserPreferencesDTO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.c2.sysmgr.AuthenticationProvider;
import com.chinacreator.c2.sysmgr.Subject;

public class SysMgrUserPreferencesServiceImpl implements UserPreferencesService {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private com.chinacreator.asp.comp.sys.basic.userpreferences.service.UserPreferencesService userPreferencesService;

	@Override
	public void setUserPreferences(UserPreferences userPreferences) {
		String userId = getUserID();
		if (null != userId && !userId.trim().equals("")) {
			UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
			userPreferencesDTO.setUserId(userId);
			BeanCopierUtil.copy(userPreferences, userPreferencesDTO);

			userPreferencesService.set(userPreferencesDTO);
		}
	}

	@Override
	public void deleteUserPreferences(String infoName) {
		if (null != infoName && !infoName.trim().equals("")) {
			String userId = getUserID();
			if (null != userId && !userId.trim().equals("")) {
				userPreferencesService.deleteByPKs(new String[] { userId },
						new String[] { infoName });
			}
		}
	}

	@Override
	public UserPreferences getUserPreferences(String infoName) {
		UserPreferences userPreferences = new UserPreferences();
		if (null != infoName && !infoName.trim().equals("")) {
			String userId = getUserID();
			if (null != userId && !userId.trim().equals("")) {
				UserPreferencesDTO userPreferencesDTO = userPreferencesService
						.queryByPK(userId, infoName);
				if (null != userPreferencesDTO) {
					BeanCopierUtil.copy(userPreferencesDTO, userPreferences);
				}
			}
		}
		return userPreferences;
	}

	@Override
	public List<UserPreferences> getAllUserPreferences() {
		List<UserPreferences> list = new ArrayList<UserPreferences>();
		String userId = getUserID();
		if (null != userId && !userId.trim().equals("")) {
			UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
			userPreferencesDTO.setUserId(userId);
			List<UserPreferencesDTO> dtoList = userPreferencesService
					.queryByUserPreferences(userPreferencesDTO);
			if (null != dtoList && !dtoList.isEmpty()) {
				BeanCopierUtil.copy(dtoList, list, UserPreferencesDTO.class,
						UserPreferences.class);
			}
		}
		return list;
	}

	private String getUserID() {
		Subject subject = authenticationProvider.getSubject();
		if (null != subject) {
			return subject.getId();
		}
		return null;
	}
}
