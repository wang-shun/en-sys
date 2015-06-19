package com.chinacreator.asp.comp.sys.basic.sysinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.SysInfoMessages;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dao.SysRTSettingDao;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dao.SysRTSettingTypeDao;
import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemTypeDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.eo.SystemTypeEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 系统运行时设置类型服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class SysRTSettingTypeServiceImpl implements SysRTSettingTypeService {

	@Autowired
	private SysRTSettingTypeDao sysRTSettingTypeDao;

	@Autowired
	private SysRTSettingDao sysRTSettingDao;

	@Autowired
	private SysRTSettingService sysRTSettingService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(SystemTypeDTO systemTypeDTO) {
		if (null != systemTypeDTO) {
			if (!isBlank(systemTypeDTO.getTypeName())) {
				// 已经存在的系统信息类型名称不能相同
				int count = sysRTSettingTypeDao.existsByTypeName(systemTypeDTO
						.getTypeName());
				if (count > 0) {
					// 已经存在相同名称的系统信息类型名称
					throw new IllegalArgumentException(
							SysInfoMessages
									.getString("SYSTYPE.SYSTYPENAME_ALREADY_EXIST"));
				} else {
					SystemTypeEO systemTypeEO = new SystemTypeEO();
					systemTypeDTO.setId(PKGenerator.generate());
					BeanCopierUtil.copy(systemTypeDTO, systemTypeEO);
					sysRTSettingTypeDao.create(systemTypeEO);
				}

			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYSTYPE.SYSTYPENAME_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYSTYPE.SYSTYPEDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(SystemTypeDTO systemTypeDTO) {

		if (null != systemTypeDTO) {
			if (!isBlank(systemTypeDTO.getId())) {
				// 已经存在的系统信息类型名称不能相同
				if (null != systemTypeDTO.getTypeName()) {
					if ("".equals(systemTypeDTO.getTypeName().trim())) {
						throw new NullPointerException(
								SysInfoMessages
										.getString("SYSTYPE.SYSINFO_NAME_IS_EMPTY_BLANK"));
					}
					int count = sysRTSettingTypeDao.existsByTypeNameIgnoreID(
							systemTypeDTO.getTypeName(), systemTypeDTO.getId());
					if (count > 0) {
						// 已经存在相同名称的系统信息类型名称
						throw new IllegalArgumentException(
								SysInfoMessages
										.getString("SYSTYPE.SYSTYPENAME_ALREADY_EXIST"));
					}
				}
				// 修改系统信息类型
				SystemTypeEO systemTypeEO = new SystemTypeEO();
				BeanCopierUtil.copy(systemTypeDTO, systemTypeEO);
				sysRTSettingTypeDao.update(systemTypeEO);

			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYSTYPE.SYSTYPENAME_OR_SYSTYPEID_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYSTYPE.SYSTYPEDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... ids) {
		// 传参为null
		if (null != ids) {
			// 不传参
			if (ids.length > 0) {
				// 数组中含有为Null,空或空格的元素
				for (String id : ids) {
					if (isBlank(id)) {
						throw new NullPointerException(
								SysInfoMessages
										.getString("SYSTYPE.SYSTYPE_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					}
				}
				sysRTSettingService.deleteBySystemTypeIds(ids);

				sysRTSettingTypeDao.deleteByPKs(ids);
			} else {
				throw new NullPointerException(
						SysInfoMessages
								.getString("SYSTYPE.SYSTYPE_ID_ARRAY_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYSTYPE.SYSTYPE_ID_ARRAY_IS_NULL"));
		}

	}

	public List<SystemTypeDTO> queryAll() {
		List<SystemTypeEO> systemTypeEOList = sysRTSettingTypeDao.queryAll();
		List<SystemTypeDTO> systemTypeDTOList = new ArrayList<SystemTypeDTO>();
		if (null != systemTypeEOList && systemTypeEOList.size() > 0) {
			BeanCopierUtil.copy(systemTypeEOList, systemTypeDTOList,
					SystemTypeEO.class, SystemTypeDTO.class);
		}
		return systemTypeDTOList;
	}

	public Page<SystemTypeDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<SystemTypeEO> systemTypeEOPage = sysRTSettingTypeDao.queryAll(
				pageable, sortable);
		Page<SystemTypeDTO> systemTypeDTOPage = new Page<SystemTypeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<SystemTypeDTO>());
		if (null != systemTypeEOPage && systemTypeEOPage.getTotal() > 0) {
			systemTypeDTOPage = BeanCopierUtil.copyPage(systemTypeEOPage,
					SystemTypeEO.class, SystemTypeDTO.class);
		}
		return systemTypeDTOPage;
	}

	public List<SystemTypeDTO> queryBySystemType(SystemTypeDTO systemTypeDTO) {
		if (null != systemTypeDTO) {
			SystemTypeEO systemTypeEO = new SystemTypeEO();
			BeanCopierUtil.copy(systemTypeDTO, systemTypeEO);
			List<SystemTypeEO> systemTypeEOList = sysRTSettingTypeDao
					.queryBySystemType(systemTypeEO);
			List<SystemTypeDTO> systemTypeDTOList = new ArrayList<SystemTypeDTO>();
			if (null != systemTypeEOList && systemTypeEOList.size() > 0) {
				BeanCopierUtil.copy(systemTypeEOList, systemTypeDTOList,
						SystemTypeEO.class, SystemTypeDTO.class);
			}
			return systemTypeDTOList;
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYSTYPE.SYSTYPEDTO_IS_NULL"));
		}
	}

	public Page<SystemTypeDTO> queryBySystemType(SystemTypeDTO systemTypeDTO,
			Pageable pageable, Sortable sortable) {
		if (null != systemTypeDTO) {
			SystemTypeEO systemTypeEO = new SystemTypeEO();
			BeanCopierUtil.copy(systemTypeDTO, systemTypeEO);

			// 查询字典类型信息
			Page<SystemTypeEO> systemTypeEOPage = sysRTSettingTypeDao
					.queryBySystemType(systemTypeEO, pageable, sortable);
			Page<SystemTypeDTO> systemTypeDTOPage = new Page<SystemTypeDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<SystemTypeDTO>());
			if (null != systemTypeEOPage && systemTypeEOPage.getTotal() > 0) {
				systemTypeDTOPage = BeanCopierUtil.copyPage(systemTypeEOPage,
						SystemTypeEO.class, SystemTypeDTO.class);
			}
			return systemTypeDTOPage;
		} else {
			throw new NullPointerException(
					SysInfoMessages.getString("SYSTYPE.SYSTYPEDTO_IS_NULL"));
		}
	}

	public SystemTypeDTO queryByPK(String id) {
		if (!isBlank(id)) {
			SystemTypeEO systemTypeEO = sysRTSettingTypeDao.queryByPK(id);
			SystemTypeDTO systemTypeDTO = null;
			if (null != systemTypeEO) {
				systemTypeDTO = new SystemTypeDTO();
				BeanCopierUtil.copy(systemTypeEO, systemTypeDTO);
			}
			return systemTypeDTO;

		} else {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYSTYPE.SYSTYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByTypeName(String typeName) {
		if (isBlank(typeName)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYSTYPE.SYSINFO_NAME_IS_NULL_EMPTY_BLANK"));
		}
		return sysRTSettingTypeDao.existsByTypeName(typeName) > 0;
	}

	public boolean existsByTypeNameIgnoreID(String typeName, String id) {
		if (isBlank(typeName)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYSTYPE.SYSINFO_NAME_IS_NULL_EMPTY_BLANK"));
		}
		if (isBlank(id)) {
			throw new NullPointerException(
					SysInfoMessages
							.getString("SYSTYPE.SYSTYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
		return sysRTSettingTypeDao.existsByTypeNameIgnoreID(typeName, id) > 0;
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
