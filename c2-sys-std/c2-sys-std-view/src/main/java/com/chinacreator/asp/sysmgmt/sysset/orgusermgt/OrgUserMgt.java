package com.chinacreator.asp.sysmgmt.sysset.orgusermgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.org.service.OrgService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.spi.GenerateOrgNumberSpi;
import com.chinacreator.asp.comp.sys.basic.org.spi.GenerateOrgXzqmSpi;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.comp.sys.core.UserMessages;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.sysmgmt.mypanel.MyPanelMessages;
import com.chinacreator.c2.ioc.ApplicationContextManager;

@Component
public class OrgUserMgt {

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
	private UserService userService;

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

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void sortOrg(String... orgIds) {
		if (null != orgIds && orgIds.length > 0) {
			List<OrgDTO> orgDTOList = new ArrayList<OrgDTO>();
			for (int i = 0; i < orgIds.length; i++) {
				OrgDTO orgDTO = new OrgDTO();
				orgDTO.setOrgId(orgIds[i]);
				orgDTO.setOrgSn(i);
				orgDTOList.add(orgDTO);
			}
			orgService.setOrder(orgDTOList);
		}
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

	public UserDTO getUserByPK(String userId) {
		UserDTO userDTO = new UserDTO();
		if (null != userId && !userId.trim().equals("")) {
			userDTO = userService.queryByPK(userId);
		} else {
			userDTO.setUserPassword(userService.getDefaultPwd());
		}
		return userDTO;
	}

	/**
	 * 机构下用户排序
	 * 
	 * @param rowNum
	 *            每页记录条数
	 * @param page
	 *            当前第几页
	 * @param orgId
	 *            机构ID
	 * @param userIds
	 *            排序后的用户ID数组
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void sortOrgUser(int rowNum, int page, String orgId,
			String... userIds) {
		if (null != userIds && userIds.length > 0) {
			if (null != orgId && !orgId.trim().equals("")
					&& !orgId.trim().equals("0")) {
				List<Map<String, Object>> sortUserList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < userIds.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", userIds[i]);
					map.put("orgId", orgId);
					map.put("sn", (page - 1) * rowNum + i);
					sortUserList.add(map);
				}
				userService.setOrderInOrg(sortUserList);
			} else {
				List<UserDTO> userDTOList = new ArrayList<UserDTO>();
				for (int i = 0; i < userIds.length; i++) {
					UserDTO userDto = new UserDTO();
					userDto.setUserId(userIds[i]);
					userDto.setUserSn((page - 1) * rowNum + i);
					userDTOList.add(userDto);
				}
				userService.setOrder(userDTOList);
			}
		}
	}

	/**
	 * 用户新增修改页面，用户所属机构显示名称字符串
	 * 
	 * @param type
	 *            用户新增、修改类型
	 * @param orgId
	 *            机构ID
	 * @param userId
	 *            用户ID
	 * @return 机构显示名称字符串，例："(主)机构A"<br>
	 *         一条记录也没查询到的情况下返回空字符串
	 */
	public String getOrgs(String type, String orgId, String userId) {
		if ("add".equals(type)) {
			return getOrgsByOrgId(orgId);
		} else if ("edit".equals(type)) {
			return getOrgsByUserId(userId);
		} else {
			return "";
		}
	}

	/**
	 * 获取指定机构显示名称字符串
	 * 
	 * @param orgId
	 *            机构ID
	 * @return 机构显示名称字符串，例："(主)机构A"<br>
	 *         一条记录也没查询到的情况下返回空字符串
	 */
	private String getOrgsByOrgId(String orgId) {
		if (null != orgId && !orgId.trim().equals("")) {
			OrgDTO orgDTO = orgService.queryByPK(orgId);
			if (null != orgDTO && null != orgDTO.getOrgShowName()
					&& !orgDTO.getOrgShowName().trim().equals("")) {
				return MyPanelMessages.getString("MYPANEL.MAINORG")
						+ orgDTO.getOrgShowName();
			}
		}
		return "";
	}

	/**
	 * 获取指定用户所属机构显示名称字符串
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户所属机构显示名称字符串，例："(主)机构A，机构B，机构C"<br>
	 *         一条记录也没查询到的情况下返回空字符串
	 */
	private String getOrgsByUserId(String userId) {
		if (null != userId && !userId.trim().equals("")) {
			List<OrgDTO> orgList = userService.queryOrgs(userId);
			OrgDTO mainOrg = userService.queryMainOrg(userId);
			String mainOrgId = null;
			if (null != mainOrg) {
				mainOrgId = mainOrg.getOrgId();
			}
			StringBuffer str = new StringBuffer();
			for (OrgDTO orgDTO : orgList) {
				if (null != orgDTO) {
					if (null != mainOrgId && !mainOrgId.trim().equals("")
							&& mainOrgId.equals(orgDTO.getOrgId())) {
						str.insert(0, ",");
						str.insert(0, orgDTO.getOrgShowName());
						str.insert(0,
								MyPanelMessages.getString("MYPANEL.MAINORG"));
					} else {
						str.append(orgDTO.getOrgShowName());
						str.append(",");
					}
				}
			}
			return str.length() > 0 ? str.substring(0, str.length() - 1) : "";
		}
		return "";
	}

	public Map<String, String> validateFormByUser(String elementId,
			String elementValue, String formType) {
		Map<String, String> map = new HashMap<String, String>();
		String validate = "success";
		String errmess = "";
		if (null != elementId && !elementId.trim().equals("")
				&& null != elementValue && !elementValue.trim().equals("")
				&& null != formType && !formType.trim().equals("")) {
			elementId = elementId.trim();
			elementValue = elementValue.trim();
			if ("add".equals(formType)) {
				if ("userName".equals(elementId)) {
					if (userService.existsByUserName(elementValue)) {
						validate = "error";
						errmess = UserMessages
								.getString("USER.USERNAME_IS_EXISTS");
					}
				}
			}
		}
		map.put("validate", validate);
		map.put("errmess", errmess);
		return map;
	}
}
