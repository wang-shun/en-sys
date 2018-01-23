package cn.vvvv.vv.sys.access;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinacreator.asp.comp.sys.core.security.service.AccessControlService;

@RestController
public class AccessController {

	@Autowired
	AccessControlService acs;
	
	@RequestMapping(value="login")
	public Map<String, Object> login(String userName,String passWord){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isLogin = acs.login(userName, passWord);
		if(isLogin){
			map.put("result", SecurityUtils.getSubject());
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
