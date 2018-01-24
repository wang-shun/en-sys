package cn.vvvv.vv.sys.access;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

@RestController
public class AccessController {

	@Autowired
	AccessControlService acs;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object loginAction(@RequestBody JSONObject params) {
		String username = params.getString("username");
		String password = params.getString("password");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isLogin = acs.login(username, password);
		if(isLogin){
			map.put("result", acs.getUser());
			return map;
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="logout")
	public String logout(){
		return acs.logout();
	}
}
