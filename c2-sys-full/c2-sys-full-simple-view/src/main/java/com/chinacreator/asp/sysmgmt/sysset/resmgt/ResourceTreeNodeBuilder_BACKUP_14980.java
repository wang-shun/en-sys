package com.chinacreator.asp.sysmgmt.sysset.resmgt;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.chinacreator.asp.comp.sys.core.privilege.dto.PrivilegeDTO;
import com.chinacreator.c2.res.ResourceManager;
import com.chinacreator.c2.res.SerialNumberRegister;
import com.chinacreator.c2.res.exception.ResourceNotFoundException;
import com.chinacreator.c2.web.ds.TreeNode;
//import com.chinacreator.platform.mvc.docket.Event;
//import com.chinacreator.platform.mvc.entity.Entity;
//import com.chinacreator.platform.mvc.form.model.Form;
//import com.chinacreator.platform.mvc.rule.Rule;

public class ResourceTreeNodeBuilder {
	private List<PrivilegeDTO> resources;

	// Path<-->虚拟节点
	private static Map<String, TreeNode> virtualNodes = new HashMap<String, TreeNode>();

	// resid<-->树节点
	private static Map<String, TreeNode> realNodes = new HashMap<String, TreeNode>();

	private void init() {
		virtualNodes.clear();
		realNodes.clear();
		virtualNodes.put("ws", new ResourceTreeNode("0", "ws", "服务",
				"service-root", true));
		virtualNodes.put("f", new ResourceTreeNode("0", "form", "表单",
				"form-root", true));
		virtualNodes.put("e", new ResourceTreeNode("0", "entity", "实体",
				"entity-root", true));
		virtualNodes.put("other", new ResourceTreeNode("0", "other", "自定义",
				"custom", true));
	}

	public ResourceTreeNodeBuilder(List<PrivilegeDTO> resources) {
		this.resources = resources;
		init();
	}

	/**
	 * 根据资源生成一个树节点，并将其加入到节点列表中去，在创建过程中会把所有的父节点都处理好
	 * 
	 * @param nodes
	 *            树节点列表
	 * @param resource
	 *            资源对象
	 * @return 输入资源对象对应的树节点
	 */
	private TreeNode addTreeNode(Collection<TreeNode> nodes,
			PrivilegeDTO resource) {
/*		if (realNodes.containsKey(resource.getPrivilegeCode())) {
			TreeNode node = realNodes.get(resource.getPrivilegeCode());
			nodes.add(node);
			return node;
		}
		String path = resource.getParentId();
		String parentPath = parseParentPath(path);

		TreeNode parentNode = null;
		PrivilegeDTO parentResource = getParentInResources(parentPath);
		if (parentResource == null) {
			// 父资源没有被作为resource传入，表明在这次展现的视图中不需要对父资源的权限进行配置
			if (virtualNodes.containsKey(parentPath)) {
				parentNode = virtualNodes.get(parentPath);
				nodes.add(parentNode);
			} else {
				parentNode = createVirtualNode(parentPath);
				if (parentNode != null) {
					// 父资源有可能不存在
					virtualNodes.put(parentPath, parentNode);
					nodes.add(parentNode);
				}
			}
		} else {
			// 父节点也是入参之一
			parentNode = addTreeNode(nodes, parentResource);
		}

		if (parentNode == null) {
			// 父节点不存在，则子节点也不应该存在
			return null;
		}

		// 创建当前节点
		ResourceTreeNode node = new ResourceTreeNode(
				resource.getPrivilegeCode(), resource.getPrivilegeName(),
				resource.getType(), false);
		node.setPid(parentNode.getId());
		node.setPrivilegeId(resource.getPrivilegeId());
		node.setSource(resource.getSource());
		nodes.add(node);s
		realNodes.put(resource.getPrivilegeCode(), node);*/
		// 还有isParent属性没有设置
//		return node;
		return null;
	}

	/**
	 * 通过Path创建虚拟节点，path会指向项目内的一个模型
	 * 
	 * @param path
	 *            资源路径
	 * @return 虚拟节点，也有可能为null，因为在开发时，本地资源可能还不完整
	 */
	private TreeNode createVirtualNode(String path) {
/*		// 通过path找到对应的模型，从中解析出树节点信息
		// 虚拟节点一定是有固定的parent信息的
		// TODO:虚拟节点的父节点也一定是虚拟节点?
		// 树节点最顶层一定是几个虚拟节点：即表示资源类型的节点
		if (null == path) {
			return new ResourceTreeNode(null, "0", "资源树", "0", true);
		}

		String[] fragments = path.split("/");
		if (fragments.length == 1) {
			return virtualNodes.get(fragments);
		}

		if (StringUtils.equals(fragments[0], "ws")) {
			// 不会出现这种情况
			String serviceSn = fragments[1];
			try {
				Rule rule = ResourceManager.getInstance().getContent(
						SerialNumberRegister.getRuleID(serviceSn), Rule.class);
				ResourceTreeNode node = new ResourceTreeNode(rule.getRes(),
						rule.getName(), "service", true);
				node.setPid("service");
				return node;
			} catch (ResourceNotFoundException e) {
				return null;
			}

		} else if (StringUtils.equals(fragments[0], "f")) {
			String formSn = fragments[1];
			String formId = SerialNumberRegister.getFormID(formSn);
			try {
				// 创建表单虚拟节点
				Form form = ResourceManager.getInstance().getContent(formId,
						Form.class);
				ResourceTreeNode node = new ResourceTreeNode(formId,
						form.getName(), "form", true);
				node.setPid("form");
				return node;
			} catch (ResourceNotFoundException e) {
				return null;
			}
		} else if (StringUtils.equals(fragments[0], "e")) {
			String entitySn = fragments[1];
			String entityId = SerialNumberRegister.getEntityID(entitySn);
			try {
				Entity entity = ResourceManager.getInstance().getContent(
						entityId, Entity.class);
				if (fragments.length == 2) {
					// 创建实体虚拟节点
					ResourceTreeNode node = new ResourceTreeNode(entityId,
							entity.getName(), "entity", true);
					node.setPid("entity");
					return node;
				} else {
					String opid = fragments[2];
					// 创建实体操作虚拟节点
					for (Event event : entity.getEvents().getEvent()) {
						if (StringUtils.equals(event.getId(), opid)) {
							ResourceTreeNode node = new ResourceTreeNode(
									entityId + "." + opid, event.getName(),
									"entity_op", true);
							node.setPid(entityId);
							return node;
						}
					}
				}
			} catch (ResourceNotFoundException e) {
				return null;
			}
		}
		return null;
	}

	private PrivilegeDTO getParentInResources(String parentPath) {
		for (PrivilegeDTO resource : resources) {
			if (StringUtils.equals(resource.getParentId(), parentPath)) {
				return resource;
			}
		}
		return null;
	}

	*//**
	 * 通过当前节点的path去解析它父节点的path
	 * 
	 * @param path
	 * @return
	 *//*
	private String parseParentPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		String[] fragments = path.split("/");
		if (StringUtils.equals(fragments[0], "e")) {
			// 实体
			if (fragments.length > 3) {
				// 是实体操作表单的dom元素，那么它的parent必然是实体操作
				return fragments[0] + "/" + fragments[1] + "/" + fragments[2];
			} else if (fragments.length == 3) {
				return fragments[0] + "/" + fragments[1];
			} else if (fragments.length == 2) {
				return fragments[0];
			}
		} else if (StringUtils.equals(fragments[0], "ws")) {
			// 是服务，parent是服务根节点
			return fragments[0];
		} else if (StringUtils.equals(fragments[0], "f")) {
			if (fragments.length > 2) {
				// 是dom
				return fragments[0] + "/" + fragments[1];
			} else {
				// 表单根节点
				return fragments[0];
			}
		} else if (StringUtils.equals(fragments[0], "other")) {
			return fragments[0];
		}*/
		return null;
	}

	public TreeNode[] build() {
		Collection<TreeNode> treeNodes = buileToCollection();
		return treeNodes.toArray(new TreeNode[treeNodes.size()]);
	}

	public Collection<TreeNode> buileToCollection() {
		Collection<TreeNode> treeNodes = new LinkedHashSet<TreeNode>();
		treeNodes.add(virtualNodes.get("f"));
		treeNodes.add(virtualNodes.get("e"));
		treeNodes.add(virtualNodes.get("ws"));
		treeNodes.add(virtualNodes.get("other"));

		for (PrivilegeDTO resource : resources) {
			addTreeNode(treeNodes, resource);
		}

		initIsParent(treeNodes);

		return treeNodes;
	}

	/**
	 * 计算所有节点的isParent属性
	 * 
	 * @param treeNodes
	 *            树节点列表
	 */
	private void initIsParent(Collection<TreeNode> treeNodes) {
		Set<String> pids = new HashSet<String>();
		for (TreeNode node : treeNodes) {
			pids.add(node.getPid());
		}
		for (TreeNode node : treeNodes) {
			if (pids.contains(node.getId())) {
				ResourceTreeNode resNode = (ResourceTreeNode) node;
				resNode.setIsParent(true);
			}
		}
	}
}
