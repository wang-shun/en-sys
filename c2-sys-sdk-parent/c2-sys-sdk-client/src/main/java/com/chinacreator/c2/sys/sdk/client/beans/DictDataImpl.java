package com.chinacreator.c2.sys.sdk.client.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.chinacreator.c2.sysmgr.dict.DictData;

/**
 * 字典数据传输对象类
 */
@ApiModel(value = "字典数据传输对象")
public class DictDataImpl implements DictData,Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典ID
	 */
	@ApiModelProperty(value = "字典ID")
	private String dictdataId;
	
	/** 
	 * 字典类型ID 
	 */
	@ApiModelProperty(value = "字典类型ID ")
	private String dicttypeId;
	
	/** 
	 * 字典真实值 
	 */
	@ApiModelProperty(value = "字典真实值")
	private String dictdataName;
	
	/** 
	 * 字典显示值 
	 */
	@ApiModelProperty(value = "字典显示值")
	private String dictdataValue;
	
	/** 
	 * 字典排序号 
	 */
	@ApiModelProperty(value = "字典排序号")
	private Integer dictdataOrder;
	
	/** 
	 * 是否默认值（0：否，1：是） 
	 */
	@ApiModelProperty(value = "字典默认值（0：否，1：是）")
	private Boolean dictdataIsdefault;
	
	@Override
	public Boolean getDictdataIsdefault() {
		return dictdataIsdefault;
	}

	@Override
	public String getDictdataName() {
		return dictdataName;
	}

	@Override
	public String getDictdataValue() {
		return dictdataValue;
	}
	
	public String getDictdataId() {
		return dictdataId;
	}

	public void setDictdataId(String dictdataId) {
		this.dictdataId = dictdataId;
	}

	public String getDicttypeId() {
		return dicttypeId;
	}

	public void setDicttypeId(String dicttypeId) {
		this.dicttypeId = dicttypeId;
	}

	public void setDictdataName(String dictdataName) {
		this.dictdataName = dictdataName;
	}

	public void setDictdataValue(String dictdataValue) {
		this.dictdataValue = dictdataValue;
	}

	public Integer getDictdataOrder() {
		return dictdataOrder;
	}

	public void setDictdataOrder(Integer dictdataOrder) {
		this.dictdataOrder = dictdataOrder;
	}

	public void setDictdataIsdefault(Boolean dictdataIsdefault) {
		this.dictdataIsdefault = dictdataIsdefault;
	}

}
