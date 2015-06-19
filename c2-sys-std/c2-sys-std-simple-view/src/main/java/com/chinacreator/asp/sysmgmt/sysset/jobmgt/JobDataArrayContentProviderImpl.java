package com.chinacreator.asp.sysmgmt.sysset.jobmgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.common.CommonPropertiesUtil;
import com.chinacreator.asp.sysmgmt.sysset.jobmenumgt.JobMenuMgtMessages;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class JobDataArrayContentProviderImpl implements ArrayContentProvider {

	private JobService jobService = ApplicationContextManager.getContext()
			.getBean(JobService.class);

	private static final String sfs_ISADMINJOB = CommonPropertiesUtil
			.getAdministratorJobId();

	private static final String sfs_ISEVERYONEJOB = CommonPropertiesUtil
			.getRoleofeveryoneJobId();

	@Override
	public Page<Map<String, Object>> getElements(ArrayContext context) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(
				new Pageable(), new ArrayList<Map<String, Object>>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String orgId = (String) map.get("orgId");
				Page<JobDTO> jobPage = null;

				JobDTO jobDTO = new JobDTO();

				try {
					BeanUtils.populate(jobDTO, map);
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

				if (null != orgId && !orgId.trim().equals("")) {
					if ("-1".equals(orgId)) {
						orgId = null;
					}

					jobPage = jobService.queryByOrg(jobDTO, orgId,
							context.getPageable(), context.getSortable());

				} else {
					jobPage = jobService.queryByJob(jobDTO,
							context.getPageable(), context.getSortable());
				}

				if (null != jobPage) {
					List<JobDTO> jobDTOs = jobPage.getContents();
					List<Map<String, Object>> jobMapList = new ArrayList<Map<String, Object>>();
					Map<String, Object> jobMap = new HashMap<String, Object>();
					try {
						for (JobDTO job : jobDTOs) {
							jobMap = BeanUtils.describe(job);
							if ("0".equals(job.getJobScope())) {
								jobMap.put("orgId", "-1");
								jobMap.put(
										"orgShowName",
										JobMenuMgtMessages
												.getString("JOBMENUMGT.JOBORG_ROOT_TREENAME"));
							} else {
								OrgDTO orgDTO = jobService.queryOrgByJobId(job
										.getJobId());
								jobMap.put("orgId", orgDTO.getOrgId());
								jobMap.put("orgShowName",
										orgDTO.getOrgShowName());
							}
							jobMap.put("isAdminJob", job.getJobId().equals(sfs_ISADMINJOB));
							jobMap.put("isEveryoneJob", job.getJobId().equals(sfs_ISEVERYONEJOB));
							jobMapList.add(jobMap);
						}
						page = new Page<Map<String, Object>>(
								context.getPageable(), jobMapList);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return page;
	}
}
