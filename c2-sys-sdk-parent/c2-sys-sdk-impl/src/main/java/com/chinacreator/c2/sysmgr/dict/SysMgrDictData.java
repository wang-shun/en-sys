package com.chinacreator.c2.sysmgr.dict;

import java.io.Serializable;

/**
 * 字典数据接口实现
 * 
 * @author 彭盛
 * 
 */
public class SysMgrDictData implements DictData, Serializable {

	private static final long serialVersionUID = 1L;

	/** 字典ID */
	private String dictdataId;
	/** 字典类型ID */
	private String dicttypeId;
	/** 字典真实值 */
	private String dictdataName;
	/** 字典显示值 */
	private String dictdataValue;
	/** 字典排序号 */
	private Integer dictdataOrder;
	/** 是否默认值（0：否，1：是） */
	private Boolean dictdataIsdefault;

	public String getDictdataId() {
		return dictdataId;
	}

	public void setDictdataId(String dictdataId) {
		this.dictdataId = null == dictdataId ? null : dictdataId.trim();
	}

	public String getDicttypeId() {
		return dicttypeId;
	}

	public void setDicttypeId(String dicttypeId) {
		this.dicttypeId = null == dicttypeId ? null : dicttypeId.trim();
	}

	public String getDictdataName() {
		return dictdataName;
	}

	public void setDictdataName(String dictdataName) {
		this.dictdataName = null == dictdataName ? null : dictdataName.trim();
	}

	public String getDictdataValue() {
		return dictdataValue;
	}

	public void setDictdataValue(String dictdataValue) {
		this.dictdataValue = null == dictdataValue ? null : dictdataValue
				.trim();
	}

	public Integer getDictdataOrder() {
		return dictdataOrder;
	}

	public void setDictdataOrder(Integer dictdataOrder) {
		this.dictdataOrder = dictdataOrder;
	}

	public Boolean getDictdataIsdefault() {
		return dictdataIsdefault;
	}

	public void setDictdataIsdefault(Boolean dictdataIsdefault) {
		this.dictdataIsdefault = null == dictdataIsdefault ? null
				: dictdataIsdefault;
	}

}
