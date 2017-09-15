package net.zdsoft.keelcnet.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import net.zdsoft.keelcnet.util.FileUtils;

/**
 * @author zhangza
 * @date 2009-11-2
 */
public class EisContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        long startTime = System.currentTimeMillis();

        // 设置servletContext，以读取jar包中的配置文件
        MappingResources.setServletContext(event.getServletContext());
        
        super.contextInitialized(event);

        // 初始化文件存放根目录
        BootstrapManager.setStoreHome(event.getServletContext());

        // 初始化脚本存放根目录
        BootstrapManager.setScriptHome(event.getServletContext());
                
        System.out.println("提示：bean加载时间为    " + (System.currentTimeMillis() - startTime) + "  ms");
        
        // 读取业务jar包的版本信息
        printEisJarVersionInfo(event.getServletContext());
    }

    @Override
    protected ContextLoader createContextLoader() {
        return new EisContextLoader();
    }
    
    /**
     * 打印eis jar包的版本信息
     */
    private void printEisJarVersionInfo(ServletContext servletContext) {
        File[] jars = FileUtils.readJarFiles(servletContext);
        if ((jars == null))
            return;

        System.out.println("提示：eis各jar包版本如下：");
        for (int i = 0; i < jars.length; i++) {
            JarFile jar = null;
            try {
                jar = new JarFile(jars[i].getCanonicalPath());
                Attributes attributes = jar.getManifest().getMainAttributes();
                if ("zdsoft".equalsIgnoreCase(attributes.getValue("Built-By"))) {
                    String name = attributes.getValue("Implementation-Title");
                    String version = attributes.getValue("Implementation-Version");
                    System.out.println("    "+name + ":\t" + version);
                }
                jar.close();
            } catch (FileNotFoundException e){
                //ignore
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jar != null) {
                    try {
                        jar.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
