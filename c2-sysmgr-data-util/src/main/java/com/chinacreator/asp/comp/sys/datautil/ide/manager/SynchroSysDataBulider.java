package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeInsiderelateEO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
//import com.chinacreator.platform.mvc.perm.Resource;

public class SynchroSysDataBulider {

	private IDEOperUtil ideOperUtil = ApplicationContextManager.getContext().getBean(IDEOperUtil.class);
	private PrivilegeDao privilegeDao = ApplicationContextManager.getContext().getBean(PrivilegeDao.class);

	private List<PrivilegeEO> addPrivilegeList;
	private List<PrivilegeEO> updatePrivilegeList;
	private Set<String> delPrivilegeSet;
	private List<PrivilegeInsiderelateEO> addPrivilegeInsiderelateList;
	private Set<String> delPrivilegeInsiderelateSet;
	private Set<String> delPermSet;

//	private List<Resource> ideResList;

	// 数据库perm_expr对应id
	private Map<String, String> dbResIdMap;
	// 数据库perm_expr对应PrivilegeEO
	private Map<String, PrivilegeEO> dbResEoMap;
	private Set<String> dbResSet;
	private Set<String> existResSet;

	public SynchroSysDataBulider() {
		addPrivilegeList = new ArrayList<PrivilegeEO>();
		updatePrivilegeList = new ArrayList<PrivilegeEO>();
		delPrivilegeSet = new HashSet<String>();
		addPrivilegeInsiderelateList = new ArrayList<PrivilegeInsiderelateEO>();
		delPrivilegeInsiderelateSet = new HashSet<String>();
		delPermSet = new HashSet<String>();

//		List<Resource> ideRes = ideOperUtil.getIDEResourceList();

//		ValidateIdeRes validateIdeRes = new ValidateIdeRes(ideRes);
//		ideResList = validateIdeRes.getResList();

		dbResIdMap = new HashMap<String, String>();
		dbResEoMap = new HashMap<String, PrivilegeEO>();
		dbResSet = new HashSet<String>();
		existResSet = new HashSet<String>();

		List<PrivilegeEO> dbResList = privilegeDao.query(new PrivilegeEO());
		if (null != dbResList && !dbResList.isEmpty()) {
			for (PrivilegeEO privilegeEO : dbResList) {
				if (!"4".equals(privilegeEO.getType()) && "1".equals(privilegeEO.getSource())) {
					dbResIdMap.put(privilegeEO.getPermExpr(), privilegeEO.getPrivilegeId());
					dbResEoMap.put(privilegeEO.getPermExpr(), privilegeEO);
					dbResSet.add(privilegeEO.getPermExpr());
				}
			}
		}

		init();
	}

	private void init() {
//		if (null != ideResList && !ideResList.isEmpty()) {
//			Set<String> addSet = new HashSet<String>();
//			for (Resource resource : ideResList) {
//				if (null != resource) {
//					String id = resource.getId();
//					if (dbResSet.contains(id)) {
//						PrivilegeEO privilegeEO = dbResEoMap.get(id);
//						if (null != privilegeEO) {
//							if (!privilegeEO.getVirtual() && resource.isVirtual()) {
//								delPermSet.add(privilegeEO.getPrivilegeId());
//							}
//
//							privilegeEO.setPrivilegeName(resource.getName());
//							privilegeEO.setParentId(resource.getPath());
//							privilegeEO.setType(getTypeByResType(id, resource.getType()));
//							privilegeEO.setCreatorTime(new Date());
//							privilegeEO.setSn(resource.getSn());
//							privilegeEO.setVirtual(resource.isVirtual());
//							updatePrivilegeList.add(privilegeEO);
//
//							existResSet.add(id);
//						} else {
//							throw new NullPointerException(String.format("资源【%s(%s)】在数据库中不存在！", resource.getName(), id));
//						}
//					} else {
//						if (!addSet.contains(id)) {
//							PrivilegeEO privilegeEO = new PrivilegeEO();
//							privilegeEO.setPrivilegeId(PKGenerator.generate());
//							privilegeEO.setPrivilegeCode(id);
//							privilegeEO.setPermExpr(id);
//							privilegeEO.setPrivilegeName(resource.getName());
//							privilegeEO.setParentId(resource.getPath());
//							privilegeEO.setType(getTypeByResType(id, resource.getType()));
//							privilegeEO.setCreator("1");
//							privilegeEO.setCreatorTime(new Date());
//							privilegeEO.setSn(resource.getSn());
//							privilegeEO.setSource("1");
//							privilegeEO.setVirtual(resource.isVirtual());
//							addPrivilegeList.add(privilegeEO);
//
//							dbResIdMap.put(id, privilegeEO.getPrivilegeId());
//							addSet.add(id);
//						}
//					}
//				}
//			}
//		}

		for (String key : dbResEoMap.keySet()) {
			if (!existResSet.contains(key)) {
				PrivilegeEO privilegeEO = dbResEoMap.get(key);
				if (null != privilegeEO) {
					delPrivilegeSet.add(privilegeEO.getPrivilegeId());
					delPrivilegeInsiderelateSet.add(privilegeEO.getPrivilegeId());
				}
			}
		}

//		for (Resource resource : ideResList) {
//			if (null != resource) {
//				String id = dbResIdMap.get(resource.getId());
//				String pid = resource.getPid();
//				String relateId = null;
//				if ("0".equals(pid)) {
//					relateId = "0";
//				} else {
//					relateId = dbResIdMap.get(pid);
//				}
//				if (null == id || id.trim().equals("")) {
//					throw new NullPointerException("资源：" + resource.getId() + "未找到对应id");
//				}
//				if (null == relateId || relateId.trim().equals("")) {
//					throw new NullPointerException("资源：" + pid + "未找到对应id");
//				}
//				PrivilegeInsiderelateEO privilegeInsiderelateEO = new PrivilegeInsiderelateEO();
//				privilegeInsiderelateEO.setId(id);
//				privilegeInsiderelateEO.setRelateId(relateId);
//				privilegeInsiderelateEO.setSn(resource.getSn());
//				addPrivilegeInsiderelateList.add(privilegeInsiderelateEO);
//				delPrivilegeInsiderelateSet.add(id);
//			}
//		}
	}

	private String getTypeByResType(String resId, String resType) {
		if (null != resType && !resType.trim().equals("")) {
			if ("service".equals(resType)) {
				return "1";
			} else if ("form".equals(resType)) {
				return "2";
			} else if ("dom".equals(resType)) {
				return "3";
			} else if ("entity".equals(resType)) {
				return "5";
			} else if ("entity_op".equals(resType)) {
				return "6";
			} else if ("rest".equals(resType)) {
				return "7";
			} else if ("custom".equals(resType)) {
				return "9";
			} else {
				throw new SysException(String.format("资源【%s】类型【%s】无法识别！", resId, resType));
			}
		} else {
			throw new SysException(String.format("资源【%s】类型为空！", resId));
		}
	}

	public List<PrivilegeEO> getAddPrivilegeList() {
		return addPrivilegeList;
	}

	public List<PrivilegeEO> getUpdatePrivilegeList() {
		return updatePrivilegeList;
	}

	public Set<String> getDelPrivilegeSet() {
		return delPrivilegeSet;
	}

	public List<PrivilegeInsiderelateEO> getAddPrivilegeInsiderelateList() {
		return addPrivilegeInsiderelateList;
	}

	public Set<String> getDelPrivilegeInsiderelateSet() {
		return delPrivilegeInsiderelateSet;
	}

	public Set<String> getDelPermSet() {
		return delPermSet;
	}
}