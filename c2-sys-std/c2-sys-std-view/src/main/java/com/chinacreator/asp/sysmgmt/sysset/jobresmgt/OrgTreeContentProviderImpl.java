package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.JobMenuMgtMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
@Service("orgtreecontent")
public class OrgTreeContentProviderImpl implements TreeContentProvider {
	@Autowired
	private OrgService orgService;

	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context) {
			Map<String, Object> map = context.getConditions();
			String orgId = (String) map.get("id");
			if (null == orgId || orgId.trim().equals("")) {
				CommonTreeNode jobOrgTreeNode = new CommonTreeNode();
				jobOrgTreeNode.setId("-1");
				jobOrgTreeNode.setName(JobMenuMgtMessages
						.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"));
				jobOrgTreeNode.setPid(null);
				jobOrgTreeNode.setParent(false);
				list.add(jobOrgTreeNode);

				CommonTreeNode rootOrgTreeNode = new CommonTreeNode();
				rootOrgTreeNode.setId("0");
				rootOrgTreeNode.setName(JobMenuMgtMessages
						.getString("JOBMENUMGT.ORG_ROOT_TREENAME"));
				rootOrgTreeNode.setPid(null);
				rootOrgTreeNode.setParent(orgService.existsChildOrgs("0"));
				list.add(rootOrgTreeNode);

				orgId = "0";
			}else{
				List<OrgDTO> orgDTOs = orgService.queryChildOrgs(orgId, false);
				for (OrgDTO orgDTO : orgDTOs) {
					CommonTreeNode orgTreeNode = new CommonTreeNode();
					orgTreeNode.setId(orgDTO.getOrgId());
					orgTreeNode.setName(orgDTO.getOrgShowName());
					orgTreeNode.setPid(orgDTO.getParentId());
					orgTreeNode.setParent(orgService.existsChildOrgs(orgDTO
							.getOrgId()));
					list.add(orgTreeNode);
				}				
			}

		}
		return list.toArray(new CommonTreeNode[list.size()]);
	}
}
