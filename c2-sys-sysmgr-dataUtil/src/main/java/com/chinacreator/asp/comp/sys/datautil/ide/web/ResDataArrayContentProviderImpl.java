package com.chinacreator.asp.comp.sys.datautil.ide.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.datautil.ide.manager.SynchroSysMgtUtil;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class ResDataArrayContentProviderImpl implements ArrayContentProvider {

	@Autowired
	private SynchroSysMgtUtil synchroSysMgtUtil = ApplicationContextManager
			.getContext().getBean(SynchroSysMgtUtil.class);

	private static List<PrivilegeEO> resList = null;

	@Override
	public Page<PrivilegeEO> getElements(ArrayContext context) {
		Pageable pageable = context.getPageable();
		int pageIndex = pageable.getPageIndex();
		if (1 == pageIndex) {
			init();
		}
		int pageSize = pageable.getPageSize();
		int offset = pageable.getOffset();
		int size = (offset + pageSize) > resList.size() ? resList.size()
				: (offset + pageSize);
		Page<PrivilegeEO> page = new Page<PrivilegeEO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<PrivilegeEO>());
		List<PrivilegeEO> outList = new ArrayList<PrivilegeEO>();
		if (null != resList && !resList.isEmpty()) {
			for (int i = offset; i < size; i++) {
				outList.add(resList.get(i));
			}
			page = new Page<PrivilegeEO>(pageable.getPageIndex(),
					pageable.getPageSize(), resList.size(), outList);
		}

		return page;
	}

	private void init() {
		resList = new ArrayList<PrivilegeEO>();
		List<PrivilegeEO> addPrivilegeList = new ArrayList<PrivilegeEO>();
		List<PrivilegeEO> updatePrivilegeList = new ArrayList<PrivilegeEO>();
		List<PrivilegeEO> delPrivilegeList = new ArrayList<PrivilegeEO>();
		synchroSysMgtUtil.getSynchroResInfo(addPrivilegeList,
				updatePrivilegeList, delPrivilegeList);
		for (PrivilegeEO privilegeEO : addPrivilegeList) {
			privilegeEO.setCreator("新增");
			privilegeEO.setType(getType(privilegeEO.getType()));
			resList.add(privilegeEO);
		}
		for (PrivilegeEO privilegeEO : updatePrivilegeList) {
			privilegeEO.setCreator("修改");
			privilegeEO.setType(getType(privilegeEO.getType()));
			resList.add(privilegeEO);
		}
		for (PrivilegeEO privilegeEO : delPrivilegeList) {
			privilegeEO.setCreator("删除");
			privilegeEO.setType(getType(privilegeEO.getType()));
			resList.add(privilegeEO);
		}
	}

	private String getType(String type) {
		if ("1".equals(type)) {
			return "服务(service)";
		} else if ("2".equals(type)) {
			return "表单(form)";
		} else if ("3".equals(type)) {
			return "表单元素(dom)";
		} else if ("5".equals(type)) {
			return "实体(entity)";
		} else if ("6".equals(type)) {
			return "实体操作(entity_op)";
		} else if ("9".equals(type)) {
			return "自定义(custom)";
		} else {
			return type;
		}
	}
}
