package com.chinacreator.asp.comp.sys.basic.userpreferences.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.basic.UserPreferencesMessages;
import com.chinacreator.asp.comp.sys.basic.userpreferences.dao.UserPreferencesDao;
import com.chinacreator.asp.comp.sys.basic.userpreferences.dto.UserPreferencesDTO;
import com.chinacreator.asp.comp.sys.basic.userpreferences.eo.UserPreferencesEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {

	@Autowired
	private UserPreferencesDao userPreferencesDao;

	public void create(UserPreferencesDTO userPreferencesDTO) {
		validateUserPreferencesDTO(userPreferencesDTO);

		if (userPreferencesDao.existsByInfoName(userPreferencesDTO.getUserId(),
				userPreferencesDTO.getInfoName()) > 0) {
			throw new IllegalArgumentException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_EXIST"));
		}

		userPreferencesDTO.setInfoLastupdate(new Timestamp(System
				.currentTimeMillis()));

		UserPreferencesEO userPreferencesEO = new UserPreferencesEO();
		BeanCopierUtil.copy(userPreferencesDTO, userPreferencesEO);

		userPreferencesDao.create(userPreferencesEO);
	}

	public void update(UserPreferencesDTO userPreferencesDTO) {
		validateUserPreferencesDTO(userPreferencesDTO);

		if (userPreferencesDao.existsByInfoName(userPreferencesDTO.getUserId(),
				userPreferencesDTO.getInfoName()) > 0) {
			userPreferencesDTO.setInfoLastupdate(new Timestamp(System
					.currentTimeMillis()));

			UserPreferencesEO userPreferencesEO = new UserPreferencesEO();
			BeanCopierUtil.copy(userPreferencesDTO, userPreferencesEO);

			userPreferencesDao.update(userPreferencesEO);
		} else {
			throw new IllegalArgumentException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NOT_EXIST"));
		}
	}

	public void set(UserPreferencesDTO userPreferencesDTO) {
		validateUserPreferencesDTO(userPreferencesDTO);

		userPreferencesDTO.setInfoLastupdate(new Timestamp(System
				.currentTimeMillis()));

		UserPreferencesEO userPreferencesEO = new UserPreferencesEO();
		BeanCopierUtil.copy(userPreferencesDTO, userPreferencesEO);

		if (userPreferencesDao.existsByInfoName(userPreferencesDTO.getUserId(),
				userPreferencesDTO.getInfoName()) > 0) {
			userPreferencesDao.update(userPreferencesEO);
		} else {
			userPreferencesDao.create(userPreferencesEO);
		}
	}

	public void deleteByPKs(String[] userIds, String[] infoNames) {
		if (isBlankByArrays(userIds)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERID_IS_NULL"));
		}

		if (isBlankByArrays(infoNames)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NULL"));
		}

		userPreferencesDao.deleteByPKs(userIds, infoNames);
	}

	public void deleteByUserIDs(String... userIds) {
		if (isBlankByArrays(userIds)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERID_IS_NULL"));
		}

		userPreferencesDao.deleteByUserIDs(userIds);
	}

	public void deleteByInfoNames(String... infoNames) {
		if (isBlankByArrays(infoNames)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NULL"));
		}

		userPreferencesDao.deleteByInfoNames(infoNames);
	}

	public List<UserPreferencesDTO> queryAll() {
		List<UserPreferencesDTO> upDTOList = new ArrayList<UserPreferencesDTO>();
		List<UserPreferencesEO> upEOList = userPreferencesDao.queryAll();
		if (null != upEOList && !upEOList.isEmpty()) {
			BeanCopierUtil.copy(upEOList, upDTOList, UserPreferencesEO.class,
					UserPreferencesDTO.class);
		}
		return upDTOList;
	}

	public List<UserPreferencesDTO> queryByUserPreferences(
			UserPreferencesDTO userPreferencesDTO) {
		List<UserPreferencesDTO> upDTOList = new ArrayList<UserPreferencesDTO>();
		if (null != userPreferencesDTO) {
			UserPreferencesEO userPreferencesEO = new UserPreferencesEO();
			BeanCopierUtil.copy(userPreferencesDTO, userPreferencesEO);

			List<UserPreferencesEO> upEOList = userPreferencesDao
					.queryByUserPreferences(userPreferencesEO);
			if (null != upEOList && !upEOList.isEmpty()) {
				BeanCopierUtil.copy(upEOList, upDTOList,
						UserPreferencesEO.class, UserPreferencesDTO.class);
			}
		}

		return upDTOList;
	}

	public UserPreferencesDTO queryByPK(String userId, String infoName) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERID_IS_NULL"));
		}

		if (isBlank(infoName)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NULL"));
		}

		UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO();
		UserPreferencesEO userPreferencesEO = userPreferencesDao.queryByPK(
				userId, infoName);
		if (null != userPreferencesEO) {
			BeanCopierUtil.copy(userPreferencesEO, userPreferencesDTO);
		}

		return userPreferencesDTO;
	}

	public boolean existsByInfoName(String userId, String infoName) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERID_IS_NULL"));
		}

		if (isBlank(infoName)) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NULL"));
		}
		return userPreferencesDao.existsByInfoName(userId, infoName) > 0;
	}

	/**
	 * 判断字符串是否为空或空格或null
	 * 
	 * @param str
	 *            被判断的字符串
	 * @return 字符串是否为null或者""或者全是空格 是：true 否：false
	 */
	private boolean isBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}

	/**
	 * 判断字符串数组是否为空或含有空格或null
	 * 
	 * @param arrays
	 *            被判断的字符串数组
	 * @return true：是，false：否
	 */
	private boolean isBlankByArrays(String[] arrays) {
		if (null == arrays) {
			return true;
		}
		for (String str : arrays) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验用户偏好设置信息数据传输对象类正确性
	 * 
	 * @param userPreferencesDTO
	 *            用户偏好设置信息数据传输对象类
	 */
	private void validateUserPreferencesDTO(
			UserPreferencesDTO userPreferencesDTO) {
		if (null == userPreferencesDTO) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERPREFERENCESDTO_IS_NULL"));
		}

		if (isBlank(userPreferencesDTO.getUserId())) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.USERID_IS_NULL"));
		}

		if (isBlank(userPreferencesDTO.getInfoName())) {
			throw new NullPointerException(
					UserPreferencesMessages
							.getString("USERPREFERENCES.INFONAME_IS_NULL"));
		}
	}
}
