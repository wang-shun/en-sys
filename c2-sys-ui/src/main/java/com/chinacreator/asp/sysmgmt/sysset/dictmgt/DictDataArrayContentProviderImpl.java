package com.chinacreator.asp.sysmgmt.sysset.dictmgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictDataDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictDataService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class DictDataArrayContentProviderImpl implements ArrayContentProvider {

	private DictDataService dictDataService = ApplicationContextManager
			.getContext().getBean(DictDataService.class);
	
	@Override
	public Page<DictDataDTO> getElements(ArrayContext context) {
		Page<DictDataDTO> page = new Page<DictDataDTO>(new Pageable(),
				new ArrayList<DictDataDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String dicttypeId = (String) map.get("dtypeId");
				if (null != dicttypeId && !dicttypeId.trim().equals("")
						&& !dicttypeId.trim().equals("0")) {
					DictDataDTO dictDataDTO = new DictDataDTO();
					try {
						BeanUtils.populate(dictDataDTO, map);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						return page;
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						return page;
					} catch (Exception e) {
						e.printStackTrace();
						return page;
					}
					dictDataDTO.setDicttypeId(dicttypeId);
					page = dictDataService.queryByDictData(dictDataDTO,
							context.getPageable(), context.getSortable());
				}
			}
		}
		return page;
	}

}
