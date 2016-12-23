package com.chinacreator.asp.comp.sys.basic.dict.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.basic.DictMessages;
import com.chinacreator.asp.comp.sys.basic.dict.dao.DictDataDao;
import com.chinacreator.asp.comp.sys.basic.dict.dao.DictTypeDao;
import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.eo.DictDataEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 字典数据服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class DictDataServiceImpl implements DictDataService {

	@Autowired
	private DictDataDao dictDataDao;

	@Autowired
	private DictTypeDao dictTypeDao;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(DictDataDTO dictDataDTO) {
		if (null != dictDataDTO) {
			if (!isBlank(dictDataDTO.getDicttypeId())
					&& !isBlank(dictDataDTO.getDictdataName())) {
				// 字典类型要在字典类型表中有定义先
				if (dictTypeDao.existsByDictTypeId(dictDataDTO.getDicttypeId()) > 0) {
					dictDataDTO.setDictdataId(PKGenerator.generate());
					DictDataEO dictDataEO = new DictDataEO();
					BeanCopierUtil.copy(dictDataDTO, dictDataEO);
					// 验证字典类型下的字典名称不重复
					if (dictDataDao.existsByDictDataName(
							dictDataDTO.getDictdataName(),
							dictDataDTO.getDicttypeId()) == 0) {
						// 创建字典数据
						dictDataDao.create(dictDataEO);
					} else {
						throw new IllegalArgumentException(
								DictMessages
										.getString("DICTDATA.DICTTYPE_NAME_ALREADY_EXIST"));
					}
				} else {
					throw new IllegalArgumentException(
							DictMessages.getString("DICTDATA.DICTTYPE_ILLEGAL"));
				}
			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTDATA.DICTTYPE_ID_OR_DICTDATA_NAME_IS_NULL_EMPTY_BLANK"));
			}
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTDATA.DICTDATADTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(DictDataDTO dictDataDTO) {
		if (null != dictDataDTO) {
			String dictTypeId = dictDataDTO.getDicttypeId();
			String dictDataName = dictDataDTO.getDictdataName();
			String dictDataId = dictDataDTO.getDictdataId();
			String _dictTypeId = null;
			if (isBlank(dictDataId)) {
				throw new NullPointerException(
						DictMessages
								.getString("DICTDATA.DICTDATA_ID_IS_NULL_EMPTY_BLANK"));
			}
			if (null != dictTypeId) {
				if ("".equals(dictTypeId.trim())) {
					throw new NullPointerException(
							DictMessages
									.getString("DICTDATA.DICTTYPE_ID_IS_EMPTY_BLANK"));
				}
				// 判断字典类型的存在性，并赋值给_dictTypeId方便后续与字典项名称综合查询保证字典类型下字典名称的唯一性
				if (dictTypeDao.existsByDictTypeId(dictTypeId) > 0) {
					_dictTypeId = dictTypeId;
				} else {
					throw new IllegalArgumentException(
							DictMessages
									.getString("DICTDATA.DICTTYPE_NOT_EXIST"));
				}
			} else {// 字典类型为空，不更新字典类型字段
					// 字典类型没传，通过字典数据ID先查询到字典类型
				DictDataEO eo = dictDataDao.queryByPK(dictDataId);
				_dictTypeId = eo.getDicttypeId();
			}
			if (null != dictDataName) {
				if ("".equals(dictDataName.trim())) {
					throw new NullPointerException(
							DictMessages
									.getString("DICTDATA.DICTDATA_NAME_IS_EMPTY_BLANK"));
				}
				if (dictDataDao.existsByDictDataNameIgnoreDictDataID(
						dictDataName, _dictTypeId, dictDataId) > 0) {
					throw new IllegalArgumentException(
							DictMessages
									.getString("DICTDATA.DICTTYPE_NAME_ALREADY_EXISTED"));
				}
			}
			DictDataEO dictDataEO = new DictDataEO();
			BeanCopierUtil.copy(dictDataDTO, dictDataEO);
			// 创建字典数据
			dictDataDao.update(dictDataEO);
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTDATA.DICTDATADTO_IS_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... dictDataIds) {
		// 传参为null
		if (null != dictDataIds) {
			// 不传参
			if (dictDataIds.length > 0) {
				// 数组中含有为Null,空或空格的元素
				for (String dictDataId : dictDataIds) {
					if (isBlank(dictDataId)) {
						throw new NullPointerException(
								DictMessages
										.getString("DICTDATA.DICT_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					}
				}
				dictDataDao.deleteByPKs(dictDataIds);
			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTDATA.DICT_ID_ARRAY_CANT_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICT_ID_ARRAY_CANT_BE_NULL"));
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByDictTypeIds(String... dictTypeIds) {
		// 传参为null
		if (null != dictTypeIds) {
			// 不传参
			if (dictTypeIds.length > 0) {
				// 数组中含有为Null,空或空格的元素
				for (String dictTypeId : dictTypeIds) {
					if (isBlank(dictTypeId)) {
						throw new NullPointerException(
								DictMessages
										.getString("DICTDATA.DICT_TYPE_ID_ARRAY_HAS_NULL_EMPTY_BLANK_ITEM"));
					}
				}
				dictDataDao.deleteByDictTypeIds(dictTypeIds);
			} else {
				throw new NullPointerException(
						DictMessages
								.getString("DICTDATA.DICT_TYPE_ID_ARRAY_HAS_NO_ITEM"));
			}
		} else {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICT_TYPE_ID_ARRAY_IS_NULL"));
		}

	}

	public List<DictDataDTO> queryByDictData(DictDataDTO dictDataDTO) {
		if (null != dictDataDTO) {
			DictDataEO dictDataEO = new DictDataEO();
			BeanCopierUtil.copy(dictDataDTO, dictDataEO);
			List<DictDataEO> dictDataEOList = dictDataDao
					.queryByDictData(dictDataEO);
			List<DictDataDTO> dictDataDTOList = new ArrayList<DictDataDTO>();
			if (null != dictDataEOList && dictDataEOList.size() > 0) {
				BeanCopierUtil.copy(dictDataEOList, dictDataDTOList,
						DictDataEO.class, DictDataDTO.class);
			}
			return dictDataDTOList;
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTDATA.DICTDATADTO_IS_NULL"));
		}
	}

	public Page<DictDataDTO> queryByDictData(DictDataDTO dictDataDTO,
			Pageable pageable, Sortable sortable) {
		if (null != dictDataDTO) {
			DictDataEO dictDataEO = new DictDataEO();
			BeanCopierUtil.copy(dictDataDTO, dictDataEO);

			// 查询字典类型信息
			Page<DictDataEO> dictDataEOPage = dictDataDao.queryByDictData(
					dictDataEO, pageable, sortable);
			Page<DictDataDTO> dictDataDTOPage = new Page<DictDataDTO>(
					pageable.getPageIndex(), pageable.getPageSize(), 0,
					new ArrayList<DictDataDTO>());
			if (null != dictDataEOPage && dictDataEOPage.getTotal() > 0) {
				dictDataDTOPage = BeanCopierUtil.copyPage(dictDataEOPage,
						DictDataEO.class, DictDataDTO.class);
			}
			return dictDataDTOPage;
		} else {
			throw new NullPointerException(
					DictMessages.getString("DICTDATA.DICTDATADTO_IS_NULL"));
		}
	}

	public List<DictDataDTO> queryByDictTypeName(String dictTypeName) {
		List<DictDataDTO> dictDataDTOs = new ArrayList<DictDataDTO>();
		if (!isBlank(dictTypeName)) {
			List<DictDataEO> dictDataEOs = dictDataDao
					.queryByDictTypeName(dictTypeName);
			if (null != dictDataEOs && !dictDataEOs.isEmpty()) {
				BeanCopierUtil.copy(dictDataEOs, dictDataDTOs,
						DictDataEO.class, DictDataDTO.class);
			}
		}
		return dictDataDTOs;
	}

	public DictDataDTO queryByPK(String dictDataId) {
		if (!isBlank(dictDataId)) {
			DictDataEO dictDataEO = dictDataDao.queryByPK(dictDataId);
			DictDataDTO dictDataDTO = null;
			if (null != dictDataEO) {
				dictDataDTO = new DictDataDTO();
				BeanCopierUtil.copy(dictDataEO, dictDataDTO);
			}
			return dictDataDTO;
		} else {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICT_ID_IS_NULL_EMPTY_BLANK"));
		}
	}

	public boolean existsByDictDataName(String dictTypeId, String dictDataName) {
		if (isBlank(dictDataName)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICTDATA_NAME_IS_EMPTY_BLANK"));
		}
		if (isBlank(dictTypeId)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICTTYPE_ID_IS_EMPTY_BLANK"));
		}
		return dictDataDao.existsByDictDataName(dictDataName, dictTypeId) > 0;
	}

	public boolean existsByDictDataNameIgnoreDictDataID(String dictDataName,
			String dictTypeId, String dictDataId) {
		if (isBlank(dictDataName)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICTDATA_NAME_IS_EMPTY_BLANK"));
		}
		if (isBlank(dictTypeId)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICTTYPE_ID_IS_EMPTY_BLANK"));
		}
		if (isBlank(dictDataId)) {
			throw new NullPointerException(
					DictMessages
							.getString("DICTDATA.DICTDATA_ID_IS_NULL_EMPTY_BLANK"));
		}
		return dictDataDao.existsByDictDataNameIgnoreDictDataID(dictDataName,
				dictTypeId, dictDataId) > 0;
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
