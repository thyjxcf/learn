package net.zdsoft.eis.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * 测试基类 <p/> 该类带OpenSessionInTest与事务默认回滚能力,并带有一个jdbcTemplate变量可在同一事务内检查数据库变化.
 * 缺省情况下，对每一个测试案例,他们创建并且回滚一个事务,所以对数据库是安全的. 如果需要在测试后提交，需要setRollBack(false);
 * 或者直接调用setComplete(); see details in spring2's reference
 * 
 * @author Kobe Su,2008-05-07
 * @see AbstractTransactionalDataSourceSpringContextTests
 */
public abstract class EisTestCase extends AbstractTransactionalDataSourceSpringContextTests {

    protected static final Logger log = LoggerFactory.getLogger(EisTestCase.class);

    /**
     * spring配置文件的路径前缀
     */
    protected static final String CONFIG_PREFIX = "classpath:conf/spring";

    /**
     * 定义默认的Spring Context的主文件路径.
     */
    protected static final String DEFAULT_MAIN_CONTEXT = "classpath:conf/spring/applicationContext.xml";
    
    protected static final String LEADIN_CONTEXT = "classpath:conf/spring/leadinServiceContext.xml";

    /**
     * 定义除主文件外所有的Spring Context文件集合的路径.
     */
    protected static final String All_CONTEXT = "classpath:conf/spring/*.xml";

    /**
     * @see AbstractTransactionalDataSourceSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        // 设置AUTOWIRE_BY_NAME ,因为Spring的测试基类默认为BY_TYPE,在有多个相同类型的Bean时冲突.
        // 或者取消setter函数，自行用applicationContext.getBean()来显式查找Bean.
        setAutowireMode(AUTOWIRE_BY_NAME);

        // 默认载入所有的spring配置的bean,在子类可重载此函数以减少载入的applicaitonContext.xml,加快测试速度.
        List<String> contexts = new ArrayList<String>();
        contexts.add(DEFAULT_MAIN_CONTEXT);
        contexts.add(LEADIN_CONTEXT);
        contexts.add("classpath:conf/spring/baseCommonDaoContext.xml");
        contexts.add("classpath:conf/spring/baseCommonServiceContext.xml");
        contexts.add("classpath:conf/spring/baseContext.xml");
        contexts.add("classpath:conf/spring/baseFrameContext.xml");
        contexts.add("classpath*:mq-server-bootstrap-config.xml");        
        
        List<String> neededContexts = Arrays.asList(getNeededConfigLocations());
        contexts.addAll(neededContexts);
        return contexts.toArray(new String[] {});
    }

    /**
     * 子类只需重写此方法
     */
    protected abstract String[] getNeededConfigLocations();

    protected Object getBean(String name) {
        return applicationContext.getBean(name);
    }

}
