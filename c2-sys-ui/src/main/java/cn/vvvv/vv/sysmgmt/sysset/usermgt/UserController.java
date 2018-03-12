package cn.vvvv.vv.sysmgmt.sysset.usermgt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.advanced.user.service.UserService;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.asp.sysmgmt.sysset.usermgt.UserMgt;

@RestController
public class UserController {

	@Autowired
	UserMgt userMgt;
	@Autowired
	UserService userService;

	@RequestMapping(value = "ws/addUser", method = RequestMethod.POST)
	public int addUser(@RequestBody JSONObject params) {
		UserDTO user = params.getObject("userDTO", UserDTO.class);
		String orgId = params.getString("orgId");
		userService.create(user, orgId, 999);
		return 1;
	}

	@RequestMapping(value = "ws/getUserByPK", method = RequestMethod.GET)
	public UserDTO getUserByPK(@RequestParam String userId) {
		return userMgt.getUserByPK(userId);
	}

	@RequestMapping(value = "ws/editUser", method = RequestMethod.POST)
	public int editUser(@RequestBody UserDTO userDTO) {
		userService.update(userDTO);
		return 1;
	}

}
