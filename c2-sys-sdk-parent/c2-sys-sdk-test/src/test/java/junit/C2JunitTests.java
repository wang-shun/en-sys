package junit;

import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.res.ResourceManager;

/** 
 * c2测试基类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:c2-core-context.xml",
        "classpath*:context/*-context.xml", "classpath*:custom/*-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class C2JunitTests extends AbstractTransactionalJUnit4SpringContextTests {

    @BeforeClass
    public static void c2BeforeClass() {
        try {
            // 获取当前工程所在目录
            String appRoot = Thread.currentThread().getContextClassLoader()
                    .getResource("").toURI().getPath();
            appRoot = appRoot.replaceAll("/target/test-classes/",
                    "/src/main/webapp/");
            // 初始化C2资源解析器
            ResourceManager.init(appRoot);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    } 
 
    @Before
    public void c2Before() {
        // 初始化化C2 Application Context
        ApplicationContextManager.init(applicationContext);
    }

    @Override
    @Resource(name = "dynamicDataSource")
    public void setDataSource(DataSource dataSource) {
        // 设置数据源
       super.setDataSource(dataSource);
    }
}