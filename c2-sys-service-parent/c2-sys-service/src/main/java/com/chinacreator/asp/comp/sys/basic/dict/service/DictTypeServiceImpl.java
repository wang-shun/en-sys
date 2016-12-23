package com.chinacreator.asp.comp.sys.basic.dict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.DictMessages;
import com.chinacreator.asp.comp.sys.basic.dict.dao.DictDataDao;
import com.chinacreator.asp.comp.sys.basic.dict.dao.DictTypeDao;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictTypeDTO;
import com.chinacreator.asp.comp.sys.basic.dict.eo.DictTypeEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 字典类型服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

	@Autowired
	private DictTypeDao dictTypeDao;

	@Autowired
	private DictDataDao dictDataDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(DictTypeDTO dictTypeDTO) {
		if (null != dictTypeDTO) {
			if (!isBlank(dictTypeDTO.getDicttypeName())) {

				// 已经存在的字典类型名称不能相同
				if (dictTypeDao.existsByDictTypeName(dictTypeDTO
						.getDicttypeName()) > 0) {
					// 已经存在相同名称的字典类型名称
					throw new IllegalArgumentException(
							DictMessages
									.getString("DICTTYPE.DICT_TYPE_NAME_ALREADY_EXIST"));
				} else {
					// 创建字典类型
					DictTypeEO dictTypeEO = new DictTypeEO();
					dictTypeDTO.setDicttypeId(PKGenerator.generate());
					BeanCopierUtil.copy(dictTypeDTO, dictTypeEO);
					dictTypeDao.create(dictTypeEO);
				}

			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTTYPE.DICT_TYPE_NAME_CANT_BE_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE.DICTTYPEDTO_IS_NULL"));
		}

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(DictTypeDTO dictTypeDTO) {
		if (null != dictTypeDTO) {
			if (!isBlank(dictTypeDTO.getDicttypeId())) {
				// 已经存在的字典类型名称不能相同
				if (dictTypeDao.existsByDictTypeNameIgnoreDictTypeID(
						dictTypeDTO.getDicttypeName(),
						dictTypeDTO.getDicttypeId()) > 0) {
					// 已经存在相同名称的字典类型名称
					throw new IllegalArgumentException(
							DictMessages
									.getString("DICTTYPE.DICT_TYPE_NAME_ALREADY_EXIST"));
				} else {
					// 创建字典类型
					DictTypeEO dictTypeEO = new DictTypeEO();
					BeanCopierUtil.copy(dictTypeDTO, dictTypeEO);
					dictTypeDao.update(dictTypeEO);
				}
			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTTYPE.DICT_TYPE_NAME_OR_DICT_TYPE_ID_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE.DICTTYPEDTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... dictTypeIds) {
		// 传参为null
		if (null != dictTypeIds) {
			// 不传参
			if (dictTypeIds.length > 0) {
				// 数组中含有为Null,空或空格的元素
				for (String dictTypeId : dictTypeIds) {
					if (isBlank(dictTypeId)) {
						throw new NullPointerException(
								DictMessages
										.getString("DICTTYPE.DICT_TYPE_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					}
				}
				dictDataDao.deleteByDictTypeIds(dictTypeIds);
				dictTypeDao.deleteByPKs(dictTypeIds);
			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTTYPE.DICT_TYPE_ID_ARRAY_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					DictMessages
							.getString("DICTTYPE.DICT_TYPE_ID_ARRAY_IS_NULL"));
		}
	}

	public List<DictTypeDTO> queryAll() {
		List<DictTypeEO> dictTypeEOList = dictTypeDao.queryAll();
		List<DictTypeDTO> dictTypeDTOList = new ArrayList<DictTypeDTO>();
		if (null != dictTypeEOList && dictTypeEOList.size() > 0) {
			BeanCopierUtil.copy(dictTypeEOList, dictTypeDTOList,
					DictTypeEO.class, DictTypeDTO.class);
		}
		return dictTypeDTOList;
	}

	public Page<DictTypeDTO> queryAll(Pageable pageable, Sortable sortable) {
		Page<DictTypeEO> dictTypeEOPage = dictTypeDao.queryAll(pageable,
				sortable);
		Page<DictTypeDTO> dictTypeDTOPage = new Page<DictTypeDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<DictTypeDTO>());
		if (null != dictTypeEOPage && dictTypeEOPage.getTotal() > 0) {
			dictTypeDTOPage = BeanCopierUtil.copyPage(dictTypeEOPage,
					DictTypeEO.class, DictTypeDTO.class);
		}
		return dictTypeDTOPage;
	}

	public List<DictTypeDTO> queryByDictType(DictTypeDTO dictTypeDTO) {
		if (null != dictTypeDTO) {
			DictTypeEO dictTypeEO = new DictTypeEO();
			BeanCopierUtil.copy(dictTypeDTO, dictTypeEO);
			List<DictTypeEO> dictTypeEOList = dictTypeDao
					.queryByDictType(dictTypeEO);
			List<DictTypeDTO> dictTypeDTOList = new ArrayList<DictTypeDTO>();
			if (null != dictTypeEOList && dictTypeEOList.size() > 0) {
				BeanCopierUtil.copy(dictTypeEOList, dictTypeDTOList,
						DictTypeEO.class, DictTypeDTO.class);
			}
			return dictTypeDTOList;
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE.DICTTYPEDTO_IS_NULL"));
		}
	}

	public Page<DictTypeDTO> queryByDictType(DictTypeDTO dictTypeDTO,
			Pageable pageable, Sortable sortable) {
		if (null != dictTypeDTO) {
			DictTypeEO dictTypeEO = new DictTypeEO();
			BeanCopierUtil.copy(dictTypeDTO, dictTypeEO);

			// 查询字典类型信息
			Page<DictTypeEO> dictTypeEOPage = dictTypeDao.queryByDictType(
					dictTypeEO, pageable, sortable);
			Page<DictTypeDTO> dictTypeDTOPage = new Page<DictTypeDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<DictTypeDTO>());
			if (null != dictTypeEOPage && dictTypeEOPage.getTotal() > 0) {
				dictTypeDTOPage = BeanCopierUtil.copyPage(dictTypeEOPage,
						DictTypeEO.class, DictTypeDTO.class);
			}
			return dictTypeDTOPage;
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE.DICTTYPEDTO_IS_NULL"));
		}
	}

	public DictTypeDTO queryByPK(String dictTypeId) {
		if (!isBlank(dictTypeId)) {
			DictTypeEO dictTypeEO = dictTypeDao.queryByPK(dictTypeId);
			DictTypeDTO dictTypeDTO = null;
			if (null != dictTypeEO) {
				dictTypeDTO = new DictTypeDTO();
				BeanCopierUtil.copy(dictTypeEO, dictTypeDTO);
			}
			return dictTypeDTO;

		} else {
			throw new NullPointerException(
					DictMessages
							.getString("DICTTYPE.DICT_TYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByDictTypeName(String dictTypeName) {
		if (!isBlank(dictTypeName)) {
			return dictTypeDao.existsByDictTypeName(dictTypeName) > 0;
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByDictTypeNameIgnoreDictTypeID(String dictTypeName,
			String dictTypeId) {
		if (isBlank(dictTypeName)) {
			throw new NullPointerException(
					DictMessages.getString("DICTTYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
		if (isBlank(dictTypeId)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTTYPE.DICT_TYPE_ID_IS_NULL_EMPTY_BLANK"));
		}
		return dictTypeDao.existsByDictTypeNameIgnoreDictTypeID(dictTypeName,
				dictTypeId) > 0;
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
