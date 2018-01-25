package cn.vvvv.vv.sys.access;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

@Controller
public class AccessController {

	@Autowired
	AccessControlService acs;
	
	@ResponseBody
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
	
	@ResponseBody
	@RequestMapping(value="logout")
	public Object logout(){
		String url = acs.logout();
		if(url != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", url);
			return map;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", null);
			return map;
		}
	}
}
