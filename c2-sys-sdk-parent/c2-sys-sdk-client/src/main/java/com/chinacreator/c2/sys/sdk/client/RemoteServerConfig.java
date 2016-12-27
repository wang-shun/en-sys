package com.chinacreator.c2.sys.sdk.client;


/**
 * 远程服务器信息，单独定义出来是为了在没有配置服务器地址的情况下让Spring初始化时可以报错
 * 
 * @author Vurt
 */
public class RemoteServerConfig {
	private String host;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取访问服务器的完整url
	 * @param path API相对路径
	 */
	public String getUrl(String path) {
		return  host + "/" + path;
	}
}
