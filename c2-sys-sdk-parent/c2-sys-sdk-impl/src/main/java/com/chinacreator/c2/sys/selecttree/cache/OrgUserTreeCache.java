package com.chinacreator.c2.sys.selecttree.cache;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.sys.selecttree.handler.OrgInvocationHandler;
import com.chinacreator.c2.sys.selecttree.handler.UserInvocationHandler;
import com.chinacreator.c2.sys.selecttree.treenode.OrgUserTreeNode;
import com.chinacreator.c2.tree.GenericTree;
import com.chinacreator.c2.tree.GenericTreeNodeFactory;
import com.google.common.reflect.Reflection;

/**
 * 机构用户树型数据缓存类
 * 
 * @author 彭盛
 * 
 */
public class OrgUserTreeCache {

	// 机构树
	private static GenericTree<OrgUserTreeNode> orgTree;
	// 机构用户树
	private static GenericTree<OrgUserTreeNode> orgUserTree;
	// 用户集合
	private static Map<String, List<UserDTO>> userMap;

	@Autowired
	private OrgService orgService;

	/**
	 * 初始化
	 */
	public void init() {
		List<OrgDTO> orgs = new ArrayList<OrgDTO>();
		List<Object> orgUsers = new ArrayList<Object>();

		List<OrgDTO> allOrgDTOs = orgService.queryAll();
		Set<String> orgIdSet = new HashSet<String>();
		userMap = new HashMap<String, List<UserDTO>>();

		reOrgs(orgs, orgUsers, allOrgDTOs, "0", orgIdSet, 1);

		orgTree = GenericTree.create(getFactory(), orgs);
		orgUserTree = GenericTree.create(getFactory(), orgUsers);
	}

	private void reOrgs(List<OrgDTO> orgs, List<Object> orgUsers, List<OrgDTO> allOrgDTOs, String parentId,
			Set<String> orgIdSet, int depth) {
		for (OrgDTO orgDTO : allOrgDTOs) {
			if (parentId.equals(orgDTO.getParentId()) && orgIdSet.add(orgDTO.getOrgId())) {
				orgDTO.setLayer("" + depth);

				orgs.add(orgDTO);
				orgUsers.add(orgDTO);

				int i = 0;
				List<UserDTO> userDTOs = orgService.queryUsers(orgDTO.getOrgId());
				for (UserDTO userDTO : userDTOs) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orgId", orgDTO.getOrgId());
					map.put("orgShowName", orgDTO.getOrgShowName());
					map.put("sn", i++);
					userDTO.setExtFields(map);
					orgUsers.add(userDTO);

					List<UserDTO> users = null;
					if (userMap.containsKey(userDTO.getUserId())) {
						users = userMap.get(userDTO.getUserId());
					} else {
						users = new ArrayList<UserDTO>();
					}
					users.add(userDTO);
					userMap.put(userDTO.getUserId(), users);
				}

				reOrgs(orgs, orgUsers, allOrgDTOs, orgDTO.getOrgId(), orgIdSet, depth + 1);
			}
		}
	}

	private GenericTreeNodeFactory<OrgUserTreeNode> getFactory() {
		GenericTreeNodeFactory<OrgUserTreeNode> factory = new GenericTreeNodeFactory<OrgUserTreeNode>() {
			@Override
			public OrgUserTreeNode createTreeNode(Object obj) {
				return Reflection.newProxy(OrgUserTreeNode.class, createInvocationHandler(obj));
			}

			public InvocationHandler createInvocationHandler(Object proxy) {
				if (proxy instanceof OrgDTO) {
					return new OrgInvocationHandler(proxy);
				} else if (proxy instanceof UserDTO) {
					return new UserInvocationHandler(proxy);
				}
				return null;
			}
		};
		return factory;
	}

	/**
	 * 获取机构树型数据缓存
	 * 
	 * @return 机构树型数据缓存
	 */
	public static GenericTree<OrgUserTreeNode> getOrgTree() {
		return orgTree;
	}

	/**
	 * 获取机构用户树型数据缓存
	 * 
	 * @return 机构用户树型数据缓存
	 */
	public static GenericTree<OrgUserTreeNode> getOrgUserTree() {
		return orgUserTree;
	}

	/**
	 * 新增机构
	 * 
	 * @param orgDTO
	 *            机构对象
	 */
	public void addOrg(OrgDTO orgDTO) {
		List<OrgDTO> nodes = new ArrayList<OrgDTO>();
		String parentId = orgDTO.getParentId();
		if (null != parentId && !parentId.trim().equals("") && !parentId.trim().equals("0")) {
			List<OrgUserTreeNode> ouNodes = orgTree.getTree(1, parentId);
			orgDTO.setLayer("" + (ouNodes.get(0).getDepth() + 1));
		} else {
			orgDTO.setLayer("1");
		}
		nodes.add(orgDTO);
		orgTree.addNodes(nodes);
		orgUserTree.addNodes(nodes);
	}

	/**
	 * 修改机构
	 * 
	 * @param orgDTO
	 *            机构对象
	 */
	public void replaceOrg(OrgDTO orgDTO) {
		List<OrgUserTreeNode> ouNodes = orgTree.getTree(1, orgDTO.getOrgId());
		OrgUserTreeNode ouNode = ouNodes.get(0);
		if (null != ouNode) {
			OrgDTO oldOrg = (OrgDTO) ouNode.getDTO();
			Set<String> ignoreAttr = new HashSet<String>();
			ignoreAttr.add("setOrgId");
			ignoreAttr.add("setLayer");

			OrgDTO newOrg = new OrgDTO();
			BeanCopierUtil.copy(oldOrg, newOrg);
			copy(orgDTO, newOrg, ignoreAttr);

			List<UserDTO> userList = new ArrayList<UserDTO>();
			if (null != orgDTO.getOrgShowName() && !orgDTO.getOrgShowName().trim().equals("")
					&& !orgDTO.getOrgShowName().equals(oldOrg.getOrgShowName())) {
				List<OrgUserTreeNode> childNodes = orgUserTree.getChildren(1, orgDTO.getOrgId());
				for (OrgUserTreeNode childNode : childNodes) {
					if ("user".equals(childNode.getType())) {
						UserDTO userDTO = (UserDTO) childNode.getDTO();
						UserDTO newUser = new UserDTO();
						BeanCopierUtil.copy(userDTO, newUser);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orgId", orgDTO.getOrgId());
						map.put("orgShowName", orgDTO.getOrgShowName());
						map.put("sn", userDTO.getExtFields().get("sn"));
						newUser.setExtFields(map);
						userList.add(newUser);

						List<UserDTO> users = userMap.get(userDTO.getUserId());
						for (UserDTO user : users) {
							if (orgDTO.getOrgId().equals(user.getExtFields().get("orgId"))) {
								user.getExtFields().put("orgShowName", orgDTO.getOrgShowName());
							}
						}
					}
				}
			}

			orgTree.replaceNodes(newOrg);
			orgUserTree.replaceNodes(newOrg);
			if (!userList.isEmpty()) {
				orgUserTree.replaceNodes(userList.toArray(new UserDTO[userList.size()]));
			}
		}
	}

	/**
	 * 机构排序
	 * 
	 * @param orgDTOs
	 *            机构对象列表
	 */
	public void setOrderByOrg(List<OrgDTO> orgDTOs) {
		List<OrgDTO> nodes = new ArrayList<OrgDTO>();
		for (OrgDTO orgDTO : orgDTOs) {
			List<OrgUserTreeNode> ouNodes = orgTree.getTree(1, orgDTO.getOrgId());
			OrgUserTreeNode ouNode = ouNodes.get(0);
			if (null != ouNode) {
				OrgDTO oldOrg = (OrgDTO) ouNode.getDTO();
				Set<String> ignoreAttr = new HashSet<String>();
				ignoreAttr.add("setOrgId");
				ignoreAttr.add("setParentId");
				ignoreAttr.add("setLayer");

				OrgDTO _oldOrg = new OrgDTO();
				BeanCopierUtil.copy(oldOrg, _oldOrg);
				copy(orgDTO, _oldOrg, ignoreAttr);

				nodes.add(_oldOrg);
			}
		}
		if (!nodes.isEmpty()) {
			orgTree.replaceNodes(nodes.toArray(new OrgDTO[nodes.size()]));
			orgUserTree.replaceNodes(nodes.toArray(new OrgDTO[nodes.size()]));
		}
	}

	/**
	 * 删除机构
	 * 
	 * @param orgIds
	 *            机构ID数组
	 */
	public void removeOrg(String... orgIds) {
		orgIds = deDuplicationAndNull(orgIds);
		if (orgIds.length > 0) {
			List<OrgUserTreeNode> orgUsers = orgUserTree.getChildren(-1, orgIds);
			for (OrgUserTreeNode node : orgUsers) {
				if ("user".equals(node.getType())) {
					String userId = node.getId().substring("user:".length(), node.getId().indexOf("###"));
					userMap.remove(userId);
				}
			}

			orgTree.removeNode(orgIds);
			orgUserTree.removeNode(orgIds);
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param userDTO
	 *            用户对象
	 * @param orgId
	 *            机构ID
	 * @param sn
	 *            排序号
	 */
	public void addUser(UserDTO userDTO, String orgId, int sn) {
		List<UserDTO> nodes = new ArrayList<UserDTO>();
		OrgDTO orgDTO = orgService.queryByPK(orgId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("orgShowName", orgDTO.getOrgShowName());
		map.put("sn", sn);
		userDTO.setExtFields(map);
		nodes.add(userDTO);
		orgUserTree.addNodes(nodes);

		List<UserDTO> users = null;
		if (userMap.containsKey(userDTO.getUserId())) {
			users = userMap.get(userDTO.getUserId());
		} else {
			users = new ArrayList<UserDTO>();
		}
		users.add(userDTO);
		userMap.put(userDTO.getUserId(), users);
	}

	/**
	 * 修改用户
	 * 
	 * @param userDTO
	 *            用户对象
	 */
	public void replaceUser(UserDTO userDTO) {
		List<UserDTO> nodes = new ArrayList<UserDTO>();
		List<UserDTO> users = userMap.get(userDTO.getUserId());
		if (null != users) {
			Set<String> ignoreAttr = new HashSet<String>();
			ignoreAttr.add("setUserId");
			ignoreAttr.add("setUserName");
			ignoreAttr.add("setExtFields");
			for (UserDTO user : users) {
				UserDTO _user = new UserDTO();
				BeanCopierUtil.copy(user, _user);

				copy(userDTO, _user, ignoreAttr);
				nodes.add(_user);
			}

			orgUserTree.replaceNodes(nodes.toArray(new UserDTO[nodes.size()]));
			userMap.put(userDTO.getUserId(), nodes);
		}
	}

	/**
	 * 用户排序
	 * 
	 * @param sortUserList
	 */
	public void setOrderByUser(List<Map<String, Object>> sortUserList) {
		List<UserDTO> nodes = new ArrayList<UserDTO>();
		for (Map<String, Object> map : sortUserList) {
			if (map != null) {
				String userId = (String) map.get("userId");
				String orgId = (String) map.get("orgId");
				Integer sn = (Integer) map.get("sn");

				List<UserDTO> users = userMap.get(userId);
				if (null != users) {
					for (UserDTO user : users) {
						String userOrg = (String) user.getExtFields().get("orgId");
						if (orgId.equals(userOrg)) {
							user.getExtFields().put("sn", sn);

							nodes.add(user);
						}
					}
				}
			}
		}
		if (!nodes.isEmpty()) {
			orgUserTree.replaceNodes(nodes.toArray(new UserDTO[nodes.size()]));
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 *            用户ID数组
	 */
	public void removeUser(String... userIds) {
		userIds = deDuplicationAndNull(userIds);
		if (userIds.length > 0) {
			Set<String> userIdSet = new HashSet<String>();
			for (String userId : userIds) {
				List<UserDTO> users = userMap.get(userId);
				if (null != users) {
					for (UserDTO userDTO : users) {
						userIdSet.add("user:" + userDTO.getUserId() + "###org:" + userDTO.getExtFields().get("orgId"));
					}
				}
			}
			if (!userIdSet.isEmpty()) {
				orgUserTree.removeNode(userIdSet.toArray(new String[userIdSet.size()]));
			}
		}
		for (String userId : userIds) {
			userMap.remove(userId);
		}
	}

	/**
	 * 用户设置主机构
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @param orgId
	 *            主机构ID
	 * @param isRetain
	 *            用户是否保留在原机构下(true:是，false:否)
	 */
	public void setMainOrgByUser(String[] userIds, String orgId, boolean isRetain) {
		userIds = deDuplicationAndNull(userIds);
		if (userIds.length > 0) {
			List<UserDTO> nodes = new ArrayList<UserDTO>();
			OrgDTO orgDTO = orgService.queryByPK(orgId);
			for (String userId : userIds) {
				List<UserDTO> users = userMap.get(userId);
				if (null != users) {
					UserDTO userDTO = users.get(0);
					UserDTO _userDTO = new UserDTO();
					BeanCopierUtil.copy(userDTO, _userDTO);

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orgId", orgId);
					map.put("orgShowName", orgDTO.getOrgShowName());
					map.put("sn", 999);
					_userDTO.setExtFields(map);
					nodes.add(_userDTO);
				}
			}
			if (!isRetain) {
				removeUser(userIds);
			}
			if (!nodes.isEmpty()) {
				orgUserTree.addNodes(nodes);
				for (UserDTO userDTO : nodes) {
					List<UserDTO> users = null;
					if (userMap.containsKey(userDTO.getUserId())) {
						users = userMap.get(userDTO.getUserId());
					} else {
						users = new ArrayList<UserDTO>();
					}
					users.add(userDTO);
					userMap.put(userDTO.getUserId(), users);
				}
			}
		}
	}

	/**
	 * 去重复与空值
	 * 
	 * @param userIds
	 *            用户ID数组
	 * @return 去重复与空值后的用户ID数组
	 */
	private static String[] deDuplicationAndNull(String... ids) {
		Set<String> set = new HashSet<String>();
		if (null != ids && ids.length > 0) {
			for (String id : ids) {
				if (null != id && !id.trim().equals("")) {
					set.add(id);
				}
			}
		}
		return set.toArray(new String[set.size()]);
	}

	/**
	 * 将源类型转换为目标实体类型,忽略源类型对象中的null值
	 * 
	 * @param source
	 *            源类型对象
	 * @param target
	 *            目标类型对象
	 * @param ignoreAttr
	 *            忽略属性
	 */
	private static <S, T> void copy(S source, final T target, final Set<String> ignoreAttr) {
		if (source == null || target == null) {
			return;
		}

		BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), true);
		Converter converter = new Converter() {
			@Override
			public Object convert(Object value, Class type, Object key) {
				if (null == value || (null != ignoreAttr && ignoreAttr.contains(key))) {
					String _key = key.toString().substring(3, 4).toLowerCase() + key.toString().substring(4);

					try {
						return PropertyUtils.getProperty(target, _key);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return value;
			}
		};
		copier.copy(source, target, converter);
	}
}