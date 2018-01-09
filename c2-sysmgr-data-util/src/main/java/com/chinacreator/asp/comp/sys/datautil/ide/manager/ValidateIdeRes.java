package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinacreator.platform.mvc.perm.Resource;

public class ValidateIdeRes {

	private List<Resource> resList;
	private Map<String, List<Resource>> resMap;

	private Map<String, String> ideResMap;
	private Set<String> resPidSet;
	private Set<String> resIdPidSet;
	
	private Set<String> resTypeSet;

	public ValidateIdeRes(List<Resource> ideResList) {
		resList = new ArrayList<Resource>();
		resMap = new HashMap<String, List<Resource>>();
		ideResMap = new HashMap<String, String>();
		resPidSet = new HashSet<String>();
		resIdPidSet = new HashSet<String>();
		resTypeSet = new HashSet<String>();
		resTypeSet.add("service");
		resTypeSet.add("form");
		resTypeSet.add("dom");
		resTypeSet.add("menu");
		resTypeSet.add("entity");
		resTypeSet.add("entity_op");
		resTypeSet.add("rest");
		resTypeSet.add("custom");

		if (null != ideResList && !ideResList.isEmpty()) {
			for (Resource resource : ideResList) {
				if (null != resource) {
					validateIdeResIsNull(resource);
					if (!ideResMap.containsKey(resource.getId())) {
						ideResMap.put(resource.getId(), resource.getName() + "(" + resource.getId() + ")");
					}

					String pid = resource.getPid();
					if (null == pid || pid.trim().equals("")) {
						pid = "0";
						resource.setPid("0");
					}
					List<Resource> list = resMap.get(pid);
					if (null == list) {
						list = new ArrayList<Resource>();
					}
					list.add(resource);
					resMap.put(pid, list);
				}
			}

			Set<String> pathSet = new HashSet<String>();
			reSelectRes("0", pathSet);

			for (String key : resMap.keySet()) {
				if (!resPidSet.contains(key)) {
					List<Resource> list = resMap.get(key);
					if (null != list && !list.isEmpty()) {
						for (Resource resource : list) {
							if (resIdPidSet.add(resource.getId() + "0")) {
								resource.setPid("0");
								resList.add(resource);
							}
						}
					}
				}
			}
		}
	}

	private void reSelectRes(String pid, Set<String> pathSet) {
		resPidSet.add(pid);
		List<Resource> list = resMap.get(pid);
		if (null != list) {
			for (Resource resource : list) {
				Set<String> set = new LinkedHashSet<String>();
				set.addAll(pathSet);
				if (!set.add(resource.getId())) {
					outErr(set, resource.getId());
				} else {
					if (resIdPidSet.add(resource.getId() + pid)) {
						resList.add(resource);
					}
					reSelectRes(resource.getId(), set);
				}
			}
		}
	}

	private void validateIdeResIsNull(Resource resource) {
		String id = resource.getId();
		String name = resource.getName();
		String type = resource.getType();
		if (null == id || id.trim().equals("") || null == name || name.trim().equals("") || null == type
				|| type.trim().equals("")) {
			throw new NullPointerException("资源权限有空值！{id:" + id + ",name:" + name + ",type:" + type + "}");
		}
		if(!resTypeSet.contains(type)){
			throw new IllegalArgumentException("资源权限存在未知类型！{id:" + id + ",name:" + name + ",type:" + type + "}");
		}
	}

	private void outErr(Set<String> pathSet, String id) {
		StringBuffer mess = new StringBuffer();
		mess.append("资源权限有循环依赖：");

		for (String resId : pathSet) {
			mess.append(ideResMap.get(resId));
			mess.append("->");
		}
		mess.append(ideResMap.get(id));
		throw new IllegalArgumentException(mess.toString());
	}

	public List<Resource> getResList() {
		return resList;
	}
}
