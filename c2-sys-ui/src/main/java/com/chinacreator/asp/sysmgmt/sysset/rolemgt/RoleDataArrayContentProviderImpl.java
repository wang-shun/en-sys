package com.chinacreator.asp.sysmgmt.sysset.rolemgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.advanced.role.service.RoleService;
import com.chinacreator.asp.comp.sys.basic.role.dto.RoleTypeDTO;
import com.chinacreator.asp.comp.sys.basic.role.service.RoleTypeService;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Order;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

@Component("roledataarraycontent")
public class RoleDataArrayContentProviderImpl implements ArrayContentProvider {

	private RoleTypeService roleTypeService = ApplicationContextManager.getContext().getBean(RoleTypeService.class);

	private RoleService roleService = ApplicationContextManager.getContext().getBean(RoleService.class);

	private static final Set<String> sfset_INBUILT_ROLEID = new HashSet<String>() {
		private static final long serialVersionUID = 1L;

		{
			add(CommonPropertiesUtil.getAdministratorRoleId());
			add(CommonPropertiesUtil.getOrgManagerRoleId());
			add(CommonPropertiesUtil.getRoleofeveryoneRoleId());
		}
	};

	public Page<Map<String, Object>> getElements(ArrayContext context) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(new Pageable(),
				new ArrayList<Map<String, Object>>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String roleType = (String) map.get("roleType");
				if ("0".equals(roleType)) {
					map.remove("roleType");
				}
				RoleDTO roleDTO = new RoleDTO();
				try {
					BeanUtils.populate(roleDTO, map);
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
				Sortable sortable = context.getSortable();
				if (null != sortable) {
					List<Order> orderList = sortable.getOrders();
					if (null != orderList) {
						for (Order order : orderList) {
							if (order.getOrderProperty().equals("roleTypeName")) {
								order.setOrderProperty("roleType");
							}
						}
					}
				}

				Page<RoleDTO> rolePage = roleService.queryByRoleIgnoreAnonymous(roleDTO, context.getPageable(),
						context.getSortable());

				if (null != rolePage) {
					List<RoleDTO> roleDTOs = rolePage.getContents();
					List<Map<String, Object>> roleMapList = new ArrayList<Map<String, Object>>();

					try {
						for (RoleDTO r : roleDTOs) {
							Map<String, Object> roleMap = new HashMap<String, Object>();
							roleMap = BeanUtils.describe(r);
							RoleTypeDTO roleTypeDTO = roleTypeService.queryByPK(r.getRoleType());
							roleMap.put("roleTypeName", roleTypeDTO.getTypeName());
							roleMap.put("isUD", !sfset_INBUILT_ROLEID.contains(r.getRoleId()));
							roleMap.put("isAdmin", r.getRoleId().equals(CommonPropertiesUtil.getAdministratorRoleId()));
							roleMap.put("isEveryone",
									r.getRoleId().equals(CommonPropertiesUtil.getRoleofeveryoneRoleId()));

							roleMapList.add(roleMap);
						}
						page = new Page<Map<String, Object>>(context.getPageable(), roleMapList);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return page;
	}
}
