package cn.vvvv.vv.sys.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sys.properties")
@ConfigurationProperties(prefix = "")
public class SysConfig {

	Map encryption = new HashMap();
	Map<String, String> sysMgt = new HashMap<String, String>();
	public Map getEncryption() {
		return encryption;
	}
	public void setEncryption(Map encryption) {
		this.encryption = encryption;
	}
	public Map<String, String> getSysMgt() {
		return sysMgt;
	}
	public void setSysMgt(Map<String, String> sysMgt) {
		this.sysMgt = sysMgt;
	}


}
