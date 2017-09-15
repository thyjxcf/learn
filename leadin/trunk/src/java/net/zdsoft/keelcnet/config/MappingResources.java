package net.zdsoft.keelcnet.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import net.zdsoft.keelcnet.util.FileUtils;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.SAXException;


/**
 * Hibernate xml mapping file list
 * 搜寻Hibernate的实体映射文件，然后预装载
 * 即：先在conf\entity目录中查找后辍名为.entity.xml的配置文件，
 * 然后根据该文件指定的包路径依次查找后辍名为.hbm.xml的Entity配置文件
 * 
 * @author Brave Tao
 * @since 2004-5-15
 * @version $Id: MappingResources.java,v 1.1 2006/12/15 03:17:34 liangxiao Exp $
 */
public class MappingResources {
    public static final String MAPPINGRESOURCES = "mappingResources";
    private static ServletContext servletContext;
    private String DEFAULT_PATH = FileUtils.CLASSPATH + "conf/entity/";
    private String HBM_POSTFIX = ".hbm.xml";
    private String ENTITY_POSTFIX = ".entity.xml";
    private String entityExt = ENTITY_POSTFIX;
    private String hbmExt = HBM_POSTFIX;

    //指定的映射文件，存放的具体值是：/conf/entity/framework.entity.xml
    private List<String> mappingFiles = new ArrayList<String>();

    //目录下文件
    private List<String> classpaths = new ArrayList<String>();

    //内部类Mappings实例列表，数量对应于mappingFiles中包路径的个数
    private List<Mappings> mappingList = new ArrayList<Mappings>();

//  hibernate实体映射文件
    private String[] mappings = null;
    
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Mapping hibernate xml mapping list
     *
     */
    public MappingResources() {
    }

    public void init() {
    	if (classpaths.isEmpty())
			classpaths.add(DEFAULT_PATH);
    }

    public void flush() {
        mappings = null;
        servletContext = null;
        mappingList.clear();
        mappingFiles.clear();
        classpaths.clear();
    }

    /**
     * 如果设置该变量，则系统会从webapp的lib目录下所有jar文件中搜索实体配置文件
     * @param servletContext The servletContext to set.
     */
    public static void setServletContext(ServletContext servletContext) {
        MappingResources.servletContext = servletContext;
    }

    /**
     * 设置实体配置的扩展名
     * @param entityExt The entityExt to set.
     */
    public void setEntityExt(String entityExt) {
        this.entityExt = entityExt;
    }

    /**
     * 设置hibernate映射文件扩展名
     * @param hbmExt The hbmExt to set.
     */
    public void setHbmExt(String hbmExt) {
        this.hbmExt = hbmExt;
    }

    public void setClasspaths(List<String> classpaths) {
    	this.classpaths.clear();
        this.classpaths.addAll(classpaths);
    }

    public void setMappings(List<String> mappings) {
        this.mappingFiles.addAll(mappings);
    }

    public void addMapping(Mappings mapping) {
        if (mappingList.contains(mapping)) {
            return;
        }

        mappingList.add(mapping);
    }

    public String[] getMappingsAsArray() {
        if (mappings != null) {
            return mappings;
        }

        // init entities from classpath and *.entity.xml files
        DefaultResourceLoader loader = new DefaultResourceLoader();
        List<String> entityList = new ArrayList<String>();

        // load from mappings
        entityList.addAll(mappingFiles);

        // load from specified classpath
        for (Iterator<String> it = classpaths.iterator(); it.hasNext();) {
            String path = it.next();
            String[] files = loadClasspath(loader, path);

            for (int i = 0; i < files.length; i++) {
                if (entityList.contains(files[i])) {
                    continue;
                }

                entityList.add(files[i]);
            }
        }

        for (Iterator<String> it = entityList.iterator(); it.hasNext();) {
            String filename = it.next();
            loadMapping(loader, filename);
        }

        if (log.isDebugEnabled()) {
            log.debug("请稍候，系统正在载入Hibernate实体映射...");
        }

        List<String> fileList = new ArrayList<String>();

        for (Iterator<Mappings> it = mappingList.iterator(); it.hasNext();) {
            Mappings m = it.next();
            List<String> files = m.getFiles();
            
            if (true == files.isEmpty()) {
                fileList.addAll(loadPackage(loader, m.getName()));
            } else {
                fileList.addAll(files);
            }
        }

        int i = 0;
        mappings = new String[fileList.size()];

        for (Iterator<String> it = fileList.iterator(); it.hasNext();) {
            mappings[i] = String.valueOf(it.next());
            i++;
        }
        
        if (log.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();
            sb.append("\n------------------------------------------");
            sb.append("\n读取Hibernate映射文件, 共 " + mappings.length + " 个映射文件");

            for (int j = 0; j < mappings.length; j++) {
                sb.append("\n" + mappings[j]);
            }

            sb.append("\n------------------------------------------");
            log.debug(sb.toString());
        }

        return mappings;
    }

    /**
     * 读取*.entity.xml文件
     * @param loader
     * @param filename
     */
    private void loadMapping(ResourceLoader loader, String filename) {
        Resource r = loader.getResource(filename);

        try {
            if (log.isDebugEnabled()) {
                log.debug("载入实体配置文件: " + filename);
            }

            load(r.getInputStream());
        } catch (IOException e) {
            log.error("读取配置文件出错: " + filename, e);
        } catch (SAXException e) {
            log.error("读取配置文件出错: " + filename, e);
        }
    }

    /**
     * 读取位于classpath下的实体配置文件信息，默认配置文件以".entity.xml"
     * @param loader
     * @param classpath
     * @return
     */
    private String[] loadClasspath(ResourceLoader loader, String classpath) {
        return FileUtils.readClasspathFiles(classpath, entityExt, servletContext);
//        return FileUtils.readClasspathFiles(classpath, entityExt);
    }

    /**
     * load mapping file from package.
     *
     * @param packageName
     * @return
     */
    private List<String> loadPackage(ResourceLoader loader, String packageName) {
        @SuppressWarnings("unused")
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
        
        packageName = packageName.replace('.', '/');

        List<String> files = new ArrayList<String>();
        String[] mappingFiles = FileUtils.readClasspathFiles(packageName,
                hbmExt, servletContext);

        for (int i = 0; i < mappingFiles.length; i++) {
            if (mappingFiles[i].startsWith(FileUtils.CLASSPATH)) {
                mappingFiles[i] = mappingFiles[i].substring(FileUtils.CLASSPATH.length());
            }

            files.add(mappingFiles[i]);
        }

        return files;
    }

    /**
     * 使用Digester将*.entity.xml文件映射到this类
     * @param is
     * @throws IOException
     * @throws SAXException
     */
    private void load(InputStream is) throws IOException, SAXException {
        Digester digester = new Digester();
        digester.push(this);
        digester.addObjectCreate("packages/package", Mappings.class.getName());
        digester.addSetProperties("packages/package/file");
        digester.addCallMethod("packages/package/file", "addFile", 1);
        digester.addCallParam("packages/package/file", 0);
        digester.addSetProperties("packages/package");
        digester.addSetNext("packages/package", "addMapping",
            Mappings.class.getName());

        digester.parse(new InputStreamReader(is));
    }

    public static final class Mappings {
        private String packageName;
        private List<String> files = new ArrayList<String>();

        public Mappings() {
        }

        public String getName() {
            return this.packageName;
        }

        public void setName(String packageName) {
            this.packageName = packageName;
        }

        public void addFile(String filename) {
            files.add(filename);
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof Mappings)) {
                return false;
            }

            Mappings m = (Mappings) obj;

            return m.packageName.equals(this.packageName);
        }

        /**
         * @return Returns the files.
         */
        public List<String> getFiles() {
            if (true == files.isEmpty()) {
                return files;
            }
            
            //用"/"代替"."
            packageName = packageName.replace('.', '/');

            List<String> fileList = new ArrayList<String>(files.size());

            for (Iterator<String> it = files.iterator(); it.hasNext();) {
                String name = (String) it.next();
                String fullname = packageName + "/" + name;
                fileList.add(fullname);
            }

            return fileList;
        }
    }
    
    //测试3.5
    public static void main(String[] args) throws IOException, SAXException {
        MappingResources mr = new MappingResources();
        List<String> f = new ArrayList<String>();
        f.add("/conf/entity");
        mr.setClasspaths(f);
        f = new ArrayList<String>();
        f.add("/conf/entity/cnet3_examples.entity.xml");
        f.add("/conf/entity/framework.entity.xml");
        mr.setMappings(f);

        @SuppressWarnings("unused")
        String[] mf = mr.getMappingsAsArray();
    }
}
