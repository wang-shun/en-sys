package com.chinacreator.asp.comp.sys.std.exp.changejob;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;
import com.chinacreator.c2.context.OperationContextHolder;
import com.chinacreator.c2.context.WebOperationContext;
import com.chinacreator.c2.ioc.ApplicationContextManager;

public class ChangeJobMgt {

	/**
	 * 所有岗位
	 */
	public static final String sfs_ALLJOB = "allJob";

	/**
	 * session中属性的key
	 */
	public static final String sfs_SESSION_CURRENTJOBIDS = "currentJobIds";

	/**
	 * 获取当前session中的岗位Id集合
	 * 
	 * @return 当前session中的岗位Id集合
	 */
	public static Set<String> getCurrentJobIds() {
		HttpSession session = ((WebOperationContext) OperationContextHolder
				.getContext()).getSession();
		String[] currentJobIds = (String[]) session
				.getAttribute(sfs_SESSION_CURRENTJOBIDS);
		Set<String> set = new HashSet<String>();
		if (null != currentJobIds && currentJobIds.length > 0) {
			for (String id : currentJobIds) {
				if (null != id && !id.trim().equals("")) {
					set.add(id);
				}
			}
		} else {
			set.add(sfs_ALLJOB);
		}
		return set;
	}

	/**
	 * 切换岗位
	 * 
	 * @param jobIds
	 *            岗位ID数组
	 */
	public void changeJob(String... jobIds) {
		HttpSession session = ((WebOperationContext) OperationContextHolder
				.getContext()).getSession();
		if (null != jobIds && jobIds.length > 0) {
			session.setAttribute(sfs_SESSION_CURRENTJOBIDS, jobIds);
			AccessControlService accessControlService = ApplicationContextManager
					.getContext().getBean(AccessControlService.class);
			accessControlService.cleanPermittedCache();
		}
	}
}
