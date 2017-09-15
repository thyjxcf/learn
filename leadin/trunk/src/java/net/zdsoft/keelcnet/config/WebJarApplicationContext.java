package net.zdsoft.keelcnet.config;

import java.io.IOException;

//import net.zdsoft.keel.util.ArrayUtils;
import net.zdsoft.keelcnet.util.FileUtils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/*
 * Spring入口，通过解析web.xml获取配置文件信息，加载bean
 * 
 * @author taoy
 * @since 1.0
 * @version $Id: WebJarApplicationContext.java,v 1.2 2006/12/21 01:45:29 liangxiao Exp $
 */
public class WebJarApplicationContext extends XmlWebApplicationContext
        implements ConfigurableWebApplicationContext {
    /** Default prefix for building a config location for a namespace */
    public static final String DEFAULT_CONFIG_LOCATION_PREFIX = FileUtils.CLASSPATH
            + "conf/spring/";

    /** Default suffix for building a config location for a namespace */
    public static final String DEFAULT_CONFIG_LOCATION_SUFFIX = ".xml";

    /**
     * web.xml文件参数，用来指定spring配置文件的路径
     */
    public static final String CONTEXT_PATH_PARAM = "cnetContextPath";

    /**
     * web.xml文件参数，用来指定spring配置文件的扩展名
     */
    public static final String CONFIG_SUFFIX_PARAM = "cnetContextSuffix";

    public WebJarApplicationContext() {
    }

    protected String getConfigPath() {
        String configPath = super.getServletContext().getInitParameter(
                CONTEXT_PATH_PARAM);

        if (configPath == null) {
            configPath = DEFAULT_CONFIG_LOCATION_PREFIX;
        }

        return configPath;
    }

    protected String getConfigSuffix() {
        String configSuffix = super.getServletContext().getInitParameter(
                CONFIG_SUFFIX_PARAM);

        if (configSuffix == null) {
            configSuffix = DEFAULT_CONFIG_LOCATION_SUFFIX;
        }

        return configSuffix;
    }

    /**
     * Return the default config locations to use, for the case where no
     * explicit config locations have been specified.
     * 
     * @see #setConfigLocations
     */
    protected String[] getDefaultConfigLocations() {
        long startTime = System.currentTimeMillis();
        
        String[] _configLocations = FileUtils.readClasspathFiles(this
                .getConfigPath(), this.getConfigSuffix(), super
                .getServletContext());

        //System.out.println(ArrayUtils.toString(_configLocations));

        // 增加默认配置文件
        String[] configLocations = new String[_configLocations.length + 1];
        configLocations[0] = "/WEB-INF/applicationContext.xml";
        System.arraycopy(_configLocations, 0, configLocations, 1,
                _configLocations.length);

        //System.out.println(ArrayUtils.toString(configLocations));
        
        System.out.println("getDefaultConfigLocations" + (System.currentTimeMillis() - startTime) + "ms");

        return configLocations;
        // return
        // FileUtils.readClasspathFiles(this.getConfigPath(),this.getConfigSuffix());
        
        
    }

    protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
            throws BeansException, IOException {
        String[] configLocations = getConfigLocations();

        long startTime = System.currentTimeMillis();
        long posTime = startTime;

        if (configLocations != null) {
            ResourcePatternResolver resourcePatternResolver = getResourcePatternResolver();

            for (int i = 0; i < configLocations.length; i++) {
                Resource[] configResources = resourcePatternResolver
                        .getResources(configLocations[i]);

                for (int j = 0; j < configResources.length; j++) {
                    try {
                        reader.loadBeanDefinitions(configResources[j]);
                    }
                    catch (BeansException e) {
                        logger.fatal("读取Spring配置文件 [" + configLocations[i]
                                + "] 时发生错误!", e);
                    }
                }
                System.out.println("loadBeans of " + configLocations[i] + ":" + (System.currentTimeMillis() - posTime) + "ms");
                posTime = System.currentTimeMillis();
            }
        }

        System.out.println("loadBeanDefinitions" + (System.currentTimeMillis() - startTime) + "ms");
    }

}
