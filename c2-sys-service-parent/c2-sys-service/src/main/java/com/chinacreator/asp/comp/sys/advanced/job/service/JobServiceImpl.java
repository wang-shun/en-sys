package com.chinacreator.asp.comp.sys.advanced.job.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.asp.comp.sys.advanced.JobMessages;
import com.chinacreator.asp.comp.sys.advanced.group.dao.GroupOrgJobDao;
import com.chinacreator.asp.comp.sys.advanced.group.eo.GroupOrgJobEO;
import com.chinacreator.asp.comp.sys.advanced.job.dao.JobDao;
import com.chinacreator.asp.comp.sys.advanced.job.dto.JobDTO;
import com.chinacreator.asp.comp.sys.advanced.job.eo.JobEO;
import com.chinacreator.asp.comp.sys.advanced.org.dao.OrgJobDao;
import com.chinacreator.asp.comp.sys.advanced.org.eo.OrgJobEO;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.basic.OrgMessages;
import com.chinacreator.asp.comp.sys.basic.RoleMessages;
import com.chinacreator.asp.comp.sys.basic.UserMessages;
import com.chinacreator.asp.comp.sys.basic.org.dto.OrgDTO;
import com.chinacreator.asp.comp.sys.basic.org.eo.OrgEO;
import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.common.CommonConstants;
import com.chinacreator.asp.comp.sys.common.PKGenerator;
import com.chinacreator.asp.comp.sys.core.group.dao.GroupDao;
import com.chinacreator.asp.comp.sys.core.group.eo.GroupEO;
import com.chinacreator.asp.comp.sys.core.group.service.GroupService;
import com.chinacreator.asp.comp.sys.core.role.dto.RoleDTO;
import com.chinacreator.asp.comp.sys.core.role.eo.RoleEO;
import com.chinacreator.asp.comp.sys.core.user.dao.UserInstanceDao;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.comp.sys.core.user.eo.UserInstanceEO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 岗位服务接口实现
 * 
 * @author 杨祎程
 * 
 */
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobDao jobDao;

	@Autowired
	private GroupOrgJobDao groupOrgJobDao;

	@Autowired
	private OrgJobDao orgJobDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UserInstanceDao userInstanceDao;

	@Autowired
	private GroupService groupService;

	@Autowired
	@Qualifier("com.chinacreator.asp.comp.sys.advanced.user.service.UserServiceImpl")
	private UserService userService;

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void create(JobDTO jobDTO) {
		// 验证数据的合法性
		if (null == jobDTO) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_DTO_IS_NULL"));
		}
		if (isBlank(jobDTO.getJobName())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		String jobScopeType = "岗位";
		if (null == jobDTO.getJobScope()) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_SCOPE_IS_NULL"));
		} else {
			if ("0".equals(jobDTO.getJobScope())) {
				jobScopeType = "通用岗位";
			} else if ("1".equals(jobDTO.getJobScope())) {
				jobScopeType = "机构岗位";
			} else {
				throw new IllegalArgumentException(
						JobMessages.getString("JOB.JOB_SCOPE_ILLEGAL"));
			}
		}
		jobDTO.setJobId(PKGenerator.generate());

		// 先创建用户组
		GroupEO groupEO = new GroupEO();
		// 机构用户组，创建用户组时使用用户组ID定义名称
		String pkID = PKGenerator.generate();
		groupEO.setGroupId(pkID);
		groupEO.setGroupName(pkID);
		groupEO.setGroupDesc(jobScopeType + "【" + jobDTO.getJobName()
				+ "】对应的匿名用户组");
		groupEO.setOwnerId(jobDTO.getOwnerId());
		groupEO.setType("2");
		groupDao.create(groupEO);

		// 创建岗位
		JobEO jobEO = new JobEO();
		BeanCopierUtil.copy(jobDTO, jobEO);
		jobDao.create(jobEO);

		// 创建岗位与用户组的关系
		GroupOrgJobEO groupOrgJobEO = new GroupOrgJobEO();
		groupOrgJobEO.setGroupId(groupEO.getGroupId());
		groupOrgJobEO.setJobId(jobEO.getJobId());
		groupOrgJobDao.create(groupOrgJobEO);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void update(JobDTO jobDTO) {
		// 验证数据合法性
		if (null == jobDTO) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_DTO_IS_NULL"));
		}
		if (isBlank(jobDTO.getJobId())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(jobDTO.getJobName())) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_NAME_IS_NULL"));
		}
		JobEO jobEO = new JobEO();
		BeanCopierUtil.copy(jobDTO, jobEO);
		jobDao.update(jobEO);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void deleteByPKs(String... jobIds) {
		// 验证机构ID数组
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		// 查询岗位对应的用户实例
		List<UserInstanceEO> uiEOList = userInstanceDao
				.queryByScopeTypeScopeIds("2", jobIds);
		List<String> uiIdList = getUserInsList(uiEOList);

		if (!uiIdList.isEmpty()) {
			// 删除用户实例
			userService.deleteUserInstancesByUserInstanceIds(uiIdList
					.toArray(new String[uiIdList.size()]));
		}

		// 查询岗位映射的用户组
		List<String> groupIds = jobDao.queryGroupIdsByJobIds(jobIds);
		// 岗位与用户组关系
		groupOrgJobDao.deleteByJobIds(jobIds);
		if (null != groupIds && !groupIds.isEmpty()) {
			// 删除岗位映射的用户组
			groupService.deleteByPKs(groupIds.toArray(new String[groupIds
					.size()]));
		}

		// 删除岗位与机构关系
		orgJobDao.deleteByJobIds(jobIds);

		// 删除岗位
		jobDao.deleteByPKs(jobIds);
	}

	public List<UserDTO> queryUsers(String jobId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		String groupId = jobDao.queryGroupIdByJobId(jobId);

		// 采用从用户组查下来定位到用户
		List<UserDTO> userDTOList = groupService.queryUsers(groupId, 2, jobId);
		return userDTOList;
	}

	public List<JobDTO> queryAll() {
		List<JobDTO> list = new ArrayList<JobDTO>();
		List<JobEO> jobEOList = jobDao.queryAll();

		BeanCopierUtil.copy(jobEOList, list, JobEO.class, JobDTO.class);

		return list;
	}

	public List<JobDTO> queryByJob(JobDTO jobDTO) {
		List<JobDTO> list = new ArrayList<JobDTO>();
		if (null != jobDTO) {
			JobEO jobEO = new JobEO();
			BeanCopierUtil.copy(jobDTO, jobEO);

			List<JobEO> jobEOList = jobDao.queryByJob(jobEO);
			BeanCopierUtil.copy(jobEOList, list, JobEO.class, JobDTO.class);
		}
		return list;
	}

	public Page<JobDTO> queryByJob(JobDTO jobDTO, Pageable pageable,
			Sortable sortable) {
		Page<JobDTO> page = new Page<JobDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<JobDTO>());

		JobEO jobEO = new JobEO();
		BeanCopierUtil.copy(jobDTO, jobEO);

		Page<JobEO> jobPage = jobDao.queryByJob(jobEO, pageable, sortable);
		if (null != jobPage && jobPage.getTotal() > 0) {
			page = BeanCopierUtil.copyPage(jobPage, JobEO.class, JobDTO.class);
		}

		return page;
	}

	public JobDTO queryByPK(String jobId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		JobEO jobEO = jobDao.queryByPK(jobId);
		JobDTO jobDTO = null;
		if (null != jobEO) {
			jobDTO = new JobDTO();
			BeanCopierUtil.copy(jobEO, jobDTO);
		}
		return jobDTO;
	}

	public List<JobDTO> queryByOrg(JobDTO jobDTO, String orgId) {
		if (null == jobDTO) {
			// 为确保能通过机构查询岗位列表，故给新new出一个DTO
			jobDTO = new JobDTO();
		}
		// 1.如果机构ID传了，且非空，则认为是查询机构下的岗位
		if (!isBlank(orgId)) {
			JobEO jobEO = new JobEO();
			BeanCopierUtil.copy(jobDTO, jobEO);

			jobEO.setJobScope("1");
			List<JobEO> jobEOList = jobDao.queryOrgJob(jobEO, orgId);
			List<JobDTO> jobDTOList = new ArrayList<JobDTO>();
			if (null != jobEOList && !jobEOList.isEmpty()) {
				BeanCopierUtil.copy(jobEOList, jobDTOList, JobEO.class,
						JobDTO.class);
			}
			return jobDTOList;
		} else {// 2.如果机构ID传的是空，则认为是查询直接授予的岗位
			JobEO jobEO = new JobEO();
			BeanCopierUtil.copy(jobDTO, jobEO);
			jobEO.setJobScope("0");
			// 认为查询的是全局的岗位
			List<JobEO> jobEOList = jobDao.queryOrgJob(jobEO, null);
			List<JobDTO> jobDTOList = new ArrayList<JobDTO>();
			if (null != jobEOList && !jobEOList.isEmpty()) {
				BeanCopierUtil.copy(jobEOList, jobDTOList, JobEO.class,
						JobDTO.class);
			}
			return jobDTOList;
		}

	}

	public Page<JobDTO> queryByOrg(JobDTO jobDTO, String orgId,
			Pageable pageable, Sortable sortable) {
		if (null == jobDTO) {
			// 为确保能通过机构查询岗位列表，故给新new出一个DTO
			jobDTO = new JobDTO();
		}
		// 1.如果机构ID传了，且非空，则认为是查询机构下的岗位
		if (!isBlank(orgId)) {
			JobEO jobEO = new JobEO();
			BeanCopierUtil.copy(jobDTO, jobEO);

			jobEO.setJobScope("1");
			Page<JobEO> jobEOPage = jobDao.queryOrgJob(jobEO, orgId, pageable,
					sortable);
			Page<JobDTO> jobDTOPage = new Page<JobDTO>(pageable.getPageIndex(),
					pageable.getPageSize(), 0, new ArrayList<JobDTO>());
			if (null != jobEOPage && jobEOPage.getTotal() > 0) {
				jobDTOPage = BeanCopierUtil.copyPage(jobEOPage, JobEO.class,
						JobDTO.class);
			}
			return jobDTOPage;
		} else {// 2.如果机构ID传的是空，则认为是查询直接授予的岗位
			JobEO jobEO = new JobEO();
			BeanCopierUtil.copy(jobDTO, jobEO);
			jobEO.setJobScope("0");
			// 认为查询的是全局的岗位
			Page<JobEO> jobEOPage = jobDao.queryOrgJob(jobEO, null, pageable,
					sortable);
			Page<JobDTO> jobDTOPage = new Page<JobDTO>(pageable.getPageIndex(),
					pageable.getPageSize(), 0, new ArrayList<JobDTO>());
			if (null != jobEOPage && jobEOPage.getTotal() > 0) {
				jobDTOPage = BeanCopierUtil.copyPage(jobEOPage, JobEO.class,
						JobDTO.class);
			}
			return jobDTOPage;
		}
	}

	public List<JobDTO> queryByUser(JobDTO jobDTO, String userId) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (null == jobDTO) {
			// 为确保能通过用户查询岗位列表，故给新new出一个DTO
			jobDTO = new JobDTO();
		}
		JobEO jobEO = new JobEO();
		BeanCopierUtil.copy(jobDTO, jobEO);
		List<JobEO> jobEOList = jobDao.queryUserJob(jobEO, userId);
		List<JobDTO> jobDTOList = new ArrayList<JobDTO>();
		if (null != jobEOList && !jobEOList.isEmpty()) {
			BeanCopierUtil.copy(jobEOList, jobDTOList, JobEO.class,
					JobDTO.class);
		}
		return jobDTOList;
	}

	public Page<JobDTO> queryByUser(JobDTO jobDTO, String userId,
			Pageable pageable, Sortable sortable) {
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		if (null == jobDTO) {
			// 为确保能通过用户查询岗位列表，故给新new出一个DTO
			jobDTO = new JobDTO();
		}
		JobEO jobEO = new JobEO();
		BeanCopierUtil.copy(jobDTO, jobEO);
		Page<JobEO> jobEOPage = jobDao.queryUserJob(jobEO, userId, pageable,
				sortable);
		Page<JobDTO> jobDTOPage = new Page<JobDTO>(pageable.getPageIndex(),
				pageable.getPageSize(), 0, new ArrayList<JobDTO>());
		if (null != jobEOPage && jobEOPage.getTotal() > 0) {
			jobDTOPage = BeanCopierUtil.copyPage(jobEOPage, JobEO.class,
					JobDTO.class);
		}
		return jobDTOPage;
	}

	public List<JobDTO> queryByOrgUser(String orgId, String userId) {
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		if (isBlank(userId)) {
			throw new NullPointerException(
					UserMessages.getString("USER.USERID_IS_NULL"));
		}
		List<JobEO> jobEOList = jobDao.queryByOrgUser(orgId, userId);
		List<JobDTO> jobDTOList = new ArrayList<JobDTO>();
		if (null != jobEOList && !jobEOList.isEmpty()) {
			BeanCopierUtil.copy(jobEOList, jobDTOList, JobEO.class,
					JobDTO.class);
		}
		return jobDTOList;
	}

	public List<RoleDTO> queryRoles(String jobId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		List<RoleEO> roleEOList = jobDao.queryRoles(jobId);
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		if (null != roleEOList && !roleEOList.isEmpty()) {
			BeanCopierUtil.copy(roleEOList, roleDTOList, RoleEO.class,
					RoleDTO.class);
		}
		return roleDTOList;
	}

	public OrgDTO queryOrgByJobId(String jobId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		OrgDTO orgDTO = null;
		OrgEO orgEO = jobDao.queryOrgByJobId(jobId);
		if (null != orgEO) {
			orgDTO = new OrgDTO();
			BeanCopierUtil.copy(orgEO, orgDTO);
		}
		return orgDTO;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToOrgs(String jobId, String... orgIds) {
		addToOrgs(new String[] { jobId }, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToOrgs(String[] jobIds, String[] orgIds) {
		if (null == jobIds) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == orgIds) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		List<OrgJobEO> orgJobEOList = new ArrayList<OrgJobEO>();
		for (String jobId : jobIds) {
			for (String orgId : orgIds) {
				if (!belongsToOrg(jobId, orgId)) {
					OrgJobEO orgJobEO = new OrgJobEO();
					orgJobEO.setJobId(jobId);
					orgJobEO.setOrgId(orgId);
					orgJobEOList.add(orgJobEO);
				}
			}
		}
		if (!orgJobEOList.isEmpty()) {
			orgJobDao.createBatch(orgJobEOList);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToOrgs(String jobId, String... orgIds) {
		setToOrgs(new String[] { jobId }, orgIds);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToOrgs(String[] jobIds, String[] orgIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == orgIds || orgIds.length == 0) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_ARRAY_IS_NULL"));
		}
		for (String orgId : orgIds) {
			if (isBlank(orgId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						OrgMessages.getString("ORG.ORG_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String orgId : orgIds) {
			// 获取机构下的岗位列表
			List<JobEO> jobEOList = jobDao.queryOrgJob(new JobEO(), orgId);
			List<String> jobIdList = getJobIdListInJobEOList(jobEOList);

			// 需要添加的岗位
			List<String> addJobIdList = new ArrayList<String>();

			for (String jobId : jobIds) {
				if (jobIdList.contains(jobId)) {
					jobIdList.remove(jobId);
				} else {
					addJobIdList.add(jobId);
				}
			}

			if (!jobIdList.isEmpty()) {
				removeFromOrgs(jobIdList.toArray(new String[jobIdList.size()]),
						new String[] { orgId });
			}

			if (!addJobIdList.isEmpty()) {
				addToOrgs(
						addJobIdList.toArray(new String[addJobIdList.size()]),
						new String[] { orgId });
			}
		}

	}

	private List<String> getJobIdListInJobEOList(List<JobEO> jobEOList) {
		List<String> jobIdList = new ArrayList<String>();
		if (null != jobEOList && !jobEOList.isEmpty()) {
			for (JobEO jobEO : jobEOList)
				jobIdList.add(jobEO.getJobId());
		}
		return jobIdList;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromAllOrgs(String... jobIds) {
		deleteByPKs(jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrgs(String jobId, String... orgIds) {
		removeFromOrgs(new String[] { jobId }, orgIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromOrgs(String[] jobIds, String[] orgIds) {
		deleteByPKs(jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToUsers(String jobId, String... userIds) {
		addToUsers(new String[] { jobId }, userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void addToUsers(String[] jobIds, String[] userIds) {
		userService.addJobs(userIds, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToUsers(String jobId, String... userIds) {
		setToUsers(new String[] { jobId }, userIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setToUsers(String[] jobIds, String[] userIds) {
		userService.setJobs(userIds, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromAllUsers(String... jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		// 查出所有的用户实例
		List<UserInstanceEO> userInstanceEoList = userInstanceDao
				.queryByScopeTypeScopeIds("2", jobIds);
		List<String> userInsList = getUserInsList(userInstanceEoList);

		// 删除用户实例
		userService.deleteUserInstancesByUserInstanceIds(userInsList
				.toArray(new String[userInsList.size()]));
	}

	private List<String> getUserInsList(List<UserInstanceEO> userInstanceEOs) {
		List<String> arr = new ArrayList<String>();
		if (null != userInstanceEOs && !userInstanceEOs.isEmpty()) {
			for (UserInstanceEO userInstanceEO : userInstanceEOs) {
				if (!isBlank(userInstanceEO.getId())) {
					arr.add(userInstanceEO.getId());
				}
			}
		}
		return arr;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromUsers(String jobId, String... userIds) {
		removeFromUsers(new String[] { jobId }, userIds);

	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void removeFromUsers(String[] jobIds, String[] userIds) {
		userService.removeJobs(userIds, jobIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String jobId, String... roleIds) {
		assignRoles(new String[] { jobId }, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void assignRoles(String[] jobIds, String[] roleIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						RoleMessages
								.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
			}
		}
		List<String> groupIds = jobDao.queryGroupIdsByJobIds(jobIds);
		String[] groupArr = groupIds.toArray(new String[groupIds.size()]);
		groupService.assignRoles(groupArr, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String jobId, String... roleIds) {
		setRoles(new String[] { jobId }, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void setRoles(String[] jobIds, String[] roleIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						RoleMessages
								.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
			}
		}
		for (String jobId : jobIds) {
			// 查询岗位下的角色
			List<RoleEO> roleEOList = jobDao.queryRoles(jobId);
			List<String> roleIdList = getRoleIdListByRoleEOList(roleEOList);
			List<String> addRoleIdList = new ArrayList<String>();
			for (String roleId : roleIds) {
				if (roleIdList.contains(roleId)) {
					roleIdList.remove(roleId);
				} else {
					addRoleIdList.add(roleId);
				}
			}
			if (!roleIdList.isEmpty()) {
				revokeRoles(jobId,
						roleIdList.toArray(new String[roleIdList.size()]));
			}
			if (!addRoleIdList.isEmpty()) {
				assignRoles(jobId,
						addRoleIdList.toArray(new String[addRoleIdList.size()]));
			}
		}
	}

	private List<String> getRoleIdListByRoleEOList(List<RoleEO> roleEOList) {
		List<String> roleIdList = new ArrayList<String>();
		if (null != roleEOList && !roleEOList.isEmpty()) {
			for (RoleEO roleEO : roleEOList) {
				roleIdList.add(roleEO.getRoleId());
			}
		}
		return roleIdList;
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeAllRoles(String... jobIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}

		List<String> groupIds = jobDao.queryGroupIdsByJobIds(jobIds);
		if (null != groupIds && !groupIds.isEmpty()) {
			String[] groupArr = groupIds.toArray(new String[groupIds.size()]);
			groupService.revokeAllRoles(groupArr);
		}
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String jobId, String... roleIds) {
		revokeRoles(new String[] { jobId }, roleIds);
	}

	@Transactional(CommonConstants.sfs_SYSMGT_TRANSACTIONMANAGER_NAME)
	public void revokeRoles(String[] jobIds, String[] roleIds) {
		if (null == jobIds || jobIds.length == 0) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_ARRAY_IS_NULL"));
		}
		if (null == roleIds || roleIds.length == 0) {
			throw new NullPointerException(
					RoleMessages.getString("ROLE.ROLETYPE_ID_ARRAY_IS_NULL"));
		}
		for (String jobId : jobIds) {
			if (isBlank(jobId)) {
				throw new NullPointerException(
						JobMessages.getString("JOB.JOB_ID_ARRAY_HAS_NULL_ITEM"));
			}
		}
		for (String roleId : roleIds) {
			if (isBlank(roleId)) {
				throw new NullPointerException(
						RoleMessages
								.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
			}
		}
		List<String> groupIds = jobDao.queryGroupIdsByJobIds(jobIds);
		String[] groupIdArr = groupIds.toArray(new String[groupIds.size()]);

		groupService.revokeRoles(groupIdArr, roleIds);
	}

	public boolean belongsToOrg(String jobId, String orgId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(orgId)) {
			throw new NullPointerException(
					OrgMessages.getString("ORG.ORG_ID_IS_NULL"));
		}
		return jobDao.belongsToOrg(jobId, orgId) > 0;
	}

	public boolean hasRole(String jobId, String roleId) {
		if (isBlank(jobId)) {
			throw new NullPointerException(
					JobMessages.getString("JOB.JOB_ID_IS_NULL"));
		}
		if (isBlank(roleId)) {
			throw new NullPointerException(
					RoleMessages.getString("ROLE.ROLEID_IS_NULL_EMPTY_BLANK"));
		}
		return jobDao.hasRole(jobId, roleId) > 0;
	}

	/**
	 * 判断字符串是否为空或空格或null
	 * 
	 * @param str
	 *            被判断的字符串
	 * @return 字符串是否为null或者""或者全是空格 是：true 否：false
	 */
	private boolean isBlank(String str) {
		return (null == str || "".equals(str.trim()));
	}

	public boolean containsUser(String jobId, String userId) {
		return userService.belongsJob(userId, jobId);
	}

	public boolean containsUser(String orgId, String jobId, String userId) {
		return userService.belongsJob(orgId, userId, jobId);
	}
}
