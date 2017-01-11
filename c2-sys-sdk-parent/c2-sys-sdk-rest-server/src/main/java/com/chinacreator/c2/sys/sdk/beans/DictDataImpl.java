package com.chinacreator.c2.sys.sdk.beans;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.c2.sysmgr.dict.DictData;

/**
 * 字典数据传输对象类
 */
@ApiModel(value = "字典数据传输对象")
public class DictDataImpl extends DictDataDTO implements DictData,Serializable{
	private static final long serialVersionUID = 1L;
}