package com.chinacreator.asp.sysmgmt.sysset.orgmgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.spi.GenerateOrgNumberSpi;
import com.chinacreator.asp.comp.sys.basic.org.spi.GenerateOrgXzqmSpi;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.c2.ioc.ApplicationContextManager;

@Component
public class OrgMgt {

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.org.service.OrgServiceImpl")
	private OrgService orgService;
	
	public OrgDTO getOrgByPK(String orgId, String parentId) {
		OrgDTO orgDTO = new OrgDTO();
		if (null != orgId && !orgId.trim().equals("")) {
			orgDTO = orgService.queryByPK(orgId);
		} else {
			if (null != parentId && !parentId.trim().equals("")) {
				orgDTO.setParentId(parentId);
			} else {
				orgDTO.setParentId("0");
			}
			orgDTO.setOrgNumber(generateOrgNumber());
			orgDTO.setOrgXzqm(generateOrgXzqm());
		}
		return orgDTO;
	}
	
	public Map<String, String> validateFormByOrg(String elementId,
			String elementValue, String formType, OrgDTO orgDTO) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")
				&& null != orgDTO) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("orgNumber".equals(elementId)) {
					if (orgService.existsByOrgNumber(elementValue)) {
						validate = "error";
						errmess = OrgMessages
								.getString("ORG.ORG_NUMBER_EXISTED");
					}
				} else if ("orgName".equals(elementId)) {
					if (CommonPropertiesUtil.isUniqueOrgName()
							&& orgService.existsByOrgName(elementValue)) {
						validate = "error";
						errmess = OrgMessages.getString("ORG.ORG_NAME_EXISTED");
					}
				} else if ("orgShowName".equals(elementId)) {
					if (CommonPropertiesUtil.isUniqueOrgShowName()
							&& orgService.existsByOrgShowName(
									orgDTO.getParentId(), elementValue)) {
						validate = "error";
						errmess = OrgMessages
								.getString("ORG.ORG_SHOWNAME_EXISTED_IN_THE_SAME_LEVEL");
					}
				}
			} else if ("edit".equals(formType)) {
				if ("orgNumber".equals(elementId)) {
					if (orgService.existsByOrgNumberIgnoreOrgID(elementValue,
							orgDTO.getOrgId())) {
						validate = "error";
						errmess = OrgMessages
								.getString("ORG.ORG_NUMBER_EXISTED");
					}
				} else if ("orgName".equals(elementId)) {
					if (CommonPropertiesUtil.isUniqueOrgName()
							&& orgService.existsByOrgNameIgnoreOrgID(
									elementValue, orgDTO.getOrgId())) {
						validate = "error";
						errmess = OrgMessages.getString("ORG.ORG_NAME_EXISTED");
					}
				} else if ("orgShowName".equals(elementId)) {
					if (CommonPropertiesUtil.isUniqueOrgShowName()
							&& orgService.existsByOrgShowNameIgnoreOrgID(
									elementValue, orgDTO.getOrgId())) {
						validate = "error";
						errmess = OrgMessages
								.getString("ORG.ORG_SHOWNAME_EXISTED_IN_THE_SAME_LEVEL");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}

	/**
	 * 移动机构
	 * 
	 * @param orgIds
	 *            被移动的机构ID
	 * @param parentId
	 *            移动到的父机构ID
	 * @param sortOrgIds
	 *            父机构下子机构排序
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void moveOrgs(String[] orgIds, String parentId, String... sortOrgIds) {
		if (null != orgIds && orgIds.length > 0 && null != parentId
				&& !parentId.trim().equals("")) {
			for (int i = 0; i < orgIds.length; i++) {
				OrgDTO orgDTO = new OrgDTO();
				orgDTO.setOrgId(orgIds[i]);
				orgDTO.setParentId(parentId);
				orgService.update(orgDTO);
			}
		}

		if (null != sortOrgIds && sortOrgIds.length > 0) {
			List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
			for (int i = 0; i < sortOrgIds.length; i++) {
				OrgDTO orgDTO = new OrgDTO();
				orgDTO.setOrgId(sortOrgIds[i]);
				orgDTO.setOrgSn(i);
				orgDTOList.add(orgDTO);
			}
			orgService.setOrder(orgDTOList);
		}
	}
	
	private String generateOrgNumber() {
		String orgNumber = null;
		try {
			GenerateOrgNumberSpi generateOrgNumberSpi = ApplicationContextManager
					.getContext().getBean(GenerateOrgNumberSpi.class);
			if (null != generateOrgNumberSpi) {
				orgNumber = generateOrgNumberSpi.getOrgNumber();
			}
		} catch (Exception e) {
		}

		return orgNumber;
	}

	private String generateOrgXzqm() {
		String orgXzqm = null;
		try {
			GenerateOrgXzqmSpi generateOrgXzqmSpi = ApplicationContextManager
					.getContext().getBean(GenerateOrgXzqmSpi.class);
			if (null != generateOrgXzqmSpi) {
				orgXzqm = generateOrgXzqmSpi.getOrgXzqm();
			}
		} catch (Exception e) {
		}
		return orgXzqm;
	}
}
