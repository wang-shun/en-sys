package com.chinacreator.asp.comp.sys.core.security.shiro.filter;

import java.util.Enumeration;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

//import com.chinacreator.c2.config.ConfigManager;

/**
 * 自定义的动态访问控制类
 * 
 * @author 彭盛
 * 
 */
@Component
public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {

	/** shiro默认的anonUrl链接定义 **/
	private String anonFilterChainDefinitions;
	/** shiro默认的authcUrl链接定义 **/
	private String authcFilterChainDefinitions;

	/** properties配置中，以"anonUrl."开头的无需权限的url的key **/
	private static final String sfs_ANON_START = "anonUrl.";
	/** properties配置中，以"authcUrl."开头的需要权限的url的key **/
	private static final String sfs_AUTHC_START = "authcUrl.";
	/** 分号分隔符 **/
	private static final String sfs_SEPARATOR_SEMICOLON = ";";
	/** 等号分隔符 **/
	private static final String sfs_SEPARATOR_EQUALS = "=";

	@Override
	public Section getObject() throws Exception {
		Ini.Section section = null;
		// 加载默认的anonUrl
		{
			Ini ini = new Ini();
			ini.load(anonFilterChainDefinitions);
			section = ini.getSection(IniFilterChainResolverFactory.URLS);
			if (CollectionUtils.isEmpty(section)) {
				section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
			}
		}

		// 获取properties配置中，以"anonUrl."开头的无需权限的url
		StringBuffer anonUrls = new StringBuffer();
		// 获取properties配置中，以"authcUrl."开头的需要权限的url
		StringBuffer authcUrls = new StringBuffer();

//		Enumeration<String> enumerations = ConfigManager.getInstance()
//				.getConfigNames();
//		if (null != enumerations) {
//			while (enumerations.hasMoreElements()) {
//				String key = (String) enumerations.nextElement();
//				if (key.startsWith(sfs_ANON_START)) {
//					String anonUrl = ConfigManager.getInstance().getConfig(key);
//					if (null != anonUrl && !anonUrl.trim().equals("")) {
//						anonUrls.append(anonUrl)
//								.append(sfs_SEPARATOR_SEMICOLON);
//					}
//				} else if (key.startsWith(sfs_AUTHC_START)) {
//					String authcUrl = ConfigManager.getInstance()
//							.getConfig(key);
//					if (null != authcUrl && !authcUrl.trim().equals("")) {
//						authcUrls.append(authcUrl).append(
//								sfs_SEPARATOR_SEMICOLON);
//					}
//				}
//			}
//		}

		// 加载自定义的anonUrl
		if (anonUrls.length() > 0) {
			for (String url : anonUrls.toString()
					.split(sfs_SEPARATOR_SEMICOLON)) {
				if (!url.trim().equals("")) {
					section.put(url.trim(), "anon");
				}
			}
		}

		// 加载自定义的authcUrl
		if (authcUrls.length() > 0) {
			for (String str : authcUrls.toString().split(
					sfs_SEPARATOR_SEMICOLON)) {
				if (!str.trim().equals("")) {
					String[] strs = str.split(sfs_SEPARATOR_EQUALS);
					if (strs.length == 2) {
						section.put(strs[0].trim(), strs[1].trim());
					}
				}
			}
		}

		// 加载默认的authcUrl
		{
			Ini ini = new Ini();
			ini.load(authcFilterChainDefinitions);
			Ini.Section authcSection = ini
					.getSection(IniFilterChainResolverFactory.URLS);
			if (CollectionUtils.isEmpty(authcSection)) {
				authcSection = ini.getSection(Ini.DEFAULT_SECTION_NAME);
			}
			if (!CollectionUtils.isEmpty(authcSection)) {
				for (String key : authcSection.keySet()) {
					section.put(key, authcSection.get(key));
				}
			}
		}
		return section;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getAnonFilterChainDefinitions() {
		return anonFilterChainDefinitions;
	}

	public void setAnonFilterChainDefinitions(String anonFilterChainDefinitions) {
		this.anonFilterChainDefinitions = anonFilterChainDefinitions;
	}

	public String getAuthcFilterChainDefinitions() {
		return authcFilterChainDefinitions;
	}

	public void setAuthcFilterChainDefinitions(
			String authcFilterChainDefinitions) {
		this.authcFilterChainDefinitions = authcFilterChainDefinitions;
	}

}
