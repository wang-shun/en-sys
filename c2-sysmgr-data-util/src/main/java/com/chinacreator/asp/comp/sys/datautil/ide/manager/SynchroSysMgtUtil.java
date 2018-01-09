package com.chinacreator.asp.comp.sys.datautil.ide.manager;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.exception.SysException;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeDao;
import com.chinacreator.asp.comp.sys.core.privilege.dao.PrivilegeInsiderelateDao;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeEO;
import com.chinacreator.asp.comp.sys.core.privilege.eo.PrivilegeInsiderelateEO;
import com.chinacreator.asp.comp.sys.core.role.dao.RolePrivilegeDao;

/**
 * IDE同步到系统管理工具类
 * 
 * @author 彭盛
 * 
 */
@Component
public class SynchroSysMgtUtil {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	@Autowired
	private PrivilegeInsiderelateDao privilegeInsiderelateDao;

	/**
	 * 同步IDE中的资源到系统管理
	 */
	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void synchroRes() {
		try {
			SynchroSysDataBulider bulider = new SynchroSysDataBulider();
			List<PrivilegeEO> addPrivilegeList = bulider.getAddPrivilegeList();
			List<PrivilegeEO> updatePrivilegeList = bulider.getUpdatePrivilegeList();
			Set<String> delPrivilegeSet = bulider.getDelPrivilegeSet();
			Set<String> delPermSet = bulider.getDelPermSet();
			Set<String> delPrivilegeInsiderelateSet = bulider.getDelPrivilegeInsiderelateSet();
			List<PrivilegeInsiderelateEO> addPrivilegeInsiderelateList = bulider.getAddPrivilegeInsiderelateList();

			if (null != addPrivilegeList && !addPrivilegeList.isEmpty()) {
				for (PrivilegeEO privilegeEO : addPrivilegeList) {
					privilegeDao.create(privilegeEO);
				}
			}
			if (null != updatePrivilegeList && !updatePrivilegeList.isEmpty()) {
				for (PrivilegeEO privilegeEO : updatePrivilegeList) {
					privilegeDao.update(privilegeEO);
				}
			}
			if (null != delPrivilegeSet && !delPrivilegeSet.isEmpty()) {
				rolePrivilegeDao.deleteByPrivileges(delPrivilegeSet.toArray(new String[delPrivilegeSet.size()]));
				privilegeDao.deleteByPKs(delPrivilegeSet.toArray(new String[delPrivilegeSet.size()]));

			}
			if (null != delPermSet && !delPermSet.isEmpty()) {
				rolePrivilegeDao.deleteByPrivileges(delPermSet.toArray(new String[delPermSet.size()]));
			}

			if (null != delPrivilegeInsiderelateSet && !delPrivilegeInsiderelateSet.isEmpty()) {
				privilegeInsiderelateDao.deleteByPKs(delPrivilegeInsiderelateSet
						.toArray(new String[delPrivilegeInsiderelateSet.size()]));
			}
			for (PrivilegeInsiderelateEO privilegeInsiderelateEO : addPrivilegeInsiderelateList) {
				privilegeInsiderelateDao.create(privilegeInsiderelateEO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e);
		}
	}
}
