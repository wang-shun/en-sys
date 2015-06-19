package com.chinacreator.asp.comp.sys.basic.sysinfo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.SysInfoMessages;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dao.SysRTSettingDao;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dao.SysRTSettingTypeDao;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemInfoDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.eo.SystemInfoEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 系统运行时设置服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class SysRTSettingServiceImpl implements SysRTSettingService {

	@Autowired
	private SysRTSettingDao sysRTSettingDao;

	@Autowired
	private SysRTSettingTypeDao sysRTSettingTypeDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(SystemInfoDTO systemInfoDTO) {
		if (null != systemInfoDTO) {
			if (!isBlank(systemInfoDTO.getInfoType())
					&& !isBlank(systemInfoDTO.getInfoName())) {
				// 系统信息类型要在系统信息类型表中有定义先
				if (sysRTSettingTypeDao.existsByTypeId(systemInfoDTO
						.getInfoType()) > 0) {
					systemInfoDTO.setId(PKGenerator.generate());
					SystemInfoEO systemInfoEO = new SystemInfoEO();
					BeanCopierUtil.copy(systemInfoDTO, systemInfoEO);
					// 创建系统信息数据
					if (sysRTSettingDao.existsByInfoName(
							systemInfoDTO.getInfoType(),
							systemInfoDTO.getInfoName()) == 0) {
						// 系统类型下不存在同名系统，创建
						sysRTSettingDao.create(systemInfoEO);
					} else {
						throw new IllegalArgumentException(
								SysInfoMessages
										.getString("SYS.SYSTYPE_NAME_ALREADY_EXIST"));
					}
				} else {
					throw new IllegalArgumentException(
							SysInfoMessages
									.getString("SYS.SYSINFO_TYPE_ILLEGAL"));
				}
			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYS.SYSTYPENAME_OR_SYSINFO_NAME_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFODTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(SystemInfoDTO systemInfoDTO) {
		if (null != systemInfoDTO) {

			String infoType = systemInfoDTO.getInfoType();
			String infoName = systemInfoDTO.getInfoName();
			String id = systemInfoDTO.getId();
			String _infoType = null;

			if (isBlank(id)) {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYS.SYSINFOID_IS_NULL_EMPTY_BLANK"));
			}
			if (null != infoType) {
				if ("".equals(infoType.trim())) {
					throw new NullPointerException(
							SysInfoMessages
									.getString("SYS.SYSTYPE_IS_EMPTY_BLANK"));
				}
				if (sysRTSettingTypeDao.existsByTypeId(infoType) > 0) {
					_infoType = infoType;
				} else {
					throw new IllegalArgumentException(
							SysInfoMessages.getString("SYS.SYSTYPE_NOT_EXIST"));
				}

			} else {
				SystemInfoEO eo = sysRTSettingDao.queryByPK(id);
				_infoType = eo.getInfoType();
			}

			if (null != infoName) {
				if ("".equals(infoName.trim())) {
					throw new NullPointerException(
							SysInfoMessages
									.getString("SYS.SYSINFO_NAME_IS_EMPTY_BLANK"));
				}
				if (sysRTSettingDao.existsByInfoNameIgnoreID(_infoType,
						infoName, id) > 0) {
					throw new IllegalArgumentException(
							SysInfoMessages
									.getString("SYS.SYSTYPE_NAME_ALREADY_EXIST_IN_SYSTYPE"));
				}
			}

			SystemInfoEO systemInfoEO = new SystemInfoEO();
			BeanCopierUtil.copy(systemInfoDTO, systemInfoEO);

			sysRTSettingDao.update(systemInfoEO);

		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFODTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... ids) {
		if (null != ids) {
			if (ids.length > 0) {
				// 数组中含有为Null,空或空格的元素
				for (String id : ids) {
					if (isBlank(id)) {
						throw new NullPointerException(
								SysInfoMessages
										.getString("SYS.SYSINFO_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					} else if (isDeletable(id)) {
						throw new IllegalArgumentException(
								SysInfoMessages
										.getString("SYS.SYSINFO_CANNOT_DELETE_EMPTY"));
					}
				}
				sysRTSettingDao.deleteByPKs(ids);
			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYS.SYSINFO_ID_ARRAY_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFO_ID_ARRAY_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteBySystemTypeIds(String... systemTypeIds) {
		if (null != systemTypeIds) {
			if (systemTypeIds.length > 0) {
				Set<String> set = new HashSet<String>();
				for (String id : systemTypeIds) {
					if (isBlank(id)) {
						throw new NullPointerException(
								SysInfoMessages
										.getString("SYS.SYSINFO_TYPE_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					} else {
						SystemInfoEO systemInfoEO = new SystemInfoEO();
						systemInfoEO.setInfoType(id);
						List<SystemInfoEO> systemInfoEOList = sysRTSettingDao
								.queryBySystemInfo(systemInfoEO);
						for (SystemInfoEO sysInfoEO : systemInfoEOList) {
							set.add(sysInfoEO.getId());
						}
					}
				}
				if (!set.isEmpty()) {
					deleteByPKs(set.toArray(new String[set.size()]));
				}
			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYS.SYSINFO_TYPE_ID_ARRAY_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYS.SYSINFO_TYPE_ID_ARRAY_IS_NULL"));
		}

	}

	public List<SystemInfoDTO> queryAll() {
		List<SystemInfoEO> systemInfoEOList = sysRTSettingDao.queryAll();
		List<SystemInfoDTO> systemInfoDTOList = new ArrayList<SystemInfoDTO>();
		if (null != systemInfoEOList && systemInfoEOList.size() > 0) {
			BeanCopierUtil.copy(systemInfoEOList, systemInfoDTOList,
					SystemInfoEO.class, SystemInfoDTO.class);
		}
		return systemInfoDTOList;
	}

	public Page<SystemInfoDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<SystemInfoEO> systemInfoEOPage = sysRTSettingDao.queryAll(
				pageable, sortable);
		Page<SystemInfoDTO> systemInfoDTOPage = new Page<SystemInfoDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<SystemInfoDTO>());
		if (null != systemInfoEOPage && systemInfoEOPage.getTotal() > 0) {
			systemInfoDTOPage = BeanCopierUtil.copyPage(systemInfoEOPage,
					SystemInfoEO.class, SystemInfoDTO.class);
		}
		return systemInfoDTOPage;
	}

	public List<SystemInfoDTO> queryBySystemInfo(SystemInfoDTO systemInfoDTO) {
		if (null != systemInfoDTO) {
			SystemInfoEO systemInfoEO = new SystemInfoEO();
			BeanCopierUtil.copy(systemInfoDTO, systemInfoEO);
			List<SystemInfoEO> systemInfoEOList = sysRTSettingDao
					.queryBySystemInfo(systemInfoEO);
			List<SystemInfoDTO> systemInfoDTOList = new ArrayList<SystemInfoDTO>();
			if (null != systemInfoEOList && systemInfoEOList.size() > 0) {
				BeanCopierUtil.copy(systemInfoEOList, systemInfoDTOList,
						SystemInfoEO.class, SystemInfoDTO.class);
			}
			return systemInfoDTOList;
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFODTO_IS_NULL"));
		}
	}

	public Page<SystemInfoDTO> queryBySystemInfo(SystemInfoDTO systemInfoDTO,
			Pageable pageable, Sortable sortable) {
		if (null != systemInfoDTO) {
			SystemInfoEO systemInfoEO = new SystemInfoEO();
			BeanCopierUtil.copy(systemInfoDTO, systemInfoEO);

			// 查询字典类型信息
			Page<SystemInfoEO> systemInfoEOPage = sysRTSettingDao
					.queryBySystemInfo(systemInfoEO, pageable, sortable);
			Page<SystemInfoDTO> systemInfoDTOPage = new Page<SystemInfoDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<SystemInfoDTO>());
			if (null != systemInfoEOPage && systemInfoEOPage.getTotal() > 0) {
				systemInfoDTOPage = BeanCopierUtil.copyPage(systemInfoEOPage,
						SystemInfoEO.class, SystemInfoDTO.class);
			}
			return systemInfoDTOPage;
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFODTO_IS_NULL"));
		}
	}

	public SystemInfoDTO queryByPK(String id) {
		if (!isBlank(id)) {
			SystemInfoEO systemInfoEO = sysRTSettingDao.queryByPK(id);
			SystemInfoDTO systemInfoDTO = null;
			if (null != systemInfoEO) {
				systemInfoDTO = new SystemInfoDTO();
				BeanCopierUtil.copy(systemInfoEO, systemInfoDTO);
			}
			return systemInfoDTO;

		} else {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYS.SYSINFO_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsBySystemInfoName(String systemTypeId,
			String systemInfoName) {
		if (isBlank(systemTypeId)) {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFO_ID_IS_NULL"));
		}
		if (isBlank(systemInfoName)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYS.SYSINFO_NAME_IS_EMPTY_BLANK"));
		}
		return sysRTSettingDao.existsByInfoName(systemTypeId, systemInfoName) > 0;
	}

	public boolean existsByInfoNameIgnoreID(String systemTypeId,
			String systemInfoName, String systemInfoId) {
		if (isBlank(systemTypeId)) {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFO_ID_IS_NULL"));
		}
		if (isBlank(systemInfoName)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYS.SYSINFO_NAME_IS_EMPTY_BLANK"));
		}
		if (isBlank(systemInfoId)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYS.SYSINFO_ID_IS_NULL_EMPTY_BLANK"));
		}
		return sysRTSettingDao.existsByInfoNameIgnoreID(systemTypeId,
				systemInfoName, systemInfoId) > 0;
	}

	public boolean isDeletable(String id) {
		if (!isBlank(id)) {
			SystemInfoEO systemInfoEO = sysRTSettingDao.queryByPK(id);
			if (null == systemInfoEO) {
				throw new IllegalArgumentException(
						SysInfoMessages.getString("SYS.SYSINFO_NOT_EXIST"));
			}
			return systemInfoEO.getCanremove();
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYS.SYSINFO_ID_IS_NULL"));
		}

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
}
