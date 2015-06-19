package com.chinacreator.asp.comp.sys.core.security.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.security.dto.OnlineUserDTO;
import com.chinacreator.asp.comp.sys.core.security.shiro.bean.SimpleUser;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Order;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 在线用户服务接口实现
 * 
 * @author 彭盛
 * 
 */
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

	@Autowired
	private SessionDAO sessionDAO;

	// 登录状态key
	private static final String sfs_AUTHENTICATED_SESSION_KEY = "org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY";
	// 用户信息key
	private static final String sfs_PRINCIPALS_SESSION_KEY = "org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY";

	public int getOnlineSessionNumber() {
		return getSessions().size();
	}

	public List<OnlineUserDTO> getAllOnlineUsers() {
		List<OnlineUserDTO> onlineUserDTOs = new ArrayList<OnlineUserDTO>();
		Collection<Session> sessions = getSessions();
		if (null != sessions && !sessions.isEmpty()) {
			for (Session session : sessions) {
				Object object = session
						.getAttribute(sfs_AUTHENTICATED_SESSION_KEY);
				boolean authenticated = false;
				UserDTO userDTO = null;
				if (null != object && object instanceof Boolean) {
					authenticated = (Boolean) object;
				}
				if (authenticated) {
					object = session.getAttribute(sfs_PRINCIPALS_SESSION_KEY);
					if (null != object
							&& object instanceof SimplePrincipalCollection) {
						SimplePrincipalCollection principal = (SimplePrincipalCollection) object;
						SimpleUser simpleUser = principal
								.oneByType(SimpleUser.class);
						userDTO = new UserDTO();
						BeanCopierUtil.copy(simpleUser, userDTO);
					}
				}
				OnlineUserDTO onlineUserDTO = new OnlineUserDTO();
				onlineUserDTO.setSessionId((String) session.getId());
				onlineUserDTO.setHost(session.getHost());
				onlineUserDTO.setStartTime(session.getStartTimestamp());
				onlineUserDTO.setLastAccessTime(session.getLastAccessTime());
				onlineUserDTO.setAuthenticated(authenticated);
				onlineUserDTO.setUserDTO(userDTO);
				onlineUserDTOs.add(onlineUserDTO);
			}
		}
		return onlineUserDTOs;
	}

	public List<OnlineUserDTO> queryOnlineUsers(OnlineUserDTO onlineUserDTO) {
		List<OnlineUserDTO> onlineUserDTOs = new ArrayList<OnlineUserDTO>();
		List<OnlineUserDTO> allOnlineUserDTOs = getAllOnlineUsers();
		if (null != onlineUserDTO) {
			for (OnlineUserDTO onUserDTO : allOnlineUserDTOs) {
				if (compareOnlineUserDTO(onUserDTO, onlineUserDTO)) {
					onlineUserDTOs.add(onUserDTO);
				}
			}
		} else {
			onlineUserDTOs.addAll(allOnlineUserDTOs);
		}
		return onlineUserDTOs;
	}

	public Page<OnlineUserDTO> queryOnlineUsers(OnlineUserDTO onlineUserDTO,
			Pageable pageable, Sortable sortable) {
		List<OnlineUserDTO> list = queryOnlineUsers(onlineUserDTO);
		int pageSize = pageable.getPageSize();
		int offset = pageable.getOffset();
		int size = (offset + pageSize) > list.size() ? list.size()
				: (offset + pageSize);
		Page<OnlineUserDTO> page = new Page<OnlineUserDTO>(
				pageable.getPageIndex(), pageable.getPageSize(), 0,
				new ArrayList<OnlineUserDTO>());
		List<OnlineUserDTO> outList = new ArrayList<OnlineUserDTO>();
		if (null != list && !list.isEmpty()) {
			if (null != sortable) {
				List<Order> orderList = sortable.getOrders();
				if (null != orderList && !orderList.isEmpty()) {
					sort(list, orderList);
				}
			}

			for (int i = offset; i < size; i++) {
				outList.add(list.get(i));
			}
			page = new Page<OnlineUserDTO>(pageable.getPageIndex(),
					pageable.getPageSize(), list.size(), outList);
		}

		return page;
	}

	/**
	 * 获取未超时session列表
	 * 
	 * @return 未超时session列表
	 */
	private Collection<Session> getSessions() {
		Collection<Session> sessionsList = new ArrayList<Session>();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		if (null != sessions && !sessions.isEmpty()) {
			for (Session session : sessions) {
				if (System.currentTimeMillis() > (session.getLastAccessTime()
						.getTime() + session.getTimeout())) {
					sessionDAO.delete(session);
				} else {
					sessionsList.add(session);
				}
			}
		}
		return sessionsList;
	}

	/**
	 * 比较在线用户信息
	 * 
	 * @param sourceDTO
	 *            源在线用户
	 * @param conditionDTO
	 *            比较条件
	 * @return true:正确，false:错误
	 */
	private boolean compareOnlineUserDTO(OnlineUserDTO sourceDTO,
			OnlineUserDTO conditionDTO) {
		if (null != conditionDTO.getSessionId()
				&& !conditionDTO.getSessionId().trim().equals("")
				&& !conditionDTO.getSessionId()
						.equals(sourceDTO.getSessionId())) {
			return false;
		}
		if (null != conditionDTO.getHost()
				&& !conditionDTO.getHost().trim().equals("")
				&& !sourceDTO.getHost().contains(conditionDTO.getHost())) {
			return false;
		}
		if (null != conditionDTO.getStartTime()
				&& !sourceDTO.getStartTime().after(conditionDTO.getStartTime())) {
			return false;
		}
		if (null != conditionDTO.getLastAccessTime()
				&& !sourceDTO.getLastAccessTime().before(
						conditionDTO.getLastAccessTime())) {
			return false;
		}
		if (null != conditionDTO.getAuthenticated()
				&& !sourceDTO.getAuthenticated().equals(
						conditionDTO.getAuthenticated())) {
			return false;
		}
		UserDTO conditionUser = conditionDTO.getUserDTO();
		UserDTO sourceUser = sourceDTO.getUserDTO();
		if (null != conditionUser) {
			if (null == sourceUser) {
				if (null != conditionUser.getUserId()
						&& !conditionUser.getUserId().trim().equals("")
						&& null != conditionUser.getUserName()
						&& !conditionUser.getUserName().trim().equals("")
						&& null != conditionUser.getUserRealname()
						&& !conditionUser.getUserRealname().trim().equals("")) {
					return true;
				}
				return false;
			} else {
				if (null != conditionUser.getUserId()
						&& !conditionUser.getUserId().trim().equals("")
						&& !sourceUser.getUserId().equals(
								conditionUser.getUserId())) {
					return false;
				}
				if (null != conditionUser.getUserName()
						&& !conditionUser.getUserName().trim().equals("")
						&& !sourceUser.getUserName().contains(
								conditionUser.getUserName())) {
					return false;
				}
				if (null != conditionUser.getUserRealname()
						&& !conditionUser.getUserRealname().trim().equals("")
						&& !sourceUser.getUserRealname().contains(
								conditionUser.getUserRealname())) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 排序在线用户
	 * 
	 * @param list
	 *            需要排序的在线用户列表
	 * @param orderList
	 *            排序条件
	 */
	private void sort(List<OnlineUserDTO> list, final List<Order> orderList) {
		Collections.sort(list, new Comparator<OnlineUserDTO>() {
			public int compare(OnlineUserDTO o1, OnlineUserDTO o2) {
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 == null) {
					return -1;
				}
				if (o2 == null) {
					return 1;
				}

				String property = null;
				String direction = null;
				for (Order order : orderList) {
					property = order.getOrderProperty();
					direction = order.getOrderDirection();

					if (null != property && null != direction) {
						Comparator c = new BeanComparator(property) {
							@Override
							public int compare(Object o1, Object o2) {
								if (getProperty() == null) {
									return getComparator().compare(o1, o2);
								}

								Object value1 = null;
								Object value2 = null;
								try {
									value1 = PropertyUtils.getProperty(o1,
											getProperty());
								} catch (Exception nsme) {
									value1 = "";
								}
								try {
									value2 = PropertyUtils.getProperty(o2,
											getProperty());
								} catch (Exception e) {
									value2 = "";
								}
								return getComparator().compare(value1, value2);
							}
						};

						int result = 0;

						if ("asc".equals(direction)) {
							try {
								result = c.compare(o1, o2);
							} catch (Exception e) {
								result = -1;
							}
						} else if ("desc".equals(direction)) {
							try {
								result = c.compare(o2, o1);
							} catch (Exception e) {
								result = 1;
							}
						}

						if (result != 0) {
							return result;
						}
					}
				}
				return 0;
			}
		});
	}
}