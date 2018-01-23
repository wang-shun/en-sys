package cn.vvvv.vv.sys.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sys.properties")
@ConfigurationProperties(prefix = "")
public class SysConfig {

	List<Map<String, String>> sysMgt = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getDatasource() {
		return sysMgt;
	}

	public void setDatasource(List<Map<String, String>> datasource) {
		this.sysMgt = datasource;
	}
}
