package com.chinacreator.asp.sysmgmt.sysset.usermgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.sysmgmt.mypanel.MyPanelMessages;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class UserDataArrayContentProviderImpl implements ArrayContentProvider {

	private UserService userService = ApplicationContextManager.getContext()
			.getBean(UserService.class);

	private JobService jobService = ApplicationContextManager.getContext()
			.getBean(JobService.class);

	@Override
	public Page<UserDTO> getElements(ArrayContext context) {
		Page<UserDTO> page = new Page<UserDTO>(new Pageable(),
				new ArrayList<UserDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String orgId = (String) map.get("orgId");
				String jobId = (String) map.get("jobId");
				UserDTO userDto = new UserDTO();
				try {
					BeanUtils.populate(userDto, map);
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

				Page<UserDTO> userPage = userService.queryByOrgJob(userDto,
						orgId, jobId, context.getPageable(),
						context.getSortable());
				if (null != userPage) {
					List<UserDTO> userDTOs = userPage.getContents();
					for (UserDTO dto : userDTOs) {
						dto.setExtFields(getExtFields(dto.getUserId()));
					}
					page = new Page<UserDTO>(context.getPageable(), userDTOs);
				}
			}

		}
		return page;
	}

	private Map<String, Object> getExtFields(String userId) {
		Map<String, Object> extFields = new HashMap<String, Object>();
		if (null != userId && !userId.trim().equals("")) {
			List<OrgDTO> orgList = userService.queryOrgs(userId);
			OrgDTO mainOrg = userService.queryMainOrg(userId);
			String mainOrgId = null;
			if (null != mainOrg) {
				mainOrgId = mainOrg.getOrgId();
				extFields.put("mainOrgShowName", mainOrg.getOrgShowName());
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

			extFields
					.put("orgShowName",
							(str.length() > 0 ? str.substring(0,
									str.length() - 1) : ""));

			List<JobDTO> jobList = jobService.queryByUser(null, userId);
			StringBuffer jobNames = new StringBuffer();
			for (JobDTO jobDTO : jobList) {
				if (null != jobDTO) {
					jobNames.append(jobDTO.getJobName());
					jobNames.append(",");
				}
			}
			extFields.put(
					"jobNames",
					(jobNames.length() > 0 ? jobNames.substring(0,
							jobNames.length() - 1) : ""));
		}
		return extFields;
	}
}