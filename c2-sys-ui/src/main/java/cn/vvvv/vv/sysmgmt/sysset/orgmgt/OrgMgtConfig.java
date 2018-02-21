package cn.vvvv.vv.sysmgmt.sysset.orgmgt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.chinacreator.asp.comp.sys.common.SimpleAnnotationBeanNameGenerator;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;

@Configuration
@ComponentScan(value = { "com.chinacreator.c2.sys.selecttree.cache" }, nameGenerator = SimpleAnnotationBeanNameGenerator.class)
public class OrgMgtConfig {

//	@Bean	
//	public OrgUserTreeCache getOrgUserTreeCache(){
//		OrgUserTreeCache cache = new OrgUserTreeCache();
//		cache.init();
//		return cache;
//	}
	
}
