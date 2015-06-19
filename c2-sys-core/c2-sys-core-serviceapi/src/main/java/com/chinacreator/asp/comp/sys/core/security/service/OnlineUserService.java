package com.chinacreator.asp.comp.sys.core.security.service;

import java.util.List;

import com.chinacreator.asp.comp.sys.core.security.dto.OnlineUserDTO;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;

/**
 * 在线用户服务接口
 * 
 * @author 彭盛
 * 
 */
public interface OnlineUserService {

	/**
	 * 获取在线会话数
	 * 
	 * @return 在线会话数
	 */
	public int getOnlineSessionNumber();

	/**
	 * 获取所有在线用户
	 * 
	 * @return 在线用户列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OnlineUserDTO> getAllOnlineUsers();

	/**
	 * 查询在线用户
	 * 
	 * @param onlineUserDTO
	 *            在线用户数据传输对象类
	 * @return 在线用户列表<br>
	 *         一条记录也没查询到的情况下返回无内容的List
	 */
	public List<OnlineUserDTO> queryOnlineUsers(OnlineUserDTO onlineUserDTO);

	/**
	 * 分页查询在线用户
	 * 
	 * @param onlineUserDTO
	 *            在线用户数据传输对象类
	 * @param pageable
	 *            分页对象
	 * @param sortable
	 *            排序对象
	 * @return 分页后的在线用户列表<br>
	 *         一条记录也没查询到的情况下返回无内容的Page
	 */
	public Page<OnlineUserDTO> queryOnlineUsers(OnlineUserDTO onlineUserDTO,
			Pageable pageable, Sortable sortable);
}
