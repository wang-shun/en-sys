package com.chinacreator.asp.sysmgmt.sysset.jobresmgt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.service.JobService;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.web.ds.ArrayContentProvider;
import com.chinacreator.c2.web.ds.ArrayContext;

public class JobDataArrayContentProviderImpl implements ArrayContentProvider {

	private JobService jobService = ApplicationContextManager.getContext()
			.getBean(JobService.class);

	@Override
	public Page<JobDTO> getElements(ArrayContext context) {
		Page<JobDTO> page = new Page<JobDTO>(new Pageable(),
				new ArrayList<JobDTO>());
		if (null != context) {
			Map<String, Object> map = context.getCondition();
			if (null != map && !map.isEmpty()) {
				String orgId = (String) map.get("orgId");
				if (null != orgId && !orgId.trim().equals("")) {
					if ("-1".equals(orgId)) {
						orgId = null;
					}
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

					page = jobService.queryByOrg(jobDTO, orgId,
							context.getPageable(), context.getSortable());
				}
			}
		}
		return page;
	}
}
