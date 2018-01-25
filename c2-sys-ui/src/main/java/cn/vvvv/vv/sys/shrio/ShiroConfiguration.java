package cn.vvvv.vv.sys.shrio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import cn.vvvv.vv.sys.config.SysConfig;

import com.chinacreator.asp.comp.sys.core.security.shiro.filter.C2FormAuthenticationFilter;
import com.chinacreator.asp.comp.sys.core.security.shiro.impls.SimpleIdentitifyInfomationFetcher;
import com.chinacreator.asp.comp.sys.core.security.shiro.interfaces.IdentitifyInfomationFetcher;
import com.chinacreator.asp.comp.sys.core.security.shiro.service.AuthorizeService;
import com.chinacreator.asp.comp.sys.core.security.shiro.service.DefaultSysPasswordService;

@Configuration
@EnableConfigurationProperties(SysConfig.class)
public class ShiroConfiguration {

	@Autowired
	SysConfig sysConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

//	@Bean
//	public C2FormAuthenticationFilter C2FormAuthenticationFilter(){
//		C2FormAuthenticationFilter af = new C2FormAuthenticationFilter();
//		return af;
//	}
	
	/**
	 * Shiro的Web过滤器Factory 命名:shiroFilter<br />
	 * * * @param securityManager * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			org.apache.shiro.mgt.SecurityManager securityManager) {
		logger.info("注入Shiro的Web过滤器-->shiroFilter", ShiroFilterFactoryBean.class);
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// Shiro的核心安全接口,这个属性是必须的
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/dist/index.html");
		// 登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
		shiroFilterFactoryBean.setSuccessUrl("/dist/index.html");
		// 用户访问未对其授权的资源时,所显示的连接
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		/*
		 * 定义shiro过滤器,例如实现自定义的FormAuthenticationFilter，需要继承FormAuthenticationFilter
		 * **本例中暂不自定义实现，在下一节实现验证码的例子中体现
		 */

		/*
		 * 定义shiro过滤链 Map结构 *
		 * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest
		 * .getContextPath()的值来的 *
		 * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种 *
		 * authc
		 * ：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc
		 * .FormAuthenticationFilter
		 */
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//		filterChainDefinitionMap.put("/logout", "logout");

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/login", "anon");// anon 可以理解为不拦截
		filterChainDefinitionMap.put("/dist/**/*", "anon");
		filterChainDefinitionMap.put("/**", "enauthc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		Map<String, Filter> filters = new HashMap<String, Filter>();
		
		C2FormAuthenticationFilter af = new C2FormAuthenticationFilter();
		filters.put("enauthc", af);
		shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache_shiro.xml");
		return cacheManager;
	}

	@Bean
	public PasswordService passWordService(SysConfig sysConfig) {
//		encryption.generatePublicSalt=true
//		encryption.hashAlgorithmName=NONE
//		encryption.hashIterations=500000
		DefaultSysPasswordService ps = new DefaultSysPasswordService();
		DefaultHashService hs = new DefaultHashService();
		// TODO config
		hs.setGeneratePublicSalt(Boolean.valueOf((String)sysConfig.getEncryption().get("encryption")));
		hs.setHashAlgorithmName((String)sysConfig.getEncryption().get("hashAlgorithmName"));
		hs.setHashIterations(Integer.valueOf((String)sysConfig.getEncryption().get("hashIterations")));
		ps.setHashService(hs);
		ps.setHashAlgorithmName((String)sysConfig.getEncryption().get("hashAlgorithmName"));
		return ps;
    }   

	@Bean
	public PasswordMatcher passwordMatcher(PasswordService ps) {
		PasswordMatcher pm = new PasswordMatcher();
		pm.setPasswordService(ps);
		return pm;
	}
	
	@Bean
	public AuthorizingRealm authorizingRealm(PasswordMatcher pm) {
		AuthorizeService as = new AuthorizeService();
		as.setCredentialsMatcher(pm);
		return as;
	}

	/**
     * 
     */
	@Bean
	public RealmSecurityManager securityManager(AuthorizingRealm userRealm) {
		logger.info("注入Shiro的Web过滤器-->securityManager", ShiroFilterFactoryBean.class);
		RealmSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		// 注入缓存管理器;
		securityManager.setCacheManager(ehCacheManager());// 这个如果执行多次，也是同样的一个对象;
		return securityManager;
	}

	/**
	 * Shiro生命周期处理器 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
	 * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 *
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator
	 * (可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能 * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

/*	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			RealmSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}*/
	
	@Bean("identitifyInfomationFetcher")
	public IdentitifyInfomationFetcher SimpleIdentitifyInfomationFetcher(){
		SimpleIdentitifyInfomationFetcher iif = new SimpleIdentitifyInfomationFetcher();
		return iif;
	}	
}
