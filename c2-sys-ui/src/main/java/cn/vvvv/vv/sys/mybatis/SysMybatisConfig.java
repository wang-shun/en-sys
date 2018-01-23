package cn.vvvv.vv.sys.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.chinacreator.asp.comp.sys.common.SimpleAnnotationBeanNameGenerator;
import com.chinacreator.c2.dao.mybatis.enhance.C2SqlSessionTemplate;
@Component
//@ComponentScan(value = { "com.chinacreator.**.service" }, nameGenerator = SimpleAnnotationBeanNameGenerator.class)
@ComponentScan(value = { "com.chinacreator.asp" }, nameGenerator = SimpleAnnotationBeanNameGenerator.class)
public class SysMybatisConfig {

	@Bean("nameGenerator")
	public SimpleAnnotationBeanNameGenerator SimpleAnnotationBeanNameGenerator() {
		return new SimpleAnnotationBeanNameGenerator();
	}

	@Bean("sysSqlSessionTemplate")
	public C2SqlSessionTemplate C2SqlSessionTemplate(
			SqlSessionFactory sqlSessionFactory) {
		return new C2SqlSessionTemplate(sqlSessionFactory, "sys");
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(
			SimpleAnnotationBeanNameGenerator nameGenerator) {
		MapperScannerConfigurer msc = new MapperScannerConfigurer();
		msc.setNameGenerator(nameGenerator);	
		msc.setNameGenerator(new SimpleAnnotationBeanNameGenerator());
		msc.setBasePackage("com.chinacreator.asp.comp.sys.**.dao");
		msc.setSqlSessionTemplateBeanName("sysSqlSessionTemplate");
		return msc;
	}
}
