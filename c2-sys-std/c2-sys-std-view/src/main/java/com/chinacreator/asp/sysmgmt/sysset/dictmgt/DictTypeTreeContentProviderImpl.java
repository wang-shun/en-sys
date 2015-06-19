package com.chinacreator.asp.sysmgmt.sysset.dictmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chinacreator.asp.comp.sys.basic.dict.dto.DictTypeDTO;
import com.chinacreator.asp.comp.sys.basic.dict.service.DictTypeService;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;

public class DictTypeTreeContentProviderImpl implements TreeContentProvider {

	private DictTypeService dictTypeService = ApplicationContextManager
			.getContext().getBean(DictTypeService.class);

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String dictTypeId = (String) map.get("id");
			if(null==dictTypeId || dictTypeId.trim().equals("")){
				List<DictTypeDTO> dictTypeDTOs = dictTypeService.queryAll();
				CommonTreeNode rootTreeNode = new CommonTreeNode();
				rootTreeNode.setId("0");
				rootTreeNode.setName(DictMgtMessages
						.getString("DICTMGT.DICTTYPE_ROOT_TREENAME"));
				rootTreeNode.setPid(null);
				rootTreeNode.setParent(!dictTypeDTOs.isEmpty());
				list.add(rootTreeNode);
				
				for (DictTypeDTO dictTypeDTO : dictTypeDTOs) {
					CommonTreeNode treeNode = new CommonTreeNode();
					treeNode.setId(dictTypeDTO.getDicttypeId());
					treeNode.setName(dictTypeDTO.getDicttypeName());
					treeNode.setPid("0");
					treeNode.setParent(false);
					list.add(treeNode);
				}
			}
		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}

}
