package com.chinacreator.asp.comp.sys.basic.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.RoleMessages;
import com.chinacreator.asp.comp.sys.basic.role.dao.RoleTypeDao;
import com.chinacreator.asp.comp.sys.basic.role.dto.RoleTypeDTO;
import com.chinacreator.asp.comp.sys.basic.role.eo.RoleTypeEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.role.dao.RoleDao;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 角色类型服务接口实现类
 * 
 * @author 杨祎程
 * 
 */
@Service
public class RoleTypeServiceImpl implements RoleTypeService {

	@Autowired
	private RoleTypeDao roleTypeDao;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.basic.role.service.RoleServiceImpl")
	private RoleService roleService;

	@Autowired
	private RoleDao roleDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(RoleTypeDTO roleTypeDTO) {
		if (null != roleTypeDTO) {
			if (!isBlank(roleTypeDTO.getTypeName())) {
				if (roleTypeDao.existsByRoleTypeName(roleTypeDTO.getTypeName()) == 0) {
					roleTypeDTO.setTypeId(PKGenerator.generate());
					RoleTypeEO roleTypeEO = new RoleTypeEO();
					BeanCopierUtil.copy(roleTypeDTO, roleTypeEO);
					roleTypeDao.create(roleTypeEO);
				} else {
					throw new IllegalArgumentException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_EXIST"));
				}
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPEDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(RoleTypeDTO roleTypeDTO) {
		if (null != roleTypeDTO) {
			if (!isBlank(roleTypeDTO.getTypeId())) {

				if (null != roleTypeDTO.getTypeName()) {
					if ("".equals(roleTypeDTO.getTypeName().trim())) {
						throw new IllegalArgumentException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_EMPTY_BLANK"));
					}
					if (roleTypeDao.existsByRoleTypeNameIgnoreRoleTypeID(roleTypeDTO.getTypeName(),
							roleTypeDTO.getTypeId()) > 0) {
						throw new IllegalArgumentException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_EXIST"));
					}
				}
				RoleTypeEO roleTypeEO = new RoleTypeEO();
				BeanCopierUtil.copy(roleTypeDTO, roleTypeEO);
				roleTypeDao.update(roleTypeEO);

			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_ID_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPEDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... roleTypeIds) {
		if (null != roleTypeIds) {
			if (roleTypeIds.length > 0) {
				for (String roleTypeId : roleTypeIds) {
					if (isBlank(roleTypeId)) {
						throw new NullPointerException(
								RoleMessages.getString("ROLE.MENUID_ARRAY_CONTAINS_NULL_EMPTY_BLANK_ITEM"));
					}
				}
				// 查询出所有角色类型下的角色ID，然后进行按角色ID批量删除角色及其关系的调用
				List<String> roleIdList = roleDao.queryRoleIdsByRoleTypeIds(roleTypeIds);
				if (null != roleIdList && roleIdList.size() > 0) {
					String[] roleIds = roleIdList.toArray(new String[roleIdList.size()]);
					roleService.deleteByPKs(roleIds);
				}
				roleTypeDao.deleteByPKs(roleTypeIds);
			} else {
				throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_CONTAINS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_IS_NULL"));
		}

	}

	public List<RoleTypeDTO> queryAll() {
		List<RoleTypeEO> roleTypeEOList = roleTypeDao.queryAll();
		List<RoleTypeDTO> roleTypeDTOList = new ArrayList<RoleTypeDTO>();
		if (null != roleTypeEOList && roleTypeEOList.size() > 0) {
			BeanCopierUtil.copy(roleTypeEOList, roleTypeDTOList, RoleTypeEO.class, RoleTypeDTO.class);
		}
		return roleTypeDTOList;
	}

	public Page<RoleTypeDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<RoleTypeEO> roleTypeEOPage = roleTypeDao.queryAll(pageable, sortable);
		Page<RoleTypeDTO> roleTypeDTOPage = new Page<RoleTypeDTO>(pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<RoleTypeDTO>());
		if (null != roleTypeEOPage && roleTypeEOPage.getTotal() > 0) {
			roleTypeDTOPage = BeanCopierUtil.copyPage(roleTypeEOPage, RoleTypeEO.class, RoleTypeDTO.class);
		}
		return roleTypeDTOPage;
	}

	public List<RoleTypeDTO> queryByRoleType(RoleTypeDTO roleTypeDTO) {
		if (null != roleTypeDTO) {
			RoleTypeEO roleTypeEO = new RoleTypeEO();
			BeanCopierUtil.copy(roleTypeDTO, roleTypeEO);
			List<RoleTypeEO> roleTypeEOList = roleTypeDao.queryByRoleType(roleTypeEO);
			List<RoleTypeDTO> roleTypeDTOList = new ArrayList<RoleTypeDTO>();
			if (null != roleTypeEOList && roleTypeEOList.size() > 0) {
				BeanCopierUtil.copy(roleTypeEOList, roleTypeDTOList, RoleTypeEO.class, RoleTypeDTO.class);
			}
			return roleTypeDTOList;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPEDTO_IS_NULL"));
		}
	}

	public Page<RoleTypeDTO> queryByRoleType(RoleTypeDTO roleTypeDTO, Pageable pageable, Sortable sortable) {
		if (null != roleTypeDTO) {
			RoleTypeEO roleTypeEO = new RoleTypeEO();
			BeanCopierUtil.copy(roleTypeDTO, roleTypeEO);
			Page<RoleTypeEO> roleTypeEOPage = roleTypeDao.queryByRoleType(roleTypeEO, pageable, sortable);
			Page<RoleTypeDTO> roleTypeDTOPage = new Page<RoleTypeDTO>(pageable.getPageIndex(), pageable.getPageSize(),
					0, new ArrayList<RoleTypeDTO>());
			if (null != roleTypeEOPage && roleTypeEOPage.getTotal() > 0) {
				roleTypeDTOPage = BeanCopierUtil.copyPage(roleTypeEOPage, RoleTypeEO.class, RoleTypeDTO.class);
			}
			return roleTypeDTOPage;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPEDTO_IS_NULL"));
		}
	}

	public RoleTypeDTO queryByPK(String roleTypeId) {
		if (!isBlank(roleTypeId)) {
			RoleTypeEO roleTypeEO = roleTypeDao.queryByPK(roleTypeId);
			RoleTypeDTO roleTypeDTO = null;
			if (null != roleTypeEO) {
				roleTypeDTO = new RoleTypeDTO();
				BeanCopierUtil.copy(roleTypeEO, roleTypeDTO);
			}
			return roleTypeDTO;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByRoleTypeName(String roleTypeName) {
		if (!isBlank(roleTypeName)) {
			return roleTypeDao.existsByRoleTypeName(roleTypeName) > 0;
		} else {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByRoleTypeNameIgnoreRoleTypeId(String roleTypeName, String roleTypeId) {
		if (isBlank(roleTypeId)) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
		if (isBlank(roleTypeName)) {
			throw new NullPointerException(RoleMessages.getString("ROLE.ROLETYPE_NAME_IS_NULL_EMPTY_BLANK"));
		}
		return roleTypeDao.existsByRoleTypeNameIgnoreRoleTypeID(roleTypeName, roleTypeId) > 0;
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

	public String getAnonymousRoleTypeId() {
		return CommonPropertiesUtil.getAnonymousRoleTypeId();
	}
}
