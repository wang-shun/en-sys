package com.chinacreator.asp.sysmgmt.aspmgt.sysinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.sysinfo.dto.SystemTypeDTO;
import com.chinacreator.asp.comp.sys.basic.sysinfo.service.SysRTSettingTypeService;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class SysInfoTypeTreeContentProviderImpl implements TreeContentProvider {

	private SysRTSettingTypeService sysRTSettingTypeService = ApplicationContextManager
			.getContext().getBean(SysRTSettingTypeService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String dictTypeId = (String) map.get("id");
			if (null == dictTypeId || dictTypeId.trim().equals("")) {
				List<SystemTypeDTO> systemTypeDTOs = sysRTSettingTypeService
						.queryAll();
				CommonTreeNode rootTreeNode = new CommonTreeNode();
				rootTreeNode.setId("0");
				rootTreeNode.setName(SysInfoMessages
						.getString("SYSINFO.SYSINFOTYPE_ROOT_TREENAME"));
				rootTreeNode.setPid(null);
				rootTreeNode.setParent(!systemTypeDTOs.isEmpty());
				list.add(rootTreeNode);

				for (SystemTypeDTO systemTypeDTO : systemTypeDTOs) {
					CommonTreeNode treeNode = new CommonTreeNode();
					treeNode.setId(systemTypeDTO.getId());
					treeNode.setName(systemTypeDTO.getTypeName());
					treeNode.setPid("0");
					treeNode.setParent(false);
					list.add(treeNode);
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}

}
